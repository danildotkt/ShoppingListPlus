package io.avdev.shoppinglistplus

import android.app.Application
import android.util.Log
import com.yandex.mobile.ads.common.MobileAds
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShoppingListPlusApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initMobileAds()
    }

    private fun initMobileAds() {
        MobileAds.initialize(this) {
            Log.i("adlog", "ad sdk is initialize")
        }
    }
}