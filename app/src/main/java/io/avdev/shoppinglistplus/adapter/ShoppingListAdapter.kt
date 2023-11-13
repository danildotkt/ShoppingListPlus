package io.avdev.shoppinglistplus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.avdev.domain.model.ShoppingList
import io.avdev.shoppinglistplus.R
import io.avdev.shoppinglistplus.databinding.ItemNewlistBinding
import io.avdev.shoppinglistplus.databinding.ItemShoppingListBinding
import io.avdev.shoppinglistplus.service.ShoppingListService
import io.avdev.shoppinglistplus.utils.listeners.FragmentNavClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ShoppingListAdapter(private val listService : ShoppingListService) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var shoppingList = mutableListOf<ShoppingList>()
    private var fragmentNavClickListener: FragmentNavClickListener?  = null
    private val VIEW_TYPE_ADD_ELEMENT = 0
    private val VIEW_TYPE_SHOPPING_LIST = 1

    init {
        CoroutineScope(Dispatchers.IO).launch {
            shoppingList.addAll(listService.selectLists())
            withContext(Dispatchers.Main) {
                notifyDataSetChanged()
            }
        }
    }
    fun setOnAddElementClickListener(listener: FragmentNavClickListener) {
        fragmentNavClickListener = listener
    }
    private fun deleteShoppingList(position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            listService.deleteList(shoppingList[position])
            shoppingList.removeAt(position)
            withContext(Dispatchers.Main) {
                notifyItemRemoved(position)
            }
        }
    }
    fun addShoppingList(item: ShoppingList) {
        shoppingList.add(item)
//        itemAdapter.setList(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ADD_ELEMENT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_newlist, parent, false)
                val holder = AddElementButtonHolder(view)
                holder.itemView.setOnClickListener {
                    fragmentNavClickListener?.setCreateListFragment()
                }
                holder
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shopping_list, parent, false)
                val holder = ShoppingListHolder(view)
                holder.itemView.setOnClickListener {
                    fragmentNavClickListener?.setProductsFragment()
//                    itemAdapter.setList(shoppingList[holder.absoluteAdapterPosition])
                    notifyDataSetChanged()
                }
                holder
            }
        }
    }

    override fun getItemCount(): Int {
        return shoppingList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == shoppingList.size) {
            VIEW_TYPE_ADD_ELEMENT
        } else {
            VIEW_TYPE_SHOPPING_LIST
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ShoppingListHolder -> {
                holder.bind(shoppingList[position])
            }
            is AddElementButtonHolder -> {
            }
        }
    }

    inner class ShoppingListHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemShoppingListBinding.bind(view)
        fun bind(item: ShoppingList) = with(binding) {
            tvListName.text = item.name
            progressBar.setProgress(40, true)
            bMore.setOnClickListener {
                deleteShoppingList(absoluteAdapterPosition)
            }
        }
    }
    inner class AddElementButtonHolder(view: View) : RecyclerView.ViewHolder(view)

}




