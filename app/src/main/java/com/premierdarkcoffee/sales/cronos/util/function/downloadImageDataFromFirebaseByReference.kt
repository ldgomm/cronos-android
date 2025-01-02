package com.premierdarkcoffee.sales.cronos.util.function

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.withTimeout
import java.io.File
import java.util.UUID
import kotlin.coroutines.suspendCoroutine

data class ImageInfo(val path: String, val url: String)

//suspend fun downloadImageDataFromFirebaseByReference(path: String): ImageInfo = withTimeout(10000) { // 5000 ms timeout
//    suspendCoroutine { continuation ->
//        val oldReference = FirebaseStorage.getInstance().getReference(path)
//        val ONE_MEGABYTE: Long = 1024 * 1024
//
//        oldReference.getBytes(ONE_MEGABYTE).addOnSuccessListener { oldImage ->
//                val newPath = "product/main/${UUID.randomUUID()}.jpg"
//                val newReference = FirebaseStorage.getInstance().reference.child(newPath)
//
//                val tempFile = File.createTempFile("tempImage", "jpg")
//                tempFile.writeBytes(oldImage)
//                val fileUri = Uri.fromFile(tempFile)
//
//                newReference.putFile(fileUri).addOnSuccessListener {
//                    newReference.downloadUrl.addOnSuccessListener { downloadUrl ->
//                        Log.d(TAG,
//                              "Tracking | downloadImageDataFromFirebaseByReference | Success to download image from Firebase for old path: $path")
//                        Log.d(TAG,
//                              "Tracking | downloadImageDataFromFirebaseByReference | Success to download image from Firebase for new path: $newPath")
//                        continuation.resumeWith(Result.success(ImageInfo(newPath, downloadUrl.toString())))
//                    }.addOnFailureListener { exception ->
//                        Log.d(TAG, "Tracking | downloadImageDataFromFirebaseByReference | Failed to get download URL: $exception")
//                        continuation.resumeWith(Result.failure(exception))
//                    }
//                }.addOnFailureListener { exception ->
//                    Log.d(TAG,
//                          "Tracking | downloadImageDataFromFirebaseByReference | Failed to upload image to Firebase: $exception")
//                    continuation.resumeWith(Result.failure(exception))
//                }
//        }.addOnFailureListener { exception ->
//            Log.d(TAG, "Tracking | downloadImageDataFromFirebaseByReference | Failed to download image from Firebase: $exception")
//            continuation.resumeWith(Result.failure(exception))
//        }
//    }
//}
