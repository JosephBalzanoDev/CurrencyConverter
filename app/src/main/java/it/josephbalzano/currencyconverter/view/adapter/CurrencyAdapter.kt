package it.josephbalzano.currencyconverter.view.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import it.josephbalzano.currencyconverter.R
import it.josephbalzano.currencyconverter.swap
import it.josephbalzano.currencyconverter.view.model.CurrencyItem
import kotlinx.android.synthetic.main.currency_item.view.*


class CurrencyAdapter(val listener: ViewHolderListener) :
    RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {
    private var items = mutableListOf<CurrencyItem>()

    fun updateItems(elements: List<CurrencyItem>) {
        items.clear()
        items.addAll(elements)
        this.notifyDataSetChanged()
    }

    fun swipeToUp(itemIndex: Int) {
        items.swap(itemIndex)
        notifyItemMoved(itemIndex, 0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.currency_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])
    override fun getItemCount(): Int = items.size

    interface ViewHolderListener {
        fun onItemClick(item: CurrencyItem, indexOf: Int)
        fun onValueChanged(value: Double)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var privateModel: CurrencyItem

        init {
            view.setOnClickListener {
                listener.onItemClick(privateModel, items.indexOf(privateModel))
            }

            itemView.value.addTextChangedListener {
                object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        listener.onValueChanged(s.toString().toDouble())
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }
                }
            }
        }

        fun bind(model: CurrencyItem) {
            privateModel = model

            itemView.icon.setImageResource(model.currencyCode.flagResource)
            itemView.code.text = model.currencyCode.code
            itemView.descr.text = model.currencyCode.description
            itemView.value.setText(String.format("%.4f", model.value))
        }
    }
}
