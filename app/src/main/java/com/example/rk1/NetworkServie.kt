package com.example.lab3

import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import com.google.gson.JsonObject
import com.squareup.moshi.Json
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Query
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


interface UmoriliApi {
    @GET("/api/get")
    fun getData(
        @Query("name") resourceName: String?,
        @Query("num") count: Int
    ): Call<List<PostModel?>?>?
}

class PostModel {
    @SerializedName("site")
    @Expose
    var site: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("desc")
    @Expose
    var desc: String? = null

    @SerializedName("link")
    @Expose
    var link: String? = null

    @SerializedName("elementPureHtml")
    @Expose
    var elementPureHtml: String? = null
}

class NetworkService private constructor() {

    companion object {
        private const val BASE_URL = "https://umorili.herokuapp.com"

        private val mRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://umorili.herokuapp.com") //Базовая часть адреса
            .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
            .build()

        fun getJSONApi(): UmoriliApi? {
            return mRetrofit.create(UmoriliApi::class.java)
        }
    }
}