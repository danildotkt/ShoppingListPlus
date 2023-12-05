package io.avdev.shoppinglistplus.adapter

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import io.avdev.domain.model.ShoppingList
import io.avdev.domain.usecase.item.ClearSelectionUseCase
import io.avdev.domain.usecase.item.GetSelectedItemCountUseCase
import io.avdev.domain.usecase.item.GetTotalItemCountUseCase
import io.avdev.domain.usecase.list.DeleteListUseCase
import io.avdev.domain.usecase.list.GetListsUseCase
import io.avdev.shoppinglistplus.R
import io.avdev.shoppinglistplus.databinding.ItemShoppingListBinding
import io.avdev.shoppinglistplus.utils.listeners.FragmentNavigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SListAdapter (getListsUseCase: GetListsUseCase,
    private val getSelectedItemCountUseCase: GetSelectedItemCountUseCase,
    private val getTotalItemCountUseCase: GetTotalItemCountUseCase,
    private val deleteListUseCase: DeleteListUseCase,
    private val clearSelectionUseCase: ClearSelectionUseCase) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var shoppingLists = mutableListOf<ShoppingList>()
    private lateinit var fragmentNavigation: FragmentNavigation


    init {
        val flow = getListsUseCase.execute()
        flow.onEach { newList ->
            shoppingLists.addAll(newList.filter { !shoppingLists.contains(it) })
        }.launchIn(CoroutineScope(Dispatchers.Default))
    }

    fun setOnAddElementClickListener(listener: FragmentNavigation) {
        fragmentNavigation = listener
    }

    inner class ShoppingListHolder(view: View) : RecyclerView.ViewHolder(view), PopupMenu.OnMenuItemClickListener {
        private val binding = ItemShoppingListBinding.bind(view)
        fun bind(item: ShoppingList) = with(binding) {
            tvListName.text = item.name
            getProgress(item)
            loadInterstitialAd(item)
            bMore.setOnClickListener { showPopupMenu(it) }
        }

        private fun getProgress(list: ShoppingList) {
            CoroutineScope(Dispatchers.Default).launch {
                val id = list.id
                val selectedItemCountFlow = getSelectedItemCountUseCase.execute(id)
                val totalItemCountFlow = getTotalItemCountUseCase.execute(id)

                selectedItemCountFlow.zip(totalItemCountFlow) { selectedItemCount, totalItemCount ->
                    withContext(Dispatchers.Main) {
                        binding.tvSelectedItem.text = selectedItemCount.toString()
                        binding.progressBar.max = totalItemCount
                        binding.progressBar.setProgress(selectedItemCount, true)
                        binding.tvItems.text = totalItemCount.toString()
                    }
                }.collect{}
            }
        }

        private fun loadInterstitialAd(list : ShoppingList) {
            val selectedItemsSet = HashSet<Int>()

            CoroutineScope(Dispatchers.Default).launch {
                val id = list.id
                val selectedItemCountFlow = getSelectedItemCountUseCase.execute(id)
                selectedItemCountFlow.collect{ selectedItemCount ->
                    selectedItemsSet.add(selectedItemCount)
                    if (selectedItemsSet.size >= 25) {
                        fragmentNavigation.setInterstitialAd()
                        selectedItemsSet.clear()
                    }
                }
            }
        }

        private fun showPopupMenu(view: View) {
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.inflate(R.menu.menu_popup)
            popupMenu.setOnMenuItemClickListener(this)
            popupMenu.show()
        }

        override fun onMenuItemClick(item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.menu_rename -> {
                    fragmentNavigation.setUpdateNameFragment(shoppingLists[absoluteAdapterPosition])
                    true
                }
                R.id.menu_reset -> {
                    clearShoppingListFlags(absoluteAdapterPosition)
                    true
                }
                R.id.menu_delete -> {
                    deleteShoppingList(absoluteAdapterPosition)
                    true
                }
                else -> false
            }
        }
    }
    private fun deleteShoppingList(position: Int) {
        CoroutineScope(Dispatchers.Default).launch {
            deleteListUseCase.execute(shoppingLists[position])
            withContext(Dispatchers.Main) {
                shoppingLists.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }
    private fun clearShoppingListFlags(position: Int) {
        CoroutineScope(Dispatchers.Default).launch {
            clearSelectionUseCase.execute(shoppingLists[position].id)
            withContext(Dispatchers.Main) {
                notifyItemChanged(position)
            }
        }

    }

    inner class AddElementButtonHolder(view: View) : RecyclerView.ViewHolder(view)

    private val VIEW_TYPE_ADD_ELEMENT = 0
    private val VIEW_TYPE_SHOPPING_LIST = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ADD_ELEMENT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_newlist, parent, false)
                val holder = AddElementButtonHolder(view)
                holder.itemView.setOnClickListener {
                    fragmentNavigation.setCreateListFragment()
                }
                holder
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shopping_list, parent, false)
                val holder = ShoppingListHolder(view)
                holder.itemView.setOnClickListener {
                    fragmentNavigation.setProductsFragment(shoppingLists[holder.absoluteAdapterPosition])
                }
                holder
            }
        }
    }


    override fun getItemCount(): Int {
        return shoppingLists.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == shoppingLists.size) {
            VIEW_TYPE_ADD_ELEMENT
        } else {
            VIEW_TYPE_SHOPPING_LIST
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ShoppingListHolder -> {
                holder.bind(shoppingLists[position])
            }
            is AddElementButtonHolder -> {
            }
        }
    }

}




