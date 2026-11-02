package com.example.caffeshop.data.repository

import android.content.Context
import android.content.SharedPreferences

class UserManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveUser(fullName: String, username: String) {
        prefs.edit()
            .putString(KEY_FULL_NAME, fullName.trim())
            .putString(KEY_USERNAME, username.trim())
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .apply()
    }

    fun updateFullName(newFullName: String) {
        prefs.edit()
            .putString(KEY_FULL_NAME, newFullName.trim())
            .apply()
    }

    fun getFullName(): String {
        return prefs.getString(KEY_FULL_NAME, "Nama") ?: "Nama"
    }

    fun getUsername(): String {
        return prefs.getString(KEY_USERNAME, "user") ?: "user"
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun logout() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val PREF_NAME = "caffe_shop_user_prefs"
        private const val KEY_FULL_NAME = "key_full_name"
        private const val KEY_USERNAME = "key_username"
        private const val KEY_IS_LOGGED_IN = "key_is_logged_in"

        @Volatile
        private var instance: UserManager? = null

        fun getInstance(context: Context): UserManager {
            return instance ?: synchronized(this) {
                instance ?: UserManager(context.applicationContext).also { instance = it }
            }
        }
    }
}
