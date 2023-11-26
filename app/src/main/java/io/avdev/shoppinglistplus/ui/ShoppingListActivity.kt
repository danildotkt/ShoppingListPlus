package io.avdev.shoppinglistplus.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.avdev.domain.model.ShoppingList
import io.avdev.shoppinglistplus.R
import io.avdev.shoppinglistplus.ad.YandexBanner
import io.avdev.shoppinglistplus.adapter.ShoppingListAdapter
import io.avdev.shoppinglistplus.databinding.ActivityAppBinding
import io.avdev.shoppinglistplus.ui.createlist.CreateListFragment
import io.avdev.shoppinglistplus.ui.main.MainFragment
import io.avdev.shoppinglistplus.ui.products.ProductsFragment
import io.avdev.shoppinglistplus.ui.renamelist.RenameListFragment
import io.avdev.shoppinglistplus.utils.extensions.navigationCounter
import io.avdev.shoppinglistplus.utils.listeners.FragmentNavigationClickListener
import javax.inject.Inject


@AndroidEntryPoint
class ShoppingListActivity : AppCompatActivity(), FragmentNavigationClickListener {
    @Inject lateinit var banner: YandexBanner
    @Inject lateinit var adapter: ShoppingListAdapter
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
            .replace(R.id.fragmentLayout, MainFragment())
            .commit()
    }

    override fun setCreateListFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout, CreateListFragment())
            .commit()
    }

    override fun setProductsFragment(sList : ShoppingList) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout, ProductsFragment(sList))
            .commit()
    }

    override fun setUpdateNameFragment(list : ShoppingList) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout, RenameListFragment(list))
            .commit()
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentLayout)

        if (currentFragment is MainFragment) {
            navigationCounter++
            if (navigationCounter == 1) {
                Toast.makeText(this, "Нажмите еще раз, чтобы выйти", Toast.LENGTH_SHORT).show()
            } else if (navigationCounter == 2) {
                super.onBackPressed()
                navigationCounter = 0
            }
        }
        else {
            navigationCounter = 0
            setMainFragment()
        }
    }
}