package com.akshatsahijpal.blockchainproject.ui.PostCreator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.akshatsahijpal.blockchainproject.data.Block
import com.akshatsahijpal.blockchainproject.databinding.ChatCreatorFragmentBinding
import com.akshatsahijpal.blockchainproject.util.Constants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ChatCreatorFragment : Fragment() {

    private lateinit var _binding: ChatCreatorFragmentBinding
    private lateinit var navController: NavController
    private val model by viewModels<ChatCreatorViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = ChatCreatorFragmentBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        navController = Navigation.findNavController(view)
        _binding.apply {
            postButton.setOnClickListener {
                progressBarForPhoto.isVisible = true
                uploadBlockAndVerify(account)
            }
            userName.setText(account.displayName)
            if (account.photoUrl == null) {
                Picasso.get().load(Constants.DEFAULT_PROFILE_PIC).into(profilePictureOfUser)
            }else{
                Picasso.get().load(account.photoUrl ).into(profilePictureOfUser)
            }
        }
    }
    private fun uploadBlockAndVerify(account: GoogleSignInAccount?) {
        model.addBlock(
            Block(
                message = "${_binding.mainChatParagraph.text}",
                userPhotoUrl = account?.photoUrl.toString()?:Constants.DEFAULT_PROFILE_PIC,
                userName = account?.displayName,
                nonce = 1,
                time = Calendar.getInstance().time.toString()
            )
        )
        model.liveData.observe(viewLifecycleOwner) {
            if (it != null) {
               _binding.progressBarForPhoto.isVisible = false
                closeWindow()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Something went horribly wrong ",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun closeWindow() {
        navController.popBackStack()
    }
}