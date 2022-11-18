package com.aziza.locationreminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aziza.locationreminder.databinding.FragmentAuthenticationBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth


class AuthenticationFragment : Fragment() {
    private lateinit var binding: FragmentAuthenticationBinding
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result: FirebaseAuthUIAuthenticationResult? ->
        handelAuthResponse(result)
    }

    private fun handelAuthResponse(result: FirebaseAuthUIAuthenticationResult?) {
        //how to check if it successfully?
        Toast.makeText(requireContext(), "result${result?.resultCode}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthenticationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                // already signed in
                findNavController().navigate(R.id.reminderListFragment)
            } else {
                launchSignInFlow()
            }
        }
    }

    private fun launchSignInFlow() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTheme(R.style.Theme_LocationReminder)
            .setLogo(R.drawable.map)
            .build()
        signInLauncher.launch(signInIntent)
    }
}
