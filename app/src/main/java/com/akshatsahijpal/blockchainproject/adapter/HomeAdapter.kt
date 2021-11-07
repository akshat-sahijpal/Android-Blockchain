package com.akshatsahijpal.blockchainproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akshatsahijpal.blockchainproject.data.Block
import com.akshatsahijpal.blockchainproject.databinding.ChatArchBinding
import com.akshatsahijpal.blockchainproject.util.Constants
import com.squareup.picasso.Picasso

class HomeAdapter : PagingDataAdapter<Block, HomeAdapter.Holder>(compareList) {
    companion object {
        val compareList = object : DiffUtil.ItemCallback<Block>() {
            override fun areItemsTheSame(oldItem: Block, newItem: Block): Boolean {
                return oldItem.itr == newItem.itr
            }

            override fun areContentsTheSame(oldItem: Block, newItem: Block): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class Holder(
        private var _bind: ChatArchBinding
    ) :
        RecyclerView.ViewHolder(_bind.root) {
        fun connect(post: Block) {
            _bind.apply {
                mainChatParagraph.text = "${post.message}"
                currentHashValue.text = "${post.currentBlockHash}"
                previousHashValue.text = "${post.previousHash}"
                timeStamp.text = "${post.time?.subSequence(0, 16)}"
                userName.text = post.userName
                if (post.userPhotoUrl == null) {
                    Picasso.get().load(Constants.DEFAULT_PROFILE_PIC).into(profilePictureOfUser)
                }else{
                    Picasso.get().load(post.userPhotoUrl).into(profilePictureOfUser)
                }
            }
        }
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentPost = getItem(position)
        if (currentPost != null) {
            holder.connect(currentPost)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            ChatArchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(view)
    }

}