package com.premierdarkcoffee.sales.cronos.util.function

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID

fun compressImage(
    uri: Uri,
    contentResolver: ContentResolver,
    context: Context
): Uri? {
    try {
        val inputStream = contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val rotatedBitmap = bitmap?.let { correctImageOrientation(it, uri, contentResolver) }

        var quality = 100
        var byteArray: ByteArray
        do {
            val byteArrayOutputStream = ByteArrayOutputStream()
            rotatedBitmap?.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
            byteArray = byteArrayOutputStream.toByteArray()
            quality -= 5
        } while (byteArray.size > 512 * 1024 && quality > 0)

        // Save the compressed image to a temporary file in the cache directory
        val fileName = "${UUID.randomUUID()}.jpg"
        val cacheDir = context.cacheDir
        val file = File(cacheDir, fileName)
        FileOutputStream(file).use { it.write(byteArray) }

        // Get a Uri for the file using FileProvider
        val compressedImageUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        inputStream?.close()
        return compressedImageUri
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
}


private fun correctImageOrientation(
    bitmap: Bitmap,
    uri: Uri,
    contentResolver: ContentResolver
): Bitmap {
    val exifInterface = contentResolver.openInputStream(uri)?.let { ExifInterface(it) }
    val orientation = exifInterface?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270f)
        ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> flipImage(bitmap, horizontal = true)
        ExifInterface.ORIENTATION_FLIP_VERTICAL -> flipImage(bitmap, horizontal = false)
        else -> bitmap
    }
}

private fun rotateImage(
    bitmap: Bitmap,
    degree: Float
): Bitmap {
    val matrix = Matrix().apply { postRotate(degree) }
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

private fun flipImage(
    bitmap: Bitmap,
    horizontal: Boolean
): Bitmap {
    val matrix = Matrix().apply {
        postScale(if (horizontal) -1f else 1f, if (horizontal) 1f else -1f)
    }
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}
