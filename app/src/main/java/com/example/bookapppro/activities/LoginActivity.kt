package com.example.bookapppro.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bookapppro.databinding.ActivityLoginBinding
import com.example.bookapppro.utils.UserPreferences

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var isLoginMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- Auto-Login Check ---
        // If the user is already logged in, skip this screen and go to Main.
        if (UserPreferences.isUserLoggedIn(this)) {
            goToMainActivity()
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // --- Main Action Button (Login/Signup) ---
        binding.btnAction.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (isLoginMode) {
                handleLogin(email, password)
            } else {
                handleSignup(email, password)
            }
        }

        // --- Toggle Button (Switch between Login and Signup) ---
        binding.tvToggleMode.setOnClickListener {
            isLoginMode = !isLoginMode // Flip the mode
            if (isLoginMode) {
                binding.btnAction.text = "Login"
                binding.tvToggleMode.text = "Don't have an account? Sign Up"
            } else {
                binding.btnAction.text = "Sign Up"
                binding.tvToggleMode.text = "Already have an account? Login"
            }
        }
    }

    private fun handleLogin(email: String, password: String) {
        if (UserPreferences.validateUser(this, email, password)) {
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
            goToMainActivity()
        } else {
            Toast.makeText(this, "Invalid credentials. Please try again.", Toast.LENGTH_LONG).show()
        }
    }

    private fun handleSignup(email: String, password: String) {
        // For this simple app, we just save the user.
        // In a real app, you'd check if the email is already taken.
        UserPreferences.saveUser(this, email, password)
        Toast.makeText(this, "Signup Successful!", Toast.LENGTH_SHORT).show()
        goToMainActivity()
    }

    private fun goToMainActivity() {
        // We will create MainActivity in Phase 4
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Call finish() to remove LoginActivity from the back stack
    }
}