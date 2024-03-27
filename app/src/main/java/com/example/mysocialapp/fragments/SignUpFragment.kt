package com.example.mysocialapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.mysocialapp.R
import com.example.mysocialapp.SocialActivity
import com.example.mysocialapp.data.User
import com.example.mysocialapp.databinding.FragmentSignUpBinding
import com.example.mysocialapp.utils.Const
import com.example.mysocialapp.utils.isInputValid
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SignUpFragment : Fragment() {
    private lateinit var fragmentSignUpBinding: FragmentSignUpBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFireStore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentSignUpBinding = FragmentSignUpBinding.inflate(layoutInflater)
        return fragmentSignUpBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        mFireStore = FirebaseFirestore.getInstance()

        fragmentSignUpBinding.btnSignUp.setOnClickListener {
            val isValid = (fragmentSignUpBinding.tietEmail.isInputValid(fragmentSignUpBinding.tilEmail, "Invalid Email"))
                    && (fragmentSignUpBinding.tietPassword.isInputValid(fragmentSignUpBinding.tilPassword, "Invalid Password"))
                    && (fragmentSignUpBinding.tietFullName.isInputValid(fragmentSignUpBinding.tilFullName, "Invalid Username"))
                    && (fragmentSignUpBinding.tietAbout.isInputValid(fragmentSignUpBinding.tilAbout, "Invalid User Description"))


            lifecycleScope.launch(Dispatchers.IO) {
                // Loader Icon
                signUpUsingCoroutines(fragmentSignUpBinding.tietFullName.text.toString(),
                    fragmentSignUpBinding.tietAbout.text.toString(),
                    fragmentSignUpBinding.tietEmail.text.toString(),
                    fragmentSignUpBinding.tietPassword.text.toString())

//                withContext(Dispatchers.Main) {
//                      // Remove Loader Icon
//                }
            }

//            if(isValid) {
//                signUp(fragmentSignUpBinding.tietFullName.text.toString(),
//                    fragmentSignUpBinding.tietAbout.text.toString(),
//                    fragmentSignUpBinding.tietEmail.text.toString(),
//                    fragmentSignUpBinding.tietPassword.text.toString())
//            }else{
//                return@setOnClickListener
//            }
        }

        fragmentSignUpBinding.btnLogin.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fcv, SignInFragment())
                .commit()
        }
    }

    private fun signUp(fullName: String, about: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { result ->
            // Add this user data in my Firestore
            mFireStore
                .collection(Const.FS_USERS)
                .document(result.user?.uid ?: "EmptyDocId")
                .set(User(fullName = fullName, email = email, about = about))
                .addOnSuccessListener {
                    // Go to Social Activity
                    activity?.startActivity(Intent(activity, SocialActivity::class.java))
                    activity?.finish()
                }
                .addOnFailureListener {error ->
                    Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
                }
        }
            .addOnFailureListener { err ->
                Toast.makeText(activity, err.message, Toast.LENGTH_SHORT).show()
            }
    }

    private suspend fun signUpUsingCoroutines(
        fullName: String, about: String, email: String, password: String
    ) {
        try {
            val authResult = mAuth.createUserWithEmailAndPassword(email, password).await()

            mFireStore
                .collection("Users")
                .document(authResult.user?.uid ?: "EmptyDocId")
                .set(User(fullName = fullName, email = email, about = about))

            activity?.startActivity(Intent(activity, SocialActivity::class.java))
            activity?.finish()
        } catch (e: Exception) {
            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}