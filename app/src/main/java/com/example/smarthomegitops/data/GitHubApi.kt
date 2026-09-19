package com.example.smarthomegitops.data

import com.example.smarthomegitops.model.IssueComment
import com.example.smarthomegitops.model.PullRequest
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

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
}