package io.avdev.shoppinglistplus.ad

import android.app.Activity
import android.util.Log
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.AdTheme
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader
import io.avdev.shoppinglistplus.ad.ConstAd.YANDEX_INTERSTITIAL

class YandexInterstitialAd {
    fun loadInterstitialAd(activity: Activity) {

        val loader = InterstitialAdLoader(activity).apply {
            setAdLoadListener(object : InterstitialAdLoadListener {
                override fun onAdLoaded(ad: InterstitialAd) {
                    ad.show(activity)
                }

                override fun onAdFailedToLoad(error: AdRequestError) {
                    Log.e("adlog", error.description)
                }

            })
        }
        loader.loadAd(
            AdRequestConfiguration.Builder(YANDEX_INTERSTITIAL)
                .setPreferredTheme(AdTheme.DARK)
                .build()
        )
    }
}