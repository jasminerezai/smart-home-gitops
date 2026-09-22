package com.example.smarthomegitops.domain

class DeceptionDetector {

    private val patterns = listOf(
        Regex(
            """\b(freez(e|ing)|frozen|freeze)\b""",
            RegexOption.IGNORE_CASE
        ),
        Regex(
            """\b(valve|valves)\b.*\b(failure|failed|broken|malfunction)\b""",
            RegexOption.IGNORE_CASE
        ),
        Regex(
            """\b(acute|critical)\b.*\b(electrical|power)\b.*\b(issue|failure|problem)\b""",
            RegexOption.IGNORE_CASE
        ),
        Regex(
            """\b(structural|structure)\b.*\b(crack|cracks|failure|damage)\b""",
            RegexOption.IGNORE_CASE
        ),
        Regex(
            """\b(imminent|immediately|urgent|urgently|critical)\b""",
            RegexOption.IGNORE_CASE
        ),
        Regex(
            """\b(blowout|explosion|catastrophic)\b""",
            RegexOption.IGNORE_CASE
        )
    )

    fun analyze(text: String): Int {
        val matches = patterns.count { it.containsMatchIn(text) }

        return (matches * 20).coerceAtMost(100)
    }
}