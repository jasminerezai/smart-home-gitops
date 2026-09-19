package com.example.smarthomegitops.model

data class PullRequest(
    val number: Int,
    val title: String,
    val state: String
)