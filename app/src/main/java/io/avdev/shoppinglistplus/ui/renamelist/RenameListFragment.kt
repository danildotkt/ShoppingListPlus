package io.avdev.shoppinglistplus.ui.renamelist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.avdev.domain.model.ShoppingList
import io.avdev.shoppinglistplus.databinding.FragmentRenameListBinding
import io.avdev.shoppinglistplus.utils.extensions.moveToStartFragment
import javax.inject.Inject

@AndroidEntryPoint
class RenameListFragment(val list : ShoppingList) : Fragment() {
    private lateinit var binding: FragmentRenameListBinding
    @Inject lateinit var factory: RenameListViewModel.Factory
    private val viewModel: RenameListViewModel by viewModels {
        RenameListViewModel.provideRenameListViewModel(factory)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRenameListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etListRename.setText(list.name)
        setButtons()
    }

    private fun setButtons() {
        onClickDone()
        onClickCreate()
    }

    private fun onClickCreate() = with(binding){
        initKeyboard()
        buttonRenameList.setOnClickListener {
            viewModel.renameList(etListRename.text.toString(), list)
            moveToStartFragment()
        }
    }
    private fun onClickDone() = with(binding) {
        etListRename.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val newName = etListRename.text.toString()
                viewModel.renameList(newName, list)
                moveToStartFragment()
                true
            } else {
                false
            }
        }
    }

    private fun initKeyboard() = with(binding){
        etListRename.requestFocus()
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(etListRename, InputMethodManager.SHOW_IMPLICIT)
    }
}