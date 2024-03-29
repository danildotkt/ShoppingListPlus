package io.avdev.shoppinglistplus.adapter

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.avdev.domain.model.ShoppingList
import io.avdev.domain.usecase.item.ClearSelectionUseCase
import io.avdev.domain.usecase.item.GetProductsByListIdUseCase
import io.avdev.domain.usecase.item.GetSelectedProductCountUseCase
import io.avdev.domain.usecase.item.GetTotalProductCountUseCase
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


class ShoppingListAdapter(
    getListsUseCase: GetListsUseCase,
    private val getSelectedProductCountUseCase: GetSelectedProductCountUseCase,
    private val getTotalProductCountUseCase: GetTotalProductCountUseCase,
    private val deleteListUseCase: DeleteListUseCase,
    private val clearSelectionUseCase: ClearSelectionUseCase,
    private val getProductsByListIdUseCase: GetProductsByListIdUseCase
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

    inner class ShoppingListHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemShoppingListBinding.bind(view)
        fun bind(item: ShoppingList) = with(binding) {
            tvListName.text = item.name
            getProgress(item)
            bMore.setOnClickListener { showDialog(item) }
        }

        private fun getProgress(list: ShoppingList) {
            CoroutineScope(Dispatchers.Default).launch {
                val id = list.id
                val selectedItemCountFlow = getSelectedProductCountUseCase.execute(id)
                val totalItemCountFlow = getTotalProductCountUseCase.execute(id)

                selectedItemCountFlow.zip(totalItemCountFlow) { selectedItemCount, totalItemCount ->
                    withContext(Dispatchers.Main) {
                        binding.tvSelectedItem.text = selectedItemCount.toString()
                        binding.progressBar.max = totalItemCount
                        binding.progressBar.setProgress(selectedItemCount, true)
                        binding.tvItems.text = totalItemCount.toString()
                    }
                }.collect {}
            }
        }

        private fun showDialog(item: ShoppingList) {
            val dialog = createDialog()
            setupDialogViews(dialog, item)
            setListeners(dialog)
            showDialogWithAnimation(dialog)
        }

        private fun createDialog(): Dialog {
            val dialog = Dialog(itemView.context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_layout)
            return dialog
        }

        private fun setupDialogViews(dialog: Dialog, item: ShoppingList) {
            val dialogName: TextView = dialog.findViewById(R.id.dialogname)
            dialogName.text = item.name
        }

        private fun setListeners(dialog: Dialog) {
            val renameLayout: LinearLayout = dialog.findViewById(R.id.layoutRename)
            val resetLayout: LinearLayout = dialog.findViewById(R.id.layoutReset)
            val shareLayout: LinearLayout = dialog.findViewById(R.id.layoutShare)
            val deleteLayout: LinearLayout = dialog.findViewById(R.id.layoutDelete)

            renameLayout.setOnClickListener {
                dialog.dismiss()
                fragmentNavigation.setRenameListFragment(shoppingLists[absoluteAdapterPosition])
            }

            resetLayout.setOnClickListener {
                dialog.dismiss()
                clearShoppingListFlags(absoluteAdapterPosition)
            }

            shareLayout.setOnClickListener {
                shareShoppingList(absoluteAdapterPosition)
            }

            deleteLayout.setOnClickListener {
                dialog.dismiss()
                deleteShoppingList(absoluteAdapterPosition)
            }
        }

        private fun showDialogWithAnimation(dialog: Dialog) {
            dialog.show()
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
            dialog.window?.setGravity(Gravity.BOTTOM)
        }

        private fun shareShoppingList(position: Int) {
            CoroutineScope(Dispatchers.Main).launch {
                val itemList = withContext(Dispatchers.Default) {
                    getItemListText(shoppingLists[position].id)
                }

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, itemList)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                itemView.context.startActivity(shareIntent)
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

    private suspend fun getItemListText(id: Int): String {
        val itemList = StringBuilder()
        val items = getProductsByListIdUseCase.execute(id)
        for ((index, item) in items.withIndex()) {
            itemList.append("${index + 1}. ${item.name} \n")
        }
        return itemList.toString()
    }

    inner class AddElementButtonHolder(view: View) : RecyclerView.ViewHolder(view)

    enum class ListItemType {
        ELEMENT_ADD,
        SHOPPING_LIST
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ListItemType.entries[viewType]) {
            ListItemType.ELEMENT_ADD -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_newlist, parent, false)
                val holder = AddElementButtonHolder(view)
                holder.itemView.setOnClickListener {
                    fragmentNavigation.setCreateListFragment()
                }
                holder
            }

            ListItemType.SHOPPING_LIST -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_shopping_list, parent, false)
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
            ListItemType.ELEMENT_ADD.ordinal
        } else {
            ListItemType.SHOPPING_LIST.ordinal
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




