package it.josephbalzano.currencyconverter.view.model

import it.josephbalzano.currencyconverter.R

/**
 * Created by Joseph Balzano 05/11/2019
 */
enum class CurrencyCode(
    val code: String,
    val flagResource: Int,
    val description: String,
    val symbol: String
) {
    EUR("EUR", R.drawable.ic_eur, "Euro", "€"),
    AUD("AUD", R.drawable.ic_aud, "Australian Dollar", "\$"),
    BGN("BGN", R.drawable.ic_bgn, "Bulgarian Lev", "лв."),
    BRL("BRL", R.drawable.ic_brl, "Brazilian Real", "R\$"),
    CAD("CAD", R.drawable.ic_cad, "Canadian Dollar", "\$"),
    CHF("CHF", R.drawable.ic_chf, "Swiss Franc", "Fr."),
    CNY("CNY", R.drawable.ic_cny, "Chinese yuan", "¥"),
    CZK("CZK", R.drawable.ic_czk, "Czech koruna", "Kč"),
    DKK("DKK", R.drawable.ic_dkk, "Danish krone", "kr"),
    GBP("GBP", R.drawable.ic_gbp, "British pound", "\t£"),
    HKD("HKD", R.drawable.ic_hkd, "Hong Kong dollar", "\$"),
    HRK("HRK", R.drawable.ic_hrk, "Croatian kuna", "kn"),
    HUF("HUF", R.drawable.ic_huf, "Hungarian forint", "Ft"),
    ILS("ILS", R.drawable.ic_ils, "Israeli new shekel", "₪"),
    INR("INR", R.drawable.ic_inr, "Indian rupee", "₹"),
    ISK("ISK", R.drawable.ic_isk, "Icelandic króna", "kr"),
    JPY("JPY", R.drawable.ic_jpy, "Japanese yen", "¥"),
    KRW("KRW", R.drawable.ic_krw, "South Korean won", "₩"),
    MXN("MXN", R.drawable.ic_mxn, "Mexican peso", "\$"),
    MYR("MYR", R.drawable.ic_myr, "Malaysian ringgit", "RM"),
    NOK("NOK", R.drawable.ic_nok, "Norwegian krone", "\tkr"),
    NZD("NZD", R.drawable.ic_nzd, "New Zealand dollar", "\$"),
    PHP("PHP", R.drawable.ic_php, "Philippine peso", "₱"),
    PLN("PLN", R.drawable.ic_pln, "Polish złoty", "zł"),
    RON("RON", R.drawable.ic_ron, "Romanian leu", "lei"),
    RUB("RUB", R.drawable.ic_rub, "Russian ruble", "\u20BD"),
    SEK("SEK", R.drawable.ic_sek, "Swedish krona", "\tkr"),
    SGD("SGD", R.drawable.ic_sgd, "Singapore dollar", "\$"),
    THB("THB", R.drawable.ic_thb, "Thai baht", "฿"),
    TRY("TRY", R.drawable.ic_try, "Turkish lira", "\t₺"),
    USD("USD", R.drawable.ic_usd, "United States dollar", "\t\$"),
    ZAR("ZAR", R.drawable.ic_zar, "South African rand", "Rs"),
    IDR("IDR", R.drawable.ic_idr, "Indonesian rupiah,", "Rp")
}