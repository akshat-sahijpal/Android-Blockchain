package com.akshatsahijpal.blockchainproject.crypto

import java.lang.StringBuilder
import java.nio.charset.StandardCharsets.UTF_8
import java.security.MessageDigest

/**
 * converts a message to hash value using sha-256 function
 */
fun toHash(message: String): String  {
    val bArr =  MessageDigest.getInstance("SHA-256").digest(message.toByteArray(UTF_8))
    val hashedString: StringBuilder = StringBuilder()
    for(i in bArr){
        val stringHex = Integer.toHexString(0xff + i) // Type cast Byte to int
        if(stringHex.length == 1){
            hashedString.append('0')
        }
        hashedString.append(stringHex)
    }
    return hashedString.toString()
}