package com.streamwide.contacts.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.streamwide.contacts.R
import com.streamwide.contacts.ui.contacts.ContactsActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        lifecycleScope.launch {
            delay(TIME)
            startContactsActivity()
        }
    }

    private fun startContactsActivity()
    {
        val intent  = Intent(this,ContactsActivity::class.java)
        intent.flags  = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    companion object{
        const val TIME = 3000L
    }
}