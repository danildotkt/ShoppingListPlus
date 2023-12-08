package io.avdev.shoppinglistplus.ui.renamelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.avdev.domain.model.ShoppingList
import io.avdev.shoppinglistplus.databinding.FragmentRenameListBinding
import io.avdev.shoppinglistplus.utils.extensions.moveToStartFragment
import javax.inject.Inject

@AndroidEntryPoint
class RenameListFragment(val shoppingList: ShoppingList ) : Fragment() {
    private var _binding: FragmentRenameListBinding? = null
    @Inject lateinit var factory: RenameListViewModel.Factory
    private val viewModel: RenameListViewModel by viewModels {
        RenameListViewModel.provideRenameListViewModel(factory)
    }

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRenameListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etListRename.setText(shoppingList.name)
        setButtons()
    }

    private fun setButtons() {
        onClickDone()
        onClickCreate()
    }

    private fun onClickCreate() = with(binding){
        buttonRenameList.setOnClickListener {
            viewModel.renameList(etListRename.text.toString(), shoppingList)
            moveToStartFragment()
        }
    }
    private fun onClickDone() = with(binding) {
        etListRename.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val newName = etListRename.text.toString()
                viewModel.renameList(newName, shoppingList)
                moveToStartFragment()
                true
            } else {
                false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}