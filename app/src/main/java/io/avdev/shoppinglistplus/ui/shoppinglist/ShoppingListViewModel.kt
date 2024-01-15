import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import io.avdev.shoppinglistplus.ad.YandexBanner
import io.avdev.shoppinglistplus.ad.YandexInterstitialAd
import io.avdev.shoppinglistplus.adapter.ShoppingListAdapter
import io.avdev.shoppinglistplus.ui.shoppinglist.ShoppingListActivity

class ShoppingListViewModel(
    val banner: YandexBanner,
    val interstitialAd: YandexInterstitialAd,
    val adapter: ShoppingListAdapter
) : ViewModel() {
    private var interval = 0
    private var interstitialAdTimer: CountDownTimer? = null
    val adTime = 3 * 60 * 1000
    private var savedInterval: Long = 0

    private fun showInterstitialAd(activity: ShoppingListActivity) {
        interstitialAdTimer = object : CountDownTimer(adTime - savedInterval, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                interval += 1000
                savedInterval = interval.toLong()
            }

            override fun onFinish() {
                interstitialAd.loadInterstitialAd(activity)
                interval = 0
                savedInterval = 0
            }
        }.start()
    }

    fun stopInterstitialAdTimer() {
        interstitialAdTimer?.cancel()
    }

    fun resumeInterstitialAdTimer(activity: ShoppingListActivity) {
        showInterstitialAd(activity)
    }
}
