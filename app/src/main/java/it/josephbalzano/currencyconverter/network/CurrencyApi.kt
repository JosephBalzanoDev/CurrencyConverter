package it.josephbalzano.currencyconverter.network

import io.reactivex.Single
import it.josephbalzano.currencyconverter.network.model.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Joseph Balzano 05/11/2019
 */
interface CurrencyApi {
    @GET("latest")
    fun latest(@Query("base") page: String):
            Single<CurrencyResponse>
}