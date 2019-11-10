package it.josephbalzano.currencyconverter.view.adapter.viewholder

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import it.josephbalzano.currencyconverter.view.adapter.CurrencyAdapter
import it.josephbalzano.currencyconverter.view.model.CurrencyItem
import kotlinx.android.synthetic.main.currency_item.view.*
import java.text.NumberFormat

/**
 * Created by Joseph Balzano 09/11/2019
 */
class CurrencyHolder(
    view: View, listener: CurrencyAdapter.ViewHolderListener, items: MutableList<CurrencyItem>
) : RecyclerView.ViewHolder(view) {
    private lateinit var model: CurrencyItem

    init {
        view.setOnClickListener {
            listener.onItemClick(model, items.indexOf(model))
        }

        itemView.value.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (position != 0 && hasFocus) listener.onItemClick(model, items.indexOf(model))
        }

        itemView.value.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (position == 0) {
                    if (s.isNotBlank()) {
                        listener.onValueChanged(NumberFormat.getInstance().parse(s.toString()).toDouble())
                    } else {
                        listener.onValueChanged(1.0)
                    }
                }
            }
        })
    }

    /**
     * Bind method with change all model holder
     */
    fun bind(item: CurrencyItem) {
        this.model = item

        itemView.icon.setImageResource(item.currencyCode.flagResource)
        itemView.code.text = item.currencyCode.code
        itemView.descr.text = item.currencyCode.description
        itemView.value.setText(String.format("%.2f", item.value))
    }

    /**
     * Bind method with change only value
     */
    fun bind(value: Double) {
        model.value = value

        itemView.value.setText(String.format("%.2f", value))
    }
}