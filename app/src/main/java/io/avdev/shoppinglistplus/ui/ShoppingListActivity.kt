package io.avdev.shoppinglistplus.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.avdev.shoppinglistplus.R
import io.avdev.shoppinglistplus.ad.YandexBanner
import io.avdev.shoppinglistplus.adapter.ShoppingListAdapter
import io.avdev.shoppinglistplus.databinding.ActivityAppBinding
import io.avdev.shoppinglistplus.ui.createlist.CreateListFragment
import io.avdev.shoppinglistplus.ui.main.MainFragment
import io.avdev.shoppinglistplus.ui.products.ProductsFragment
import io.avdev.shoppinglistplus.utils.listeners.OnAddElementClickListener
import javax.inject.Inject


@AndroidEntryPoint
class ShoppingListActivity : AppCompatActivity(), OnAddElementClickListener {
    @Inject lateinit var banner: YandexBanner
    @Inject lateinit var adapter: ShoppingListAdapter
    private lateinit var listOfLists: MainFragment
    private lateinit var binding: ActivityAppBinding

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
            .replace(R.id.fragmentLayout, MainFragment.getInstance())
            .commit()
    }

    override fun onAddElementClick() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout, CreateListFragment.getInstance())
            .commit()
    }

    override fun onOpenElementClick() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout, ProductsFragment.getInstance())
            .commit()    }
}