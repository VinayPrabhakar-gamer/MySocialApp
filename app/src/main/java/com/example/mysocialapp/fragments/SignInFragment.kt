package com.example.mysocialapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mysocialapp.R
import com.example.mysocialapp.SocialActivity
import com.example.mysocialapp.databinding.FragSignInBinding
import com.example.mysocialapp.utils.isInputValid
import com.google.firebase.auth.FirebaseAuth

class SignInFragment: Fragment() {
    private lateinit var fragSignInBinding: FragSignInBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragSignInBinding = FragSignInBinding.inflate(layoutInflater)
        return fragSignInBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        // Validation of Input Fields
        fragSignInBinding.btnSignIn.setOnClickListener {
            val isValid = (fragSignInBinding.tietEmail.isInputValid(fragSignInBinding.tilEmail, "Invalid Username"))
                    && (fragSignInBinding.tietPassword.isInputValid(fragSignInBinding.tilPassword, "Invalid Password"))

            if(isValid) {
                login(fragSignInBinding.tietEmail.text.toString(),
                    fragSignInBinding.tietPassword.text.toString())
            }else{
                return@setOnClickListener
            }
        }

        fragSignInBinding.tvSignUpText.setOnClickListener {
            // Goto Sign Up Fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fcv, SignUpFragment())
                .commit()
        }
    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            // Go to Social Activity
            activity?.startActivity(Intent(activity, SocialActivity::class.java))
            activity?.finish()
        }
            .addOnFailureListener { /*Show Error*/ }
    }
}