package com.akshatsahijpal.blockchainproject.repository.HomeRepository
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.akshatsahijpal.blockchainproject.data.Block
import com.akshatsahijpal.blockchainproject.util.Constants
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
const val STARTING_PAGE = 1

class HomeDataSource : PagingSource<Int, Block>() {
    private val db = Firebase.firestore
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Block> {
        var position = params.key ?: STARTING_PAGE
        return try {
            val snap = db.collection(Constants.CHAIN).orderBy("timeStamp", Query.Direction.ASCENDING)
                .get().await()
            val response = snap.documents
            val dataSet = mutableListOf<Block>()
            for (i in response) {
                i.toObject(Block::class.java)?.let { dataSet.add(it) }
            }
            LoadResult.Page(
                data = dataSet,
                prevKey = if (position == STARTING_PAGE) null else position - 1,
                nextKey = if (dataSet.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Block>): Int? {
        TODO("Not yet implemented")
    }
}