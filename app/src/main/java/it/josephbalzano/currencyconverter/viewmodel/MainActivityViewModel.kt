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
    var selectedItem = CurrencyItem(CurrencyCode.EUR, 1.0)
        set(value) {
            // This check is used to start update of UI with old response data
            if (field.value != value.value && field.currencyCode == value.currencyCode)
                newValue()

            field = value
        }

    private var cachedResponse: CurrencyResponse? = null
    private var items = MutableLiveData<MutableList<CurrencyItem>>()

    /**
     * Start receiving data from API services every second, cache response,
     * map it and the fire it to LiveData
     */
    @SuppressLint("CheckResult")
    fun fetchData() {
        Observable.interval(1, TimeUnit.SECONDS)
            .flatMapSingle { updateValues() }
            .flatMapSingle { mappingData(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ value -> items.postValue(value) }, { items.postValue(null) })
    }

    /**
     * Getter of items
     */
    fun getItemsLiveData(): MutableLiveData<MutableList<CurrencyItem>> = items

    /**
     * Handle the correct selection of item from list
     * First of all we set new selection then swap items list
     */
    fun selectItem(item: CurrencyItem, indexOf: Int) {
        selectedItem = item
        items.value!!.swap(indexOf)
    }

    /**
     * Handle change of current value for selected currency
     */
    fun changeCurrentValue(value: Double) {
        selectedItem.value = value
    }

    /**
     * Handle changing of value, so we pick last response and re-map all response values with
     * user value of selected currency
     */
    @SuppressLint("CheckResult")
    private fun newValue() {
        if (cachedResponse == null) return

        mappingData(cachedResponse!!)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { value -> items.postValue(value) }
    }

    /**
     * Save last response from services
     */
    private fun cacheResponse(it: CurrencyResponse): Single<CurrencyResponse> {
        cachedResponse = it
        return Single.just(it)
    }

    /**
     * Prepare list of items to map it with values
     */
    private fun prepareLiveData(): MutableList<CurrencyItem> =
        CurrencyCode.values()
            .map {
                if (it == selectedItem.currencyCode) selectedItem
                else CurrencyItem(it, 0.0)
            }
            .toMutableList()

    /**
     * Map response retrieved response with list of items
     */
    private fun mappingData(response: CurrencyResponse): Single<MutableList<CurrencyItem>> {
        var list = items.value ?: prepareLiveData()

        list = list.map {
            if (it.currencyCode != selectedItem.currencyCode) {
                val field =
                    CurrencyResponse.Rates::class.java.getDeclaredField(it.currencyCode.code)
                field.isAccessible = true
                it.value = (field.get(response.rates) as Double) * selectedItem.value
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
            .latest(selectedItem.currencyCode.code)
            .onErrorReturn {
                if (cachedResponse != null) cachedResponse
                else null
            }
            .flatMap { cacheResponse(it) }
}