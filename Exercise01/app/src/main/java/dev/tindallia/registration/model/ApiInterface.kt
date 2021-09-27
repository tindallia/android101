package dev.tindallia.registration.model

import retrofit2.Call
import retrofit2.http.*


interface RetrofitApiInterface {

//    @POST("test.json") // testing interface
    @POST("users.json")
    open fun postUser(@Body user: UserModel): Call<ApiId>

//    @GET("test/{id}.json") // testing interface
    @GET("users/{id}.json")
    open fun getUser(@Path("id") url: String): Call<UserModel>?
}