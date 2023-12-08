package io.avdev.shoppinglistplus.utils.extensions

import androidx.fragment.app.Fragment
import io.avdev.shoppinglistplus.R
import io.avdev.shoppinglistplus.ui.main.MainFragment

fun Fragment.moveToStartFragment() {
    requireActivity().supportFragmentManager.beginTransaction()
        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
        .replace(R.id.fragmentLayout, MainFragment())
        .commit()
}