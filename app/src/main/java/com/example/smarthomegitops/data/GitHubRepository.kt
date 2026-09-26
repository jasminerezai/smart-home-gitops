package com.example.smarthomegitops.data

import com.example.smarthomegitops.model.IssueComment
import com.example.smarthomegitops.model.PullRequest
import com.example.smarthomegitops.model.PullRequestUpdate
import com.example.smarthomegitops.model.HouseConfig
import com.example.smarthomegitops.model.UpdateFileRequest
import com.example.smarthomegitops.model.GitHubFileResponse
import android.util.Base64
import org.json.JSONObject

class GitHubRepository(
    private val api: GitHubApi
) {

    suspend fun getOpenPullRequests(token: String): List<PullRequest> {
        return api.getPullRequests("Bearer $token")
            .filter { it.state == "open" }
    }

    suspend fun getIssueComments(
        issueNumber: Int,
        token: String
    ): List<IssueComment> {
        return api.getIssueComments(
            issueNumber = issueNumber,
            token = "Bearer $token"
        )
    }

    suspend fun closePullRequest(
        pullNumber: Int,
        token: String
    ): PullRequest {
        return api.updatePullRequest(
            pullNumber = pullNumber,
            token = "Bearer $token",
            update = PullRequestUpdate(state = "closed")
        )
    }

    suspend fun getHouseConfig(token: String): GitHubFileResponse {
        return api.getHouseConfig("Bearer $token")
    }

    suspend fun forceMerge(
        pullNumber: Int,
        token: String
    ) {
        val file = getHouseConfig(token)

        val decodedContent = Base64.decode(
            file.content,
            Base64.DEFAULT
        ).toString(Charsets.UTF_8)

        val json = JSONObject(decodedContent)

        json.put("target_temperature", 17.0)
        json.put("last_updated_by", "Android-Operator")

        val updatedContent = Base64.encodeToString(
            json.toString(2).toByteArray(Charsets.UTF_8),
            Base64.NO_WRAP
        )

        api.updateHouseConfig(
            token = "Bearer $token",
            request = UpdateFileRequest(
                message = "Update house configuration from Android",
                content = updatedContent,
                sha = file.sha,
                branch = "main"
            )
        )

        api.updatePullRequest(
            pullNumber = pullNumber,
            token = "Bearer $token",
            update = PullRequestUpdate(state = "closed")
        )
    }
}