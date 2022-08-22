package com.anafthdev.tfcompose.foundation.common

import android.content.Context
import android.graphics.Bitmap
import android.util.Size
import com.anafthdev.tfcompose.data.ClassificationColors
import com.anafthdev.tfcompose.data.ModelType
import com.anafthdev.tfcompose.data.model.ClassificationColor
import com.anafthdev.tfcompose.data.model.ClassificationResult
import com.anafthdev.tfcompose.foundation.extension.isFloating
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.nnapi.NnApiDelegate
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.TensorProcessor
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp
import org.tensorflow.lite.support.image.ops.Rot90Op
import org.tensorflow.lite.support.label.TensorLabel
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import timber.log.Timber
import javax.inject.Inject


abstract class Classifier(
	val context: Context,
	private val modelType: ModelType
) {
	
	private val nnApiDelegate = NnApiDelegate()
	
	private val tflite by lazy {
		Interpreter(
			FileUtil.loadMappedFile(context, getModel()),
			Interpreter.Options().addDelegate(nnApiDelegate))
	}
	
	private val tfInputSize by lazy {
		val inputIndex = 0
		val inputShape = tflite.getInputTensor(inputIndex).shape()
		Size(inputShape[2], inputShape[1]) // Order of axis is: {1, height, width, 3}
	}
	
	private val dataType by lazy {
		val probabilityTensorIndex = 0
		tflite.getOutputTensor(probabilityTensorIndex).dataType()
	}
	
	private val inputImageBuffer by lazy {
		TensorImage(dataType)
	}
	
	private val probabilityDataType by lazy {
		val probabilityTensorIndex = 0
		tflite.getInputTensor(probabilityTensorIndex).dataType()
	}
	
	private val probabilityShape by lazy {
		val probabilityTensorIndex = 0
		tflite.getOutputTensor(probabilityTensorIndex).shape()
	}
	
	private val outputProbabilityBuffer by lazy {
		TensorBuffer.createFixedSize(probabilityShape, probabilityDataType)
	}
	
	private val probabilityProcessor by lazy {
		TensorProcessor.Builder()
			.add(NormalizeOp(getProbabilityMean(), getProbabilityStd()))
			.build()
	}
	
	fun predict(bitmap: Bitmap, orientation: Int = 0): List<ClassificationResult> {
		val result = arrayListOf<ClassificationResult>()
		val inputImageBuffer = loadImage(bitmap, orientation)
		
		tflite.run(
			inputImageBuffer.buffer,
			outputProbabilityBuffer.buffer.rewind()
		)
		
		val labeledProbability: Map<String, Float> = TensorLabel(
				getLabels(),
				probabilityProcessor.process(outputProbabilityBuffer)
			).mapWithFloatValue
		
		labeledProbability.forEach { (label, accuracy) ->
			result.add(
				ClassificationResult(
					label = label,
					accuracy = accuracy
				)
			)
			
			Timber.i("label: $label, acc: $accuracy")
		}
		
		return result
	}
	
	private fun loadImage(bitmap: Bitmap, orientation: Int): TensorImage {
		inputImageBuffer.load(bitmap)
		
		val cropSize = minOf(bitmap.width, bitmap.height)
		
		return ImageProcessor.Builder()
			.add(ResizeWithCropOrPadOp(cropSize, cropSize))
			.add(
				ResizeOp(
					tfInputSize.height,
					tfInputSize.width,
					ResizeOp.ResizeMethod.NEAREST_NEIGHBOR
				)
			)
			.add(Rot90Op(orientation / 90))
			.add(NormalizeOp(getImageMean(), getImageStd()))
			.build()
			.process(inputImageBuffer)
	}
	
//	fun detect(bitmap: Bitmap) {
////		val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, false)
//
//		val landscape: Boolean = bitmap.width > bitmap.height
//
//		val scaleFactor = if (landscape) IMAGE_SIZE.toFloat() / bitmap.height
//		else IMAGE_SIZE.toFloat() / bitmap.width
//
//		val matrix = Matrix()
//		matrix.postScale(scaleFactor, scaleFactor)
//
//		val scaledBitmap = if (landscape) {
//			val start: Int = (bitmap.width - bitmap.height) / 2
//			Bitmap.createBitmap(
//				bitmap,
//				start,
//				0,
//				bitmap.height,
//				bitmap.height,
//				matrix,
//				true
//			)
//		} else {
//			val start: Int = (bitmap.height - bitmap.width) / 2
//			Bitmap.createBitmap(
//				bitmap,
//				0,
//				start,
//				bitmap.width,
//				bitmap.width,
//				matrix,
//				true
//			)
//		}
//
//		val labels = listOf("Daisy", "Dandelion", "Rose", "Sunflower", "Tulip")
//
//		val size = 1 * IMAGE_SIZE * IMAGE_SIZE * 3
//		val byteBuffer = ByteBuffer.allocateDirect(size)
//		scaledBitmap.copyPixelsToBuffer(byteBuffer)
//
//		try {
//			val model = ModelQuantized.newInstance(context)
//
//			val tensorBuffer = TensorBuffer.createFixedSize(intArrayOf(1, IMAGE_SIZE, IMAGE_SIZE, 3), DataType.UINT8)
//
////			val byteBuffer = ByteBuffer.allocateDirect(1 * IMAGE_SIZE * IMAGE_SIZE * 3)
//			byteBuffer.order(ByteOrder.nativeOrder())
//
//			val pixels = IntArray(IMAGE_SIZE * IMAGE_SIZE)
//			scaledBitmap.getPixels(pixels, 0, scaledBitmap.width, 0, 0, scaledBitmap.width, scaledBitmap.height)
//
//			var pixel = 0
//
//			for (i in 0 until IMAGE_SIZE) {
//				for (j in 0 until IMAGE_SIZE) {
//					val px = pixels[pixel++] // RGB
//
//					byteBuffer.apply {
//						putFloat(((px shr 16) and 0xFF) * (1f / 1))
//						putFloat(((px shr 8) and 0xFF) * (1f / 1))
//						putFloat((px and 0xFF) * (1f / 1))
//					}
//				}
//			}
//
//			tensorBuffer.loadBuffer(byteBuffer)
//
//			val outputs = model.process(tensorBuffer)
//			val outputAsTensorBuffer = outputs.outputFeature0AsTensorBuffer
//
//			val accuracy = outputAsTensorBuffer.floatArray
//
//			val maxAccuracy = accuracy.maxOrNull() ?: 0
//			val maxAccuracyIndex = accuracy.indexOfFirst { it == maxAccuracy }
//
//			"accuracy: $maxAccuracy".toast(context)
//			"label: ${labels[maxAccuracyIndex]}".toast(context)
//
//			model.close()
//		} catch (e: Exception) {
//			e.printStackTrace()
//		}
//	}
	
	private fun getImageMean(): Float {
		return if (modelType.isFloating()) IMAGE_MEAN_FLOATING else IMAGE_MEAN_QUANTIZED
	}
	
	private fun getImageStd(): Float {
		return if (modelType.isFloating()) IMAGE_STD_FLOATING else IMAGE_STD_QUANTIZED
	}
	
	private fun getProbabilityMean(): Float {
		return if (modelType.isFloating()) PROBABILITY_MEAN_FLOATING else PROBABILITY_MEAN_QUANTIZED
	}
	
	private fun getProbabilityStd(): Float {
		return if (modelType.isFloating()) PROBABILITY_STD_FLOATING else PROBABILITY_STD_QUANTIZED
	}
	
	abstract fun getModel(): String
	
	abstract fun getLabels(): List<String>
	
	companion object {
		const val IMAGE_SIZE = 224
		
		/**
		 * The quantized model does not require normalization, thus set mean as 0.0f, and std as 1.0f to
		 * bypass the normalization.
		 */
		private const val IMAGE_MEAN_QUANTIZED = 0.0f
		
		private const val IMAGE_STD_QUANTIZED = 1.0f
		
		/** Quantized MobileNet requires additional dequantization to the output probability.  */
		private const val PROBABILITY_MEAN_QUANTIZED = 0.0f
		
		private const val PROBABILITY_STD_QUANTIZED = 255.0f
		
		
		private const val IMAGE_MEAN_FLOATING = 127.5f
		
		private const val IMAGE_STD_FLOATING = 127.5f
		
		private const val PROBABILITY_MEAN_FLOATING = 0.0f
		
		private const val PROBABILITY_STD_FLOATING = 1.0f
	}
	
}