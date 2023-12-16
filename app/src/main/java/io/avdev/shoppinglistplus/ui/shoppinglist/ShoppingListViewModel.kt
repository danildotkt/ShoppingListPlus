package io.avdev.shoppinglistplus.ui.shoppinglist

import androidx.lifecycle.ViewModel
import io.avdev.shoppinglistplus.ad.YandexBanner
import io.avdev.shoppinglistplus.ad.YandexInterstitialAd
import io.avdev.shoppinglistplus.adapter.ShoppingListAdapter

class ShoppingListViewModel(
    val banner: YandexBanner,
    val interstitialAd: YandexInterstitialAd,
    val adapter: ShoppingListAdapter
) : ViewModel() {

//    private lateinit var interstitialAdTimer: CountDownTimer
//    private var remainingTime: Long = 0
//
//    fun showInterstitialAd(activity: ShoppingListActivity) {
//        val interval: Long = 5 * 60 * 1000
//        interstitialAdTimer = object : CountDownTimer(interval - remainingTime, interval) {
//            override fun onTick(millisUntilFinished: Long) {
//                remainingTime = millisUntilFinished
//            }
//
//            override fun onFinish() {
//                interstitialAd.loadInterstitialAd(activity)
//                remainingTime = 0
//                start()
//            }
//        }.start()
//    }
//
//    fun stopInterstitialAdTimer() {
//        interstitialAdTimer.cancel()
//    }
//
//    fun resumeInterstitialAd(activity: ShoppingListActivity) {
//        showInterstitialAd(activity)
//    }
}