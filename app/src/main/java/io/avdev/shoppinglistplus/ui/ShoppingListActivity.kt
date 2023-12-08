package io.avdev.shoppinglistplus.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.avdev.domain.model.ShoppingList
import io.avdev.shoppinglistplus.R
import io.avdev.shoppinglistplus.ad.YandexBanner
import io.avdev.shoppinglistplus.ad.YandexInterstitial
import io.avdev.shoppinglistplus.adapter.SListAdapter
import io.avdev.shoppinglistplus.databinding.ActivityAppBinding
import io.avdev.shoppinglistplus.ui.createlist.CreateListFragment
import io.avdev.shoppinglistplus.ui.main.MainFragment
import io.avdev.shoppinglistplus.ui.products.ProductsFragment
import io.avdev.shoppinglistplus.ui.renamelist.RenameListFragment
import io.avdev.shoppinglistplus.utils.listeners.FragmentNavigation
import javax.inject.Inject


@AndroidEntryPoint
class ShoppingListActivity : AppCompatActivity(), FragmentNavigation {
    @Inject lateinit var banner: YandexBanner
    @Inject lateinit var interstitialAd : YandexInterstitial
    @Inject lateinit var adapter : SListAdapter
    private var _binding: ActivityAppBinding? = null
    private val binding get() = _binding!!
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showInterstitialAd()
        setMainFragment()
        adapter.setOnAddElementClickListener(this)
        setBanner()
    }

    private fun setMainFragment() {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
            .replace(R.id.fragmentLayout, MainFragment())
            .commit()
    }

    override fun setCreateListFragment() {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
            .replace(R.id.fragmentLayout, CreateListFragment())
            .commit()
    }

    override fun setProductsFragment(sList : ShoppingList) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
            .replace(R.id.fragmentLayout, ProductsFragment(sList))
            .commit()
    }

    override fun setRenameListFragment(sList : ShoppingList) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
            .replace(R.id.fragmentLayout, RenameListFragment(sList))
            .commit()
    }


    private fun setBanner() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.bannerLayout, banner)
            .commit()
    }

    private fun showInterstitialAd() {
        val firstTimer = object : CountDownTimer(180000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() { interstitialAd.loadInterstitialAd(this@ShoppingListActivity) }
        }
        firstTimer.start()

        val secondTimer = object : CountDownTimer(600000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() { interstitialAd.loadInterstitialAd(this@ShoppingListActivity) }
        }
        secondTimer.start()

        val thirdTimer = object : CountDownTimer(1020000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() { interstitialAd.loadInterstitialAd(this@ShoppingListActivity) }
        }
        thirdTimer.start()

        val fourthTimer = object : CountDownTimer(1500000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() { interstitialAd.loadInterstitialAd(this@ShoppingListActivity) }
        }
        fourthTimer.start()
    }


    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentLayout)

        if (currentFragment is MainFragment) {
            counter++
            if (counter == 1) {
                Toast.makeText(this, "Нажмите еще раз, чтобы выйти", Toast.LENGTH_SHORT).show()
            } else if (counter == 2) {
                super.onBackPressed()
                counter = 0
            }
        }
        else {
            counter = 0
            setMainFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}