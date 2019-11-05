package it.josephbalzano.currencyconverter.view.model

/**
 * Created by Joseph Balzano 05/11/2019
 */
enum class CurrencyCode(
    val code: String,
    val flagResource: Int,
    val description: String
) {
    // TODO
    AUD("AUD", 0, "Australian Dollar"),
    BGN("BGN", 0, "Bulgarian Lev"),
    BRL("BRL", 0, "Brazilian Real"),
    CAD("CAD", 0, "Canadian Dollar"),
    CHF("CHF", 0, "Swiss Franc"),
    CNY("CNY", 0, ""),
    CZK("CZK", 0, ""),
    DKK("DKK", 0, ""),
    EUR("EUR", 0, ""),
    GBP("GBP", 0, ""),
    HKD("HKD", 0, ""),
    HRK("HRK", 0, ""),
    HUF("HUF", 0, ""),
    ILS("ILS", 0, ""),
    INR("INR", 0, ""),
    ISK("ISK", 0, ""),
    JPY("JPY", 0, ""),
    KRW("KRW", 0, ""),
    MXN("MXN", 0, ""),
    MYR("MYR", 0, ""),
    NOK("NOK", 0, ""),
    NZD("NZD", 0, ""),
    PHP("PHP", 0, ""),
    PLN("PLN", 0, ""),
    RON("RON", 0, ""),
    RUB("RUB", 0, ""),
    SEK("SEK", 0, ""),
    SGD("SGD", 0, ""),
    THB("THB", 0, ""),
    TRY("TRY", 0, ""),
    USD("USD", 0, ""),
    ZAR("ZAR", 0, ""),
    IDR("IDR", 0, "")
}