package com.yashkasera.firebasechatbot.repository.model

import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.yashkasera.firebasechatbot.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * @author yashkasera
 * Created 22/04/22 at 11:24 PM
 */
interface RetrofitService {

    @GET("/get")
    suspend fun getKeywords(
        @Query("bid") bid: String = "165821",
        @Query("key") key: String = "Er6JAkf53Ce8FD7q",
        @Query("uid") uid: String = "1",
        @Query("msg") msg: String
    ): Response<ApiResponseModel>

    companion object {
        fun getInstance(): RetrofitService {
            val builder = OkHttpClient.Builder()
            val interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(interceptor)
                builder.addInterceptor(OkHttpProfilerInterceptor())
            }
            val client = builder.build()
            return Retrofit.Builder()
                .baseUrl("http://api.brainshop.ai/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitService::class.java)
        }
    }
}