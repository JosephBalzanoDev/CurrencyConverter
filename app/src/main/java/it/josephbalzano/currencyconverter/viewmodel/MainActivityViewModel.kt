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
import it.josephbalzano.currencyconverter.swap
import it.josephbalzano.currencyconverter.view.model.CurrencyCode
import it.josephbalzano.currencyconverter.view.model.CurrencyItem
import java.util.concurrent.TimeUnit

/**
 * Created by Joseph Balzano 05/11/2019
 */
class MainActivityViewModel : ViewModel() {
    var livedata = MutableLiveData<MutableList<CurrencyItem>>()
    var selectedCurrency = CurrencyItem(CurrencyCode.EUR, 1.0)

    @SuppressLint("CheckResult")
    fun fetchData(): MutableLiveData<MutableList<CurrencyItem>> {
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
    private fun prepareLiveData(): MutableList<CurrencyItem> =
        CurrencyCode.values()
            .map {
                if (it == selectedCurrency.currencyCode) selectedCurrency
                else CurrencyItem(it, 0.0)
            }
            .toMutableList()

    /**
     * Map response retrieved response with list of items
     */
    private fun mappingData(response: CurrencyResponse): Single<MutableList<CurrencyItem>> {
        var list = livedata.value ?: prepareLiveData()

        list = list.map {
            if (it.currencyCode != selectedCurrency.currencyCode) {
                val field =
                    CurrencyResponse.Rates::class.java.getDeclaredField(it.currencyCode.code)
                field.isAccessible = true
                it.value = (field.get(response.rates) as Double) * selectedCurrency.value
                it
            } else it
        }.toMutableList()

        return Single.just(list)
    }


    /**
     * Call API to update value
     */
    private fun updateValues() =
        Repos.instance.currencyApi!!
            .latest(selectedCurrency.currencyCode.code)

    fun selectItem(item: CurrencyItem, indexOf: Int) {
        selectedCurrency = item
        livedata.value!!.swap(indexOf)
    }

    fun changeCurrentValue(value: Double) {
        selectedCurrency.value = value
    }
}