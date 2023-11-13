package io.avdev.shoppinglistplus.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.avdev.shoppinglistplus.R
import io.avdev.shoppinglistplus.ad.YandexBanner
import io.avdev.shoppinglistplus.adapter.ShoppingListAdapter
import io.avdev.shoppinglistplus.databinding.ActivityAppBinding
import io.avdev.shoppinglistplus.ui.createlist.CreateListFragment
import io.avdev.shoppinglistplus.ui.main.MainFragment
import io.avdev.shoppinglistplus.ui.products.ProductsFragment
import io.avdev.shoppinglistplus.utils.listeners.FragmentNavClickListener
import javax.inject.Inject


@AndroidEntryPoint
class ShoppingListActivity : AppCompatActivity(), FragmentNavClickListener {
    @Inject lateinit var banner: YandexBanner
    @Inject lateinit var adapter: ShoppingListAdapter
    private lateinit var binding: ActivityAppBinding
    private val createListFragment: CreateListFragment by lazy {
        CreateListFragment()
    }
    private var backButtonCount: Int = 0

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
            .replace(R.id.fragmentLayout, MainFragment())
            .commit()
    }

    override fun setCreateListFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout, createListFragment)
            .commit()
    }

    override fun setProductsFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout, ProductsFragment())
            .commit()
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentLayout)

        if (currentFragment is MainFragment) {
            backButtonCount++
            if (backButtonCount == 1) {
                Toast.makeText(this, "Нажмите еще раз, чтобы выйти", Toast.LENGTH_SHORT).show()
            } else if (backButtonCount == 2) {
                super.onBackPressed()
                backButtonCount = 0
            }
        }
        else {
            backButtonCount = 0
            setMainFragment()
        }
    }
}