package com.example.smarthomegitops.presentation

data class SmartHomeUiState(
    val isSecurityAlert: Boolean = false,
    val confidenceScore: Int = 0,
    val attackText: String = "",
    val pullRequestNumber: Int? = null,
    val errorMessage: String? = null
)