package com.akshatsahijpal.blockchainproject.ui.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.akshatsahijpal.blockchainproject.R
import com.akshatsahijpal.blockchainproject.adapter.HomeAdapter
import com.akshatsahijpal.blockchainproject.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var _binding: HomeFragmentBinding
    private lateinit var navController: NavController
    private val model by viewModels<HomeViewModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return _binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val adapter = HomeAdapter()
        _binding.apply {
            model.grabData()
            mainChatRecycler.setHasFixedSize(true)
            mainChatRecycler.adapter = adapter
            mainChatRecycler.layoutManager = LinearLayoutManager(requireContext())
            model.binder.observe(viewLifecycleOwner){
                it.observe(viewLifecycleOwner) { dt ->
                    adapter.submitData(viewLifecycleOwner.lifecycle, dt)
                }
            }
            addChatButton.setOnClickListener {
                onClickedChatAddButton()
            }
        }
    }
    private fun onClickedChatAddButton() {
        navController.navigate(R.id.action_homeFragment_to_chatCreatorFragment)
    }
}