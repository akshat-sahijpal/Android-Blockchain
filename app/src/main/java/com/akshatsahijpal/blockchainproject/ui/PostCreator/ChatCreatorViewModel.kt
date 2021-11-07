package com.akshatsahijpal.blockchainproject.ui.PostCreator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshatsahijpal.blockchainproject.data.Block
import com.akshatsahijpal.blockchainproject.repository.PostCreatorRepository.PostCreatorRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class ChatCreatorViewModel @Inject constructor(
    private var repo: PostCreatorRepo
): ViewModel() {
    private var _liveData: MutableLiveData<String> = MutableLiveData()
    var liveData: LiveData<String> = _liveData
    fun addBlock(bloc: Block){
        viewModelScope.async {
            var ref = repo.upload(bloc)
            if(ref?.path!=null){
                repo.updateWithID(ref.path.split("/")[1])
                repo.updatePreviousHashValue()
                _liveData.value = ref.path
            }
        }
    }
}