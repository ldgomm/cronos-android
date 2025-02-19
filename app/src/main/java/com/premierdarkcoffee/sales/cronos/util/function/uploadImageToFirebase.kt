package com.premierdarkcoffee.sales.cronos.util.function

import android.content.ContentResolver
import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage.getInstance
import java.util.UUID

fun uploadImageToFirebase(context: Context,
                          contentResolver: ContentResolver,
                          uri: Uri,
                          path: String,
                          imageInfo: (ImageInfo) -> Unit) {
    val remoteImagePath = "xxx/$path/${UUID.randomUUID()}.jpg"
    compressImage(uri, contentResolver, context)?.let { newUri ->
        getInstance().reference.child(remoteImagePath).putFile(newUri).addOnSuccessListener {
            getInstance().reference.child(remoteImagePath).downloadUrl.addOnSuccessListener { downloadUrl ->
                val info = ImageInfo(remoteImagePath, downloadUrl.toString())
                Log.d(TAG, "uploadImageToFirebase | Success: $info")
                imageInfo(info)
            }.addOnFailureListener { exception ->
                Log.d(TAG, "uploadImageToFirebase | Failed to get download URL: $exception")
            }
        }.addOnFailureListener { exception ->
            Log.d(TAG, "uploadImageToFirebase | Failed uploadImageToFirebase: $exception")
        }
    } ?: Log.d(TAG, "uploadImageToFirebase | Failed to compress image")
}
