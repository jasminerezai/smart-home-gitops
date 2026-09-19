package com.example.smarthomegitops.domain

class DeceptionDetector {

    private val patterns = listOf(
        Regex("""\b(freez(e|ing)|frozen)\b""", RegexOption.IGNORE_CASE),
        Regex("""\b(valve|valves)\s+(failure|failed|broken|malfunction)""", RegexOption.IGNORE_CASE),
        Regex("""\b(acute|critical)\s+(electrical|power)\s+(issue|failure|problem)""", RegexOption.IGNORE_CASE),
        Regex("""\b(structural|structure)\s+(crack|cracks|failure|damage)""", RegexOption.IGNORE_CASE),
        Regex("""\b(imminent|immediately|urgent|urgently|critical)\b""", RegexOption.IGNORE_CASE),
        Regex("""\b(blowout|explosion|catastrophic)\b""", RegexOption.IGNORE_CASE)
    )

    fun analyze(text: String): Int {
        val matches = patterns.count { it.containsMatchIn(text) }

        return (matches * 20).coerceAtMost(100)
    }
}