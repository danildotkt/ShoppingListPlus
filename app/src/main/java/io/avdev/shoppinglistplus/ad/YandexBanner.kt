package io.avdev.shoppinglistplus.ad

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.AdTheme
import com.yandex.mobile.ads.common.ImpressionData
import io.avdev.shoppinglistplus.consts.AdConst.TEST_BANNER
import io.avdev.shoppinglistplus.databinding.FragmentYandexBannerBinding



class YandexBanner : Fragment(){

    private lateinit var binding: FragmentYandexBannerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentYandexBannerBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeYandexBanner()
    }

    private fun initializeYandexBanner() = with(binding.yandexBanner){
        setAdUnitId(TEST_BANNER)
        setAdSize(BannerAdSize.stickySize(context, 450))
        val adRequest = AdRequest.Builder().setPreferredTheme(AdTheme.DARK).build()
        loadAd(adRequest)
        bannerEventListener()
    }

    private fun bannerEventListener() = with(binding.yandexBanner){
        setBannerAdEventListener(object : BannerAdEventListener {
            override fun onAdLoaded() {
            }

            override fun onAdFailedToLoad(error : AdRequestError) {
                Log.i("adlog", "Yandex banner error : ${error.description}")
            }

            override fun onAdClicked() {
            }

            override fun onLeftApplication() {
            }

            override fun onReturnedToApplication() {
            }

            override fun onImpression(p0: ImpressionData?) {
            }

        })
    }
}