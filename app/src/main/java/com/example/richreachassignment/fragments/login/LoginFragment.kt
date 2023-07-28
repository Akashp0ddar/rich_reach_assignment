package com.example.richreachassignment.fragments.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.richreachassignment.MainViewModel
import com.example.richreachassignment.R
import com.example.richreachassignment.ViewModelFactory
import com.example.richreachassignment.databinding.FragmentLoginBinding
import com.example.richreachassignment.repository.Repository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignClient: GoogleSignInClient
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>
    private val viewModel by activityViewModels<MainViewModel> {
        ViewModelFactory(
            viewModelProviderRepository = Repository()
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        viewModel.initDataBase(context = requireContext())
        registerLaunchers()
        fireBaseSetup()
        onClick()
    }

    private fun registerLaunchers() {
        signInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val details = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    handleResult(details)
                } else {
                    Log.d(
                        "googleSignResult",
                        "registerLaunchers:${result.data} ${result.resultCode} ${
                            GoogleSignIn.getSignedInAccountFromIntent(
                                result.data
                            ).exception
                        }"
                    )
                }

            }
    }

    private fun handleResult(signedInAccountFromIntent: Task<GoogleSignInAccount>) {
        if (signedInAccountFromIntent.isSuccessful) {

            Toast.makeText(
                requireContext(),
                "${signedInAccountFromIntent.result.displayName}",
                Toast.LENGTH_SHORT
            ).show()


            val credential =
                GoogleAuthProvider.getCredential(signedInAccountFromIntent.result.idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener { taskAuthResult ->
                if (taskAuthResult.isSuccessful) {
                    findNavController().navigate(R.id.action_loginFragment_to_listFragment)
                } else {
                    Log.d("firebaseError", "handleResult:${taskAuthResult.exception} ")
                }
            }

        } else {
            Toast.makeText(
                requireContext(),
                "${signedInAccountFromIntent.exception}",
                Toast.LENGTH_SHORT
            ).show()

        }
    }


    private fun fireBaseSetup() {
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        googleSignClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun onClick() {

        binding.btnLogin.setOnClickListener {
            if (viewModel.getDetailsListFromDataBase().isNotEmpty()) {
                findNavController().navigate(R.id.action_loginFragment_to_listFragment)
            } else {
                signInGoogle()
            }
        }

    }

    private fun signInGoogle() {
        val signInIntent = googleSignClient.signInIntent
        signInLauncher.launch(signInIntent)
    }
}