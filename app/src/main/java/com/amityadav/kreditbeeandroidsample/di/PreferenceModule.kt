package com.amityadav.kreditbeeandroidsample.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.amityadav.kreditbeeandroidsample.data.preference.PreferenceHelperImp

fun providePreferenceInstance(application: Application) : SharedPreferences {

    val prefFileName = "KEDITBEEDEMO"
    return application.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
}

fun providePreferenceImplementer(preferences: SharedPreferences) : PreferenceHelperImp {
    return PreferenceHelperImp(preferences)
}