package com.menthalan.twittercounter.ui.activities

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.menthalan.twittercounter.databinding.ActivityMainBinding
import com.menthalan.twittercounter.ui.viewmodels.TwitterCounterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: TwitterCounterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTextWatcher()
        setupButtons()
        observeState()

    }

    private fun setupTextWatcher() {
        binding.etTweet.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?,
                                           start: Int,
                                           count: Int,
                                           after: Int) = Unit

            override fun onTextChanged(s: CharSequence?,
                                       start: Int,
                                       before: Int,
                                       count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                viewModel.onTextChanged(s?.toString() ?: "")
            }
        })
    }
    private fun setupButtons() {
        binding.btnCopy.setOnClickListener {
            val text = viewModel.copyText()
            if (text.isNotBlank()) {
                val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.setPrimaryClip(ClipData.newPlainText("tweet", text))
                Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnClear.setOnClickListener {
            binding.etTweet.setText("")
            viewModel.clearText()
        }

        binding.btnPostTweet.setOnClickListener {
            viewModel.postTweet()
        }
    }
    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.tvTypedCount.text = "${state.typed}/280"
                    binding.tvRemainingCount.text = state.remaining.toString()
                    binding.btnPostTweet.isEnabled = state.isPostEnabled && !state.isLoading
                    binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

                    if (state.postSuccess) {
                        Toast.makeText(this@MainActivity, "Tweet Posted",
                            Toast.LENGTH_SHORT).show()
                        binding.etTweet.setText("")
                        viewModel.clearText()
                        viewModel.consumePostResult()
                    }
                }
            }
        }

    }
}