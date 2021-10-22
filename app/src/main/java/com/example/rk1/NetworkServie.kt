package com.example.rk1

import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit
import retrofit2.http.GET
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


interface requestApi {
    @GET("/data/v2/histoday?fsym=BTC&tsym=USD&limit=10")
    fun getData(): Call<ApiResponse?>?
}

class ApiResponse {
    @SerializedName("Responce")
    @Expose
    var responce: String? = null

    @SerializedName("Data")
    @Expose
    var data: Data? = null
}

class Data {
    @SerializedName("Data")
    @Expose
    var rows: ArrayList<DataRow?>? = null
}

class DataRow {
    @SerializedName("time")
    @Expose
    var time: String? = null

    @SerializedName("high")
    @Expose
    var high: String? = null

    @SerializedName("low")
    @Expose
    var low: String? = null

    @SerializedName("open")
    @Expose
    var open: String? = null

    @SerializedName("close")
    @Expose
    var close: String? = null

    @SerializedName("volumefrom")
    @Expose
    var volumeFrom: String? = null

    @SerializedName("volumeto")
    @Expose
    var volumeTo: String? = null
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