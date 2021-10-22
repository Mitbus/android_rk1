package com.example.rk1

import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


interface requestApi {
    @GET("/data/v2/histoday?fsym=BTC&tsym=USD&limit=10")
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
        private const val BASE_URL = "https://min-api.cryptocompare.com"

        private val mRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) //Базовая часть адреса
            .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
            .build()

        fun getJSONApi(): requestApi? {
            return mRetrofit.create(requestApi::class.java)
        }
    }
}