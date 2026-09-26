package com.example.smarthomegitops.model
data class UpdateFileRequest(
    val message: String,
    val content: String,
    val sha: String,
    val branch: String = "main"
)