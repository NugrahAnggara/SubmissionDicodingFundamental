package com.nugrahanggara.submissiondicoding.Retrofit

import com.nugrahanggara.submissiondicoding.Model.FollowersResponse
import com.nugrahanggara.submissiondicoding.Model.ModelUserResponse
import com.nugrahanggara.submissiondicoding.Model.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService
{
    @Headers("Authorization: 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")
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<FollowersResponse>>

    @Headers("Authorization: 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")
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<FollowersResponse>>

    @Headers("Authorization: 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")
    @GET("users/{username}")
    fun getDataUser(@Path("username") username: String): Call<ModelUserResponse>

    @GET("search/users")
    fun getUserByQuery(@Query("q") query : String): Call<Response>
}