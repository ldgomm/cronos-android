package com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.viewmodel

import android.content.Context
import android.content.SharedPreferences

/*
private fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
    val masterKey = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

    return EncryptedSharedPreferences.create(context,
                                             "secret_shared_prefs",
                                             masterKey,
                                             EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                                             EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
}

fun getApiKey(context: Context): String {
    val sharedPreferences = getEncryptedSharedPreferences(context)
    return sharedPreferences.getString("API_KEY", null) ?: ""
}

fun storeApiKey(context: Context, apiKey: String) {
    val sharedPreferences = getEncryptedSharedPreferences(context)
    sharedPreferences.edit().putString("API_KEY", apiKey).apply()
}

 */

private fun getSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("secret_shared_prefs", Context.MODE_PRIVATE)
}

fun getApiKey(context: Context): String {
    val sharedPreferences = getSharedPreferences(context)
    return sharedPreferences.getString("API_KEY", null) ?: ""
}

fun storeApiKey(context: Context, apiKey: String) {
    val sharedPreferences = getSharedPreferences(context)
    sharedPreferences.edit().putString("API_KEY", apiKey).apply()
}
