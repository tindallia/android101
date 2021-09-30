package dev.tindallia.registration.api

import dev.tindallia.registration.model.ApiId
import dev.tindallia.registration.model.UserModel
import retrofit2.Call
import retrofit2.http.*


interface RetrofitApiInterface {

    @POST("test.json") // testing interface
//    @POST("users.json")
    open fun postUser(@Body user: UserModel,@Query("auth")auth: String): Call<ApiId>

    @GET("test/{id}.json") // testing interface
//    @GET("users/{id}.json")
    open fun getUser(@Path("id") url: String,@Query("auth") auth: String): Call<UserModel>?
}