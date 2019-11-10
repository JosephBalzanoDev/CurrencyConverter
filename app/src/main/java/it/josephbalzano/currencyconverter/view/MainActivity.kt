package it.josephbalzano.currencyconverter.view

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.josephbalzano.currencyconverter.R
import it.josephbalzano.currencyconverter.view.adapter.CurrencyAdapter
import it.josephbalzano.currencyconverter.view.model.CurrencyItem
import it.josephbalzano.currencyconverter.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by Joseph Balzano 05/11/2019
 */
class MainActivity : AppCompatActivity(), CurrencyAdapter.ViewHolderListener {
    private lateinit var viewModel: MainActivityViewModel

    private var isManagingSelection: Boolean = false
    private val adapter = CurrencyAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        initView()
        initObserver()
        startReceiving()
    }

    /**
     * Set default view settings
     */
    private fun initView() {
        currencyList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        currencyList.adapter = adapter
    }

    /**
     * Init and start observing LiveData from ViewModel
     */
    private fun initObserver() {
        viewModel.getItemsLiveData().removeObservers(this)

        viewModel.getItemsLiveData().observe(this, Observer {
            if (!currencyList.isAnimating && !isManagingSelection) adapter.updateItems(it)
        })
    }

    /**
     * Start receiving data from ViewModel
     */
    private fun startReceiving() = viewModel.fetchData()

    //region AdapterListener
    /**
     * Invoked when user click on item into list
     * We handle the selection setting it into ViewModel, then we handle the swap into
     * adapter and then scroll the RecyclerView to 0 pos
     */
    override fun onItemClick(item: CurrencyItem, indexOf: Int) {
        if (item.currencyCode == viewModel.selectedItem.currencyCode) return

        isManagingSelection = true

        viewModel.selectItem(item, indexOf)
        adapter.swipeToUp(indexOf)
        currencyList.scrollToPosition(0)

        isManagingSelection = false
    }

    /**
     * Invoked when user change value into editText on first item
     * We set correct value to selected value into ViewModel
     */
    override fun onValueChanged(value: Double) {
        viewModel.changeCurrentValue(value)
    }
    //endregion
}
