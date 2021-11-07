package com.akshatsahijpal.blockchainproject.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.akshatsahijpal.blockchainproject.databinding.DetailScreenFragmentBinding

class DetailScreenFragment : Fragment() {
    private lateinit var _binding: DetailScreenFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DetailScreenFragmentBinding.inflate(inflater, container, false)
        return _binding.root
    }
}