package com.amityadav.kreditbeeandroidsample.data.preference

import android.content.SharedPreferences

class PreferenceHelperImp(private val preference: SharedPreferences) : PreferenceManager<Any, Any?>() {

    val PREF_KEY_USER_ID = "userId"

    override fun saveUserId(userId: String) {
        return preference.edit().putString(PREF_KEY_USER_ID, userId).apply()
    }

    override fun getUserId(): String {
        return preference.getString(PREF_KEY_USER_ID, "").orEmpty()
    }

    override fun clearData(){
        preference.edit().clear().apply()
    }
}