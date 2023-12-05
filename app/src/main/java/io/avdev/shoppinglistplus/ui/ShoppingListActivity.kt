package io.avdev.shoppinglistplus.ui

import android.os.Bundle
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
    private lateinit var binding: ActivityAppBinding
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setMainFragment()
        setBanner()
        adapter.setOnAddElementClickListener(this)
    }

    private fun setBanner() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.bannerLayout, banner)
            .commit()
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

    override fun setUpdateNameFragment(sList : ShoppingList) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
            .replace(R.id.fragmentLayout, RenameListFragment(sList))
            .commit()
    }

    override fun setInterstitialAd() {
        interstitialAd.loadInterstitialAd(this)
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
}