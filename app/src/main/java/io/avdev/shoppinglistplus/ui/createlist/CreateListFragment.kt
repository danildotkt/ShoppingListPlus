package io.avdev.shoppinglistplus.ui.createlist

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
import io.avdev.shoppinglistplus.databinding.FragmentCreateListBinding
import io.avdev.shoppinglistplus.utils.extensions.moveToStartFragment
import javax.inject.Inject

@AndroidEntryPoint
class CreateListFragment : Fragment() {
    private var _binding: FragmentCreateListBinding? = null
    @Inject lateinit var factory: CreateListViewModel.Factory
    private val viewModel: CreateListViewModel by viewModels {
        CreateListViewModel.provideCreateListViewModel(factory)
    }

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCreateListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtons()
    }
    private fun setButtons() {
        onClickDoneListener()
        onClickCreateListener()
    }

    private fun onClickCreateListener() = with(binding){
        initKeyboard()
        buttonCreateList.setOnClickListener {
            handleListCreation()
        }
    }
    private fun initKeyboard() = with(binding){
        etListName.requestFocus()
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(etListName, InputMethodManager.SHOW_IMPLICIT)
    }
    private fun onClickDoneListener() = with(binding) {
        etListName.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                handleListCreation()
                true
            } else {
                false
            }
        }
    }
    private fun handleListCreation() {
        val newName = binding.etListName.text.toString()
        viewModel.createShoppingList(newName)
        moveToStartFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}