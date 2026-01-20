package com.example.bookapppro

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bookapppro.databinding.ActivityLoginBinding
import com.example.bookapppro.utils.UserPreferences

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var isLoginMode = true
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)

        // Auto-login check
        if (userPreferences.isUserLoggedIn()) {
            navigateToMain()
            return
        }

        setupUI()
    }

    private fun setupUI() {
        updateUI()

        binding.btnAction.setOnClickListener {
            handleAction()
        }

        binding.tvToggleMode.setOnClickListener {
            isLoginMode = !isLoginMode
            updateUI()
        }
    }

    private fun updateUI() {
        if (isLoginMode) {
            binding.btnAction.text = "Login"
            binding.tvToggleMode.text = "Don't have an account? Sign Up"
        } else {
            binding.btnAction.text = "Sign Up"
            binding.tvToggleMode.text = "Already have an account? Login"
        }
    }

    private fun handleAction() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (isLoginMode) {
            if (userPreferences.validateUser(email, password)) {
                navigateToMain()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        } else {
            userPreferences.saveUser(email, password)
            Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
            navigateToMain()
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
