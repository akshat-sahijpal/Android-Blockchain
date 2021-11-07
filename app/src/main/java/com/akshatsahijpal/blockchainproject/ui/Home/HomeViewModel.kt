package com.akshatsahijpal.blockchainproject.ui.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.akshatsahijpal.blockchainproject.data.Block
import com.akshatsahijpal.blockchainproject.repository.HomeRepository.HomeScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject  constructor(
    private val repo: HomeScreenRepository
) : ViewModel() {
    private var _binder: MutableLiveData<LiveData<PagingData<Block>>> = MutableLiveData()
    var binder: LiveData<LiveData<PagingData<Block>>> = _binder
    fun grabData() {
        _binder.value = repo.constructRecycler().cachedIn(viewModelScope)
    }
}