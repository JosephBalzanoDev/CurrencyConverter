package it.josephbalzano.currencyconverter.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import it.josephbalzano.currencyconverter.network.Repos
import it.josephbalzano.currencyconverter.network.model.CurrencyResponse
import it.josephbalzano.currencyconverter.view.model.CurrencyCode
import it.josephbalzano.currencyconverter.view.model.CurrencyItem
import java.util.concurrent.TimeUnit

/**
 * Created by Joseph Balzano 05/11/2019
 */
class MainActivityViewModel : ViewModel() {
    private val TAG = "MainActivityViewModel"

    private var livedata = MutableLiveData<List<CurrencyItem>>()
    private var selectedCurrency = CurrencyCode.EUR

    @SuppressLint("CheckResult")
    fun fetchData(): MutableLiveData<List<CurrencyItem>> {
        Observable.interval(1, TimeUnit.SECONDS)
            .flatMapSingle { updateValues() }
            .flatMapSingle { mappingData(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { value -> livedata.postValue(value) }
        return livedata
    }

    /**
     * Prepare list of items to map it with values
     */
    private fun prepareLiveData(): List<CurrencyItem> =
        CurrencyCode.values().map { CurrencyItem(false, it, 0.0) }

    /**
     * Map response retrieved response with list of items
     */
    private fun mappingData(response: CurrencyResponse): Single<List<CurrencyItem>> =
        Single.just(
            livedata.value ?: prepareLiveData()
                .map {
                    val field =
                        CurrencyResponse.Rates::class.java.getDeclaredField(it.currencyCode.code)
                    field.isAccessible = true
                    it.value = field.get(response.rates) as Double
                    it
                })

    /**
     * Call API to update value
     */
    private fun updateValues() =
        Repos.instance
            .currencyApi!!
            .latest(selectedCurrency.code)
}