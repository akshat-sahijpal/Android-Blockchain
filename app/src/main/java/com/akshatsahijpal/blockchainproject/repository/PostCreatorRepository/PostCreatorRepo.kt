package com.akshatsahijpal.blockchainproject.repository.PostCreatorRepository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.akshatsahijpal.blockchainproject.data.Block
import com.akshatsahijpal.blockchainproject.util.Constants
import com.akshatsahijpal.blockchainproject.util.Constants.HASH_PREF
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await

class PostCreatorRepo constructor(private var cont: Context) {
    private val db = Firebase.firestore
    private val Context.dataWare by preferencesDataStore("hashvalue")
    suspend fun upload(uplData: Block): DocumentReference? {
         uplData.currentBlockHash?.let { saveOnServer(it) }
       uplData.previousHash = read(HASH_PREF) ?: readFromServer()
       uplData.currentBlockHash?.let { save(HASH_PREF, it) }
        return try {
            db.collection(Constants.CHAIN)
                .add(uplData).await()
        } catch (e: Exception) {
            null
        }
    }
    suspend fun save(key:String, value:String){
        val dataStoreKey = stringPreferencesKey(key)
        cont.dataWare.edit { hash ->
            hash[dataStoreKey] = value
        }
    }
    suspend fun saveOnServer(currentHash: String) {
        db.collection("CURRENT").document(Constants.CURRENT_PREV_HASH).update("hash", currentHash).await()
    }
    suspend fun readFromServer() :String {
        var res = db.collection("CURRENT").document(Constants.CURRENT_PREV_HASH).get().await()["hash"].toString()
        Log.d("Result:", res)
        return res
    }
    suspend fun read(key:String) :String? {
        val dataStoreKey = stringPreferencesKey(key)
        val pref = cont.dataWare.data.first()
        return pref[dataStoreKey]
    }
    suspend fun updateWithID(uid: String) {
        db.collection(Constants.CHAIN).document(uid).update("documentID", uid).await()
    }

    suspend fun updatePreviousHashValue() {
        val snap = db.collection(Constants.CHAIN)
            .get().await()
        var response = snap.documents
        val dataSet = mutableListOf<Block>()
        for (i in response) {
            i.toObject(Block::class.java)?.let { dataSet.add(it) }
        }
        dataSet[dataSet.size - 1].documentID?.let {
            db.collection(Constants.CHAIN).document(it)
                .update("previousHash", dataSet[dataSet.size - 2].currentBlockHash).await()
        }

    }
}