package com.akshatsahijpal.blockchainproject.data

import com.akshatsahijpal.blockchainproject.crypto.toHash
import java.lang.StringBuilder

// Block Structure
data class Block(
    var currentBlockHash: String? = "",// Hash of current block
    var previousHash: String? = "",// Hash of prev block
    var message: String? = "", // data of current block
    var nonce: Int = 0,
    var timeStamp: Long = 0,
    var time: String? = "",
    var userName:String? = "",
    var userPhotoUrl:String? = "",
    var documentID: String? = "",
    var itr: Int = 0
) {
    init {
        generateHashForCurrentBlock()
        this.timeStamp = System.currentTimeMillis()
    }

    // GenerateHash Hash of current block = PrevHash + Hash(Data)
    private fun generateHashForCurrentBlock() {
        this.currentBlockHash = toHash(this.previousHash + message?.let { toHash(it) } + timeStamp + nonce + itr)
    }

    fun mineBlock(difficulty: Int) {
        this.nonce = 0
        while (currentBlockHash?.substring(0, difficulty) != appendZeros(difficulty)){
            nonce++
            generateHashForCurrentBlock()
        }
    }
}

internal fun appendZeros(difficulty: Int) :String {
    val construct = StringBuilder()
    for (i in 0..difficulty){
        construct.append('0')
    }
    return construct.toString()
}