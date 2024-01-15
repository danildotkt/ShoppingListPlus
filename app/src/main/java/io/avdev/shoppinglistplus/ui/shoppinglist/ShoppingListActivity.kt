package io.avdev.shoppinglistplus.ui.shoppinglist

import ShoppingListViewModel
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import io.avdev.domain.model.ShoppingList
import io.avdev.shoppinglistplus.R
import io.avdev.shoppinglistplus.databinding.ActivityAppBinding
import io.avdev.shoppinglistplus.ui.createlist.CreateListFragment
import io.avdev.shoppinglistplus.ui.main.MainFragment
import io.avdev.shoppinglistplus.ui.products.ProductsFragment
import io.avdev.shoppinglistplus.ui.renamelist.RenameListFragment
import io.avdev.shoppinglistplus.utils.listeners.FragmentNavigation
import javax.inject.Inject


@AndroidEntryPoint
class ShoppingListActivity : AppCompatActivity(), FragmentNavigation {
    @Inject
    lateinit var factory: ShoppingListViewModelFactory
    private var _binding: ActivityAppBinding? = null
    private var _viewModel: ShoppingListViewModel? = null
    private var counter = 0
    private val binding get() = _binding!!
    private val viewModel get() = _viewModel!!

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            handleBackButtonPress()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAppBinding.inflate(layoutInflater)
        _viewModel = ViewModelProvider(this, factory)[ShoppingListViewModel::class.java]
        setContentView(binding.root)
        setMainFragment()
        viewModel.adapter.setOnAddElementClickListener(this)
        setBanner()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.background)))
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

    override fun setProductsFragment(sList: ShoppingList) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
            .replace(R.id.fragmentLayout, ProductsFragment(sList))
            .commit()
    }

    override fun setRenameListFragment(sList: ShoppingList) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
            .replace(R.id.fragmentLayout, RenameListFragment(sList))
            .commit()
    }

    private fun setBanner() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.bannerLayout, viewModel.banner)
            .commit()
    }

    private fun handleBackButtonPress() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentLayout)

        if (currentFragment is MainFragment) {
            counter++
            if (counter == 1) {
                Toast.makeText(this, this.getText(R.string.press_again_to_exit), Toast.LENGTH_SHORT)
                    .show()
            } else if (counter == 2) {
                finish()
                counter = 0
            }
        } else {
            counter = 0
            setMainFragment()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopInterstitialAdTimer()
    }

    override fun onResume() {
        super.onResume()
        viewModel.resumeInterstitialAdTimer(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _viewModel = null
    }
}