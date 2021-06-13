package com.amityadav.kreditbeeandroidsample.data.preference

abstract class PreferenceManager<Type, in Params>() where Type : Any {

    abstract fun saveUserId(userId : String)
    abstract fun getUserId(): String
    abstract fun clearData()
}