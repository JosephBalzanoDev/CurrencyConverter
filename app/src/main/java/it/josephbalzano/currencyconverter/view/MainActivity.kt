package it.josephbalzano.currencyconverter.view

import android.os.Bundle
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
    override fun onValueChanged(value: Double) {
        viewModel.changeCurrentValue(value)
    }

    private var isSelected: Boolean = false
    private lateinit var viewModel: MainActivityViewModel

    private val adapter = CurrencyAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        initView()
        initObserver()
    }

    private fun initObserver() {
        viewModel.fetchData().observe(this, Observer {
            if (!currencyList.isAnimating && !isSelected)
                adapter.updateItems(it)
        })
    }

    private fun initView() {
        currencyList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        currencyList.adapter = adapter
    }

    override fun onItemClick(item: CurrencyItem, indexOf: Int) {
        if (item.currencyCode == viewModel.selectedCurrency.currencyCode) return

        isSelected = true
        viewModel.selectItem(item, indexOf)
        adapter.swipeToUp(indexOf)
        currencyList.scrollToPosition(0)
        isSelected = false
    }
}
