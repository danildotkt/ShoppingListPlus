package io.avdev.shoppinglistplus.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.avdev.shoppinglistplus.adapter.ShoppingListAdapter
import io.avdev.shoppinglistplus.anim.ItemTouchHelperCallback
import io.avdev.shoppinglistplus.databinding.FragmentMainBinding
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    @Inject lateinit var shoppingAdapter: ShoppingListAdapter
    @Inject lateinit var touchHelper: ItemTouchHelperCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() = with(binding){
        rcListOfLists.layoutManager = LinearLayoutManager(context)
        rcListOfLists.adapter = shoppingAdapter
        initAnimation(rcListOfLists)
    }

    private fun initAnimation(rc : RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(touchHelper)
        itemTouchHelper.attachToRecyclerView(rc)
    }
    companion object {
        private var instance: MainFragment? = null

        fun getInstance(): MainFragment {
            if (instance == null) {
                instance = MainFragment()
            }
            return instance as MainFragment
        }
    }
}