package com.premierdarkcoffee.sales.cronos.util.function

import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

suspend fun downloadImageFromFirebaseByReference(reference: String): String {
    val storageReference = FirebaseStorage.getInstance().getReference(reference)
    return storageReference.downloadUrl.await().toString()
}
