package io.avdev.shoppinglistplus.ui.renamelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import io.avdev.domain.model.ShoppingList
import io.avdev.shoppinglistplus.databinding.FragmentRenameListBinding
import io.avdev.shoppinglistplus.utils.extensions.moveToStartFragment
import javax.inject.Inject

@AndroidEntryPoint
class RenameListFragment(val shoppingList: ShoppingList) : Fragment() {
    private var _binding: FragmentRenameListBinding? = null
    private var _viewModel: RenameListViewModel? = null

    @Inject
    lateinit var factory: RenameListViewModelFactory


    private val binding get() = _binding!!
    private val viewModel get() = _viewModel!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRenameListBinding.inflate(inflater, container, false)
        _viewModel = ViewModelProvider(this, factory)[RenameListViewModel::class.java]
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

    private fun onClickCreate() = with(binding) {
        buttonRenameList.setOnClickListener {
            viewModel.renameList(etListRename.text.toString(), shoppingList)
            moveToStartFragment()
        }
    }

    private fun onClickDone() {
        binding.etListRename.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val newName = binding.etListRename.text.toString()
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
        _viewModel = null
    }
}