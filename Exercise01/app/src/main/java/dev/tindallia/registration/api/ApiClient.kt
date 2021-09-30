package dev.tindallia.registration.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val ENDPOINT_BASE_URL: String = "https://test-project-5646f.firebaseio.com/" // test database
//    private const val ENDPOINT_BASE_URL: String = "https://android101-7f15b-default-rtdb.asia-southeast1.firebasedatabase.app/mobileid/"
    val getClient: RetrofitApiInterface
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(ENDPOINT_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(RetrofitApiInterface::class.java)
        }

}
