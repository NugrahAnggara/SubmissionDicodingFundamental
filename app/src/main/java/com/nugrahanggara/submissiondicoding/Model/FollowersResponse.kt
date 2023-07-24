package com.nugrahanggara.submissiondicoding.Model

import com.google.gson.annotations.SerializedName

data class FollowersResponse(
    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,
)
