package it.josephbalzano.currencyconverter.view.model

/**
 * Created by Joseph Balzano 05/11/2019
 */
data class CurrencyItem(
    val currencyCode: CurrencyCode,
    var value: Double = 0.0
)