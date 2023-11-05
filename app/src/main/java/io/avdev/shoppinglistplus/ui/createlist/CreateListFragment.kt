package io.avdev.shoppinglistplus.ui.createlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import io.avdev.domain.model.ShoppingList
import io.avdev.domain.usecase.list.AddListUseCase
import io.avdev.shoppinglistplus.R
import io.avdev.shoppinglistplus.adapter.ShoppingListAdapter
import io.avdev.shoppinglistplus.databinding.FragmentCreateListBinding
import io.avdev.shoppinglistplus.ui.main.MainFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CreateListFragment : Fragment() {
    private lateinit var binding: FragmentCreateListBinding
    @Inject lateinit var shoppingAdapter: ShoppingListAdapter
    @Inject lateinit var addListUseCase : AddListUseCase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCreateListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickCreate()
    }

    private fun onClickCreate() = with(binding){
        buttonCreateList.setOnClickListener {
            val name = etListName.text
            val shoppingList = ShoppingList(null, name.toString(), null)
            CoroutineScope(Dispatchers.IO).launch {
                addListUseCase.execute(shoppingList)
            }
            shoppingAdapter.addShoppingList(shoppingList)
            moveToStartFragment()
            etListName.text = null
        }
    }
    private fun moveToStartFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout, MainFragment.getInstance())
            .commit()
    }

    companion object {
        private var instance: CreateListFragment? = null

        fun getInstance(): CreateListFragment {
            if (instance == null) {
                instance = CreateListFragment()
            }
            return instance as CreateListFragment
        }
    }

}