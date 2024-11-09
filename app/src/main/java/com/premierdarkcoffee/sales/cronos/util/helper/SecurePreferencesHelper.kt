package com.premierdarkcoffee.sales.cronos.util.helper

//
//  SecurePreferencesHelper.kt
//  Cronos
//
//  Created by José Ruiz on 8/8/24.
//

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object SecurePreferencesHelper {
    private const val PREFERENCE_NAME = "secure_user_preferences"
    private const val API_KEY = "bearer"

    private fun getSecurePreferences(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        return EncryptedSharedPreferences.create(
            context,
            PREFERENCE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun getApiKey(context: Context): String? {
        return getSecurePreferences(context).getString(API_KEY, "")
    }

    fun setApiKey(
        context: Context,
        token: String
    ) {
        getSecurePreferences(context).edit().putString(API_KEY, token).apply()
    }

    fun deleteApiKey(context: Context) {
        getSecurePreferences(context).edit().remove(API_KEY).apply()
    }
}
