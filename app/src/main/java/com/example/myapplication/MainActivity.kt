package com.example.myapplication  // Ensure this matches your package name

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var userInput: EditText
    private lateinit var submitButton: Button
    private lateinit var messagesLayout: LinearLayout
    private lateinit var scrollView: ScrollView

    // List of predefined responses
    private val responses = listOf(
        "That's interesting!",
        "Can you tell me more?",
        "I'm not sure about that.",
        "Let me think about it.",
        "How do you feel about that?",
        "What do you mean?",
        "That's a great point!",
        "Hmm, that's something to consider.",
        "I see where you're coming from.",
        "Tell me more about that."
    )

    private val typingDelay = 50L // Delay between characters in milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find views by ID
        userInput = findViewById(R.id.userInput)
        submitButton = findViewById(R.id.submitButton)
        messagesLayout = findViewById(R.id.messagesLayout)
        scrollView = findViewById(R.id.scrollView)

        // Set an OnClickListener on the submit button
        submitButton.setOnClickListener {
            // Get the text from EditText
            val inputText = userInput.text.toString()

            // Check if input is not empty
            if (!TextUtils.isEmpty(inputText)) {
                // Handle the user input and generate a response
                handleUserInput(inputText)

                // Clear the EditText
                userInput.text.clear()

                // Scroll to the bottom of the ScrollView
                scrollView.post {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN)
                }
            }
        }
    }

    private fun handleUserInput(input: String) {
        // Create a new TextView for the user message
        val userMessageTextView = TextView(this)
        userMessageTextView.text = "You: $input"
        userMessageTextView.textSize = 16f
        userMessageTextView.setPadding(0, 0, 0, 16)
        userMessageTextView.setBackgroundColor(0xFFE3F2FD.toInt())  // Light blue background
        userMessageTextView.setTextColor(0xFF000000.toInt())  // Black text

        // Add the user message to the messagesLayout
        messagesLayout.addView(userMessageTextView)

        // Generate a response based on user input
        val botResponse = generateResponseForInput(input)

        // Create a placeholder TextView for the bot response
        val responseTextView = TextView(this)
        responseTextView.text = "Bot: "  // Initial text
        responseTextView.textSize = 16f
        responseTextView.setPadding(0, 0, 0, 16)
        responseTextView.setBackgroundColor(0xFFF1F8E9.toInt())  // Light green background
        responseTextView.setTextColor(0xFF000000.toInt())  // Black text

        // Add the response placeholder to the messagesLayout
        messagesLayout.addView(responseTextView)

        // Start the typing effect
        simulateTyping(responseTextView, botResponse)
    }

    private fun generateResponseForInput(input: String): String {
        // Here you can implement more sophisticated logic based on the input
        // For now, just return a random response
        return responses[Random.nextInt(responses.size) ]
    }

    private fun simulateTyping(textView: TextView, response: String) {
        val handler = Handler(mainLooper)
        var index = 0
        val runnable = object : Runnable {
            override fun run() {
                // Append one character at a time
                val currentText = textView.text.toString()
                textView.text = "$currentText${response[index]}"

                // Update index and continue if more characters are left
                index++
                if (index < response.length) {
                    handler.postDelayed(this, typingDelay)
                }
            }
        }
        handler.post(runnable)
    }
}
