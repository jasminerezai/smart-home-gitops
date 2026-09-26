package com.example.smarthomegitops.data

import com.example.smarthomegitops.model.IssueComment
import com.example.smarthomegitops.model.PullRequest
import com.example.smarthomegitops.model.PullRequestUpdate

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
}