package com.example.mysocialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mysocialapp.databinding.ActivitySocialBinding
import com.example.mysocialapp.fragments.HomeFragment
import com.example.mysocialapp.fragments.PostFragment
import com.example.mysocialapp.fragments.ProfileFragment

class SocialActivity : AppCompatActivity() {

    private val socialActivityBinding by lazy{
        ActivitySocialBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(socialActivityBinding.root)

        socialActivityBinding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.botNavProfile -> setupFragment(ProfileFragment())
                R.id.botNavPost -> setupFragment(PostFragment())
                else -> setupFragment(HomeFragment())
            }
            true
        }

    }

    private fun setupFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(socialActivityBinding.container.id, fragment)
            .commit()
    }
}