package it.josephbalzano.currencyconverter.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import it.josephbalzano.currencyconverter.R
import it.josephbalzano.currencyconverter.viewmodel.MainActivityViewModel

/**
 * Created by Joseph Balzano 05/11/2019
 */
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        //TODO
        var index = 0
        viewModel.fetchData().observe(this, Observer {
            index++
            Log.d("Test", "Prova conteggio: $index")
        })
    }
}
