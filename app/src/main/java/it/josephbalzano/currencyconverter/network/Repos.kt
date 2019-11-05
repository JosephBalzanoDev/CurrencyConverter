package it.josephbalzano.currencyconverter.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Joseph Balzano 05/11/2019
 */
class Repos private constructor() {
    private val baseUrl = "https://revolut.duckdns.org/"

    var currencyApi: CurrencyApi? = null

    init {
        createCurrencyApi()
    }

    private fun createCurrencyApi() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        currencyApi = retrofit.create(CurrencyApi::class.java)
    }

    companion object {
        val instance = Repos()
    }
}