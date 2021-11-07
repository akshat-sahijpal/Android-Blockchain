package com.akshatsahijpal.blockchainproject.ui.login

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.akshatsahijpal.blockchainproject.R
import com.akshatsahijpal.blockchainproject.databinding.LoginScreenFragmentBinding
import com.akshatsahijpal.blockchainproject.util.Constants.REQ
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginScreenFragment : Fragment() {
    private lateinit var _binding: LoginScreenFragmentBinding
    private lateinit var googleClient: GoogleSignInClient
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = LoginScreenFragmentBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        if (GoogleSignIn.getLastSignedInAccount(requireContext()) != null) {
            navController.navigate(R.id.action_loginScreenFragment_to_homeFragment)
        }
        _binding.apply {
            googleSignUpButton.setOnClickListener {
                googleClient = GoogleSignIn.getClient(requireActivity(), setupSignInOptions())
                signInWithGoogle()
            }
        }
    }

    private fun signInWithGoogle() {
        var intent = googleClient.signInIntent
        startActivityForResult(intent, REQ)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ) {
            var task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            HandleGoogleAccount(task)
        }
    }

    private fun HandleGoogleAccount(task: Task<GoogleSignInAccount>) =
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            Log.d(" Google Account ", account.toString())

            navController.navigate(R.id.action_loginScreenFragment_to_homeFragment)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            Log.w(ContentValues.TAG, "signInResult:failed code=" + e.statusCode)
            Toast.makeText(requireContext(), "Failed to Connect!!!", Toast.LENGTH_LONG).show()
        }

    private fun setupSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }

}