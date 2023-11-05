package io.avdev.shoppinglistplus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import io.avdev.domain.model.ShoppingList
import io.avdev.domain.usecase.list.DeleteListUseCase
import io.avdev.domain.usecase.list.SelectListsUseCase
import io.avdev.shoppinglistplus.R
import io.avdev.shoppinglistplus.anim.AdapterAnimExtension
import io.avdev.shoppinglistplus.anim.ItemTouchHelperCallback
import io.avdev.shoppinglistplus.databinding.ItemSlistBinding
import io.avdev.shoppinglistplus.utils.listeners.OnAddElementClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ShoppingListAdapter(private val selectListUseCase: SelectListsUseCase, private val deleteListUseCase: DeleteListUseCase) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onAddElementClickListener: OnAddElementClickListener? = null

    fun setOnAddElementClickListener(listener: OnAddElementClickListener) {
        onAddElementClickListener = listener
    }


    private fun deleteItem(position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            deleteListUseCase.execute(shoppingList[position])
            withContext(Dispatchers.Main) {
                notifyItemRemoved(position)
                notifyDataSetChanged()
            }
        }
    }

    private val VIEW_TYPE_ADD_ELEMENT = 0
    private val VIEW_TYPE_SHOPPING_LIST = 1

    private var shoppingList = mutableListOf<ShoppingList>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            shoppingList.addAll(selectListUseCase.execute())
            withContext(Dispatchers.Main) {
                notifyDataSetChanged()
            }
        }
    }

    fun addShoppingList(item: ShoppingList) {
        shoppingList.add(item)
        val insertedIndex = shoppingList.indexOf(item)
        notifyItemInserted(insertedIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ADD_ELEMENT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_new, parent, false)
                val holder = AddElementButtonHolder(view)
//                holder.itemView.setOnClickListener {
//                    onAddElementClickListener?.onAddElementClick()
//                }
                holder
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slist, parent, false)
                val holder = ShoppingListHolder(view)
//                holder.itemView.setOnClickListener {
//                }
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

}




class ShoppingListHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemSlistBinding.bind(view)

    fun bind(item: ShoppingList) = with(binding) {
        tvListName.text = item.name
        progressBar.setProgress(40, true)
    }
}

class AddElementButtonHolder(view: View) : RecyclerView.ViewHolder(view) {

}