package com.example.bookapppro.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookapppro.databinding.ActivityMainBinding

/**
 * NOTE: This is the starting file for MainActivity.
 * We will add all the logic (search, clicks, API calls) in Phase 5.
 */
class MainActivity : AppCompatActivity() {

    // This is the View Binding object that lets us access our layout's views
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Set the content view to the root of the binding
        setContentView(binding.root)

        // All our logic (search, clicks, loading history)
        // will be added here in Phase 5.
    }
}