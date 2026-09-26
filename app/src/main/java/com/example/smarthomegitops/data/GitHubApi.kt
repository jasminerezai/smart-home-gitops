package com.example.smarthomegitops.data

import com.example.smarthomegitops.model.IssueComment
import com.example.smarthomegitops.model.PullRequest
import com.example.smarthomegitops.model.PullRequestUpdate
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.PATCH

interface GitHubApi {

    @GET("repos/jasminerezai/smart-home-gitops/pulls")
    suspend fun getPullRequests(
        @Header("Authorization") token: String
    ): List<PullRequest>

    @GET("repos/jasminerezai/smart-home-gitops/issues/{issueNumber}/comments")
    suspend fun getIssueComments(
        @Path("issueNumber") issueNumber: Int,
        @Header("Authorization") token: String
    ): List<IssueComment>

    @PATCH("repos/jasminerezai/smart-home-gitops/pulls/{pullNumber}")
    suspend fun updatePullRequest(
        @Path("pullNumber") pullNumber: Int,
        @Header("Authorization") token: String,
        @Body update: PullRequestUpdate
    ): PullRequest
}