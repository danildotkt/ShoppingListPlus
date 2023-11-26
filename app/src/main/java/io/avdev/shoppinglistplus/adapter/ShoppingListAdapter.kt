package io.avdev.shoppinglistplus.adapter

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import io.avdev.domain.model.ShoppingList
import io.avdev.shoppinglistplus.R
import io.avdev.shoppinglistplus.databinding.ItemShoppingListBinding
import io.avdev.shoppinglistplus.service.ShoppingListService
import io.avdev.shoppinglistplus.utils.listeners.FragmentNavigationClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ShoppingListAdapter(private val listService : ShoppingListService) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var shoppingLists = mutableListOf<ShoppingList>()
    private var fragmentNavigationClickListener: FragmentNavigationClickListener?  = null

    init {
        CoroutineScope(Dispatchers.IO).launch {
            shoppingLists.addAll(listService.selectLists())
            withContext(Dispatchers.Main) {
                notifyDataSetChanged()
            }
        }
    }

    fun setOnAddElementClickListener(listener: FragmentNavigationClickListener) {
        fragmentNavigationClickListener = listener
    }

    private fun deleteShoppingList(position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            listService.deleteList(shoppingLists[position])
            shoppingLists.removeAt(position)
            withContext(Dispatchers.Main) {
                notifyItemRemoved(position)
            }
        }
    }
    private fun updateShoppingList(position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            listService.updateList(shoppingLists[position])
            withContext(Dispatchers.Main) {
                notifyItemChanged(position)
            }
        }
    }
    fun addShoppingList(list: ShoppingList) {
        shoppingLists.add(list)
        notifyDataSetChanged()
    }

    inner class ShoppingListHolder(view: View) : RecyclerView.ViewHolder(view), PopupMenu.OnMenuItemClickListener {
        private val binding = ItemShoppingListBinding.bind(view)
        fun bind(item: ShoppingList) = with(binding) {
            tvListName.text = item.name
            progressBar.setProgress(40, true)
            bMore.setOnClickListener { showPopupMenu(it) }
        }

        private fun showPopupMenu(view: View) {
            val style = ContextThemeWrapper(view.context, R.style.PopupMenuStyle)
            val popupMenu = PopupMenu(style, view)
            popupMenu.inflate(R.menu.menu_popup)
            popupMenu.setOnMenuItemClickListener(this)
            popupMenu.show()
        }

        override fun onMenuItemClick(item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.menu_delete -> {
                    deleteShoppingList(absoluteAdapterPosition)
                    true
                }
                R.id.menu_update -> {
                    fragmentNavigationClickListener?.setUpdateNameFragment(shoppingLists[absoluteAdapterPosition])
                    true
                }
                else -> false
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
                    fragmentNavigationClickListener?.setCreateListFragment()
                }
                holder
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shopping_list, parent, false)
                val holder = ShoppingListHolder(view)
                holder.itemView.setOnClickListener {
                    fragmentNavigationClickListener?.setProductsFragment(shoppingLists[holder.absoluteAdapterPosition])
                    notifyDataSetChanged()
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




