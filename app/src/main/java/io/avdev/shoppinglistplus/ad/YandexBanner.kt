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
import io.avdev.shoppinglistplus.ad.ConstAd.YANDEX_BANNER_RUSTORE
import io.avdev.shoppinglistplus.databinding.FragmentYandexBannerBinding

class YandexBanner : Fragment() {

    private var _binding: FragmentYandexBannerBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentYandexBannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeYandexBanner()
    }

    private fun initializeYandexBanner() = with(binding.yandexBanner) {
        setAdUnitId(YANDEX_BANNER_RUSTORE)
        setAdSize(BannerAdSize.stickySize(context, 500))
        val adRequest = AdRequest.Builder().setPreferredTheme(AdTheme.LIGHT).build()
        loadAd(adRequest)
        bannerEventListener()
    }

    private fun bannerEventListener() = with(binding.yandexBanner) {
        setBannerAdEventListener(object : BannerAdEventListener {
            override fun onAdLoaded() {
            }

            override fun onAdFailedToLoad(error: AdRequestError) {
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}