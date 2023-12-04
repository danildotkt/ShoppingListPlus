package io.avdev.shoppinglistplus.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.avdev.shoppinglistplus.databinding.FragmentMainBinding
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    @Inject lateinit var factory: MainViewModel.Factory
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.provideMainViewModel(factory)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() = with(binding){
        rcListOfLists.layoutManager = LinearLayoutManager(context)
        rcListOfLists.adapter = viewModel.provideListAdapter()
    }

}