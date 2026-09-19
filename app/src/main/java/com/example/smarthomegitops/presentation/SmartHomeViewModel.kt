package com.example.smarthomegitops.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthomegitops.BuildConfig
import com.example.smarthomegitops.data.GitHubRepository
import com.example.smarthomegitops.data.RetrofitClient
import com.example.smarthomegitops.domain.DeceptionDetector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SmartHomeViewModel : ViewModel() {

    private val repository = GitHubRepository(RetrofitClient.api)
    private val detector = DeceptionDetector()

    private val _uiState = MutableStateFlow(SmartHomeUiState())
    val uiState: StateFlow<SmartHomeUiState> = _uiState.asStateFlow()

    init {
        startPolling()
    }

    private fun startPolling() {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                pollGitHub()
                delay(30_000)
            }
        }
    }

    private suspend fun pollGitHub() {
        val token = BuildConfig.GITHUB_TOKEN

        if (token.isBlank()) {
            return
        }

        try {
            val pullRequests = repository.getOpenPullRequests(token)

            for (pullRequest in pullRequests) {
                val comments = repository.getIssueComments(
                    issueNumber = pullRequest.number,
                    token = token
                )

                for (comment in comments) {
                    val score = detector.analyze(comment.body)

                    if (score > 0) {
                        _uiState.value = SmartHomeUiState(
                            isSecurityAlert = true,
                            confidenceScore = score,
                            attackText = comment.body
                        )
                        return
                    }
                }
            }

            _uiState.value = SmartHomeUiState()

        } catch (e: Exception) {
            // Ignore temporary network/API errors.
        }
    }
}