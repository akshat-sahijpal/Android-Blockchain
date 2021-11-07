package com.akshatsahijpal.blockchainproject.repository.HomeRepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeScreenRepository {
    fun constructRecycler() =
        Pager(config = PagingConfig(pageSize = 1, maxSize = 3, enablePlaceholders = false), pagingSourceFactory = { HomeDataSource() }).liveData
}