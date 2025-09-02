package com.example.constructionlabourmanagement.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.constructionlabourmanagement.database.AppDatabase
import com.example.constructionlabourmanagement.databinding.ActivityRegisterBinding
import com.example.constructionlabourmanagement.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)

        binding.registerButton.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()
            val username = binding.usernameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()
            val company = binding.companyEditText.text.toString().trim()

            if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                if (name.isEmpty()) binding.nameEditText.error = "Name is required"
                if (username.isEmpty()) binding.usernameEditText.error = "Username is required"
                if (password.isEmpty()) binding.passwordEditText.error = "Password is required"
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val usernameExists = withContext(Dispatchers.IO) {
                    db.userDao().checkUsernameExists(username) > 0
                }

                if (usernameExists) {
                    binding.usernameEditText.error = "Username already exists"
                    return@launch
                }

                val user = User(
                    name = name,
                    username = username,
                    password = password,
                    company = company.ifEmpty { null }
                )

                val userId = withContext(Dispatchers.IO) {
                    db.userDao().insert(user).toInt()
                }

                if (userId > 0) {
                    // Registration successful
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    intent.putExtra("REGISTRATION_SUCCESS", true)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}