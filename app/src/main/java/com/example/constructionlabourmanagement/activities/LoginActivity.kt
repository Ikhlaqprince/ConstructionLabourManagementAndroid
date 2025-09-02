package com.example.constructionlabourmanagement.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.constructionlabourmanagement.database.AppDatabase
import com.example.constructionlabourmanagement.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                binding.usernameEditText.error = "Username is required"
                binding.passwordEditText.error = "Password is required"
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val user = withContext(Dispatchers.IO) {
                    db.userDao().getUserByUsername(username)
                }

                if (user != null && user.password == password) {
                    // Successfully logged in
                    val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                    intent.putExtra("USER_ID", user.id)
                    startActivity(intent)
                    finish()
                } else {
                    // Invalid credentials
                    binding.usernameEditText.error = "Invalid username or password"
                    binding.passwordEditText.error = "Invalid username or password"
                }
            }
        }

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}