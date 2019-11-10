package it.josephbalzano.currencyconverter.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import it.josephbalzano.currencyconverter.R
import it.josephbalzano.currencyconverter.swap
import it.josephbalzano.currencyconverter.view.adapter.viewholder.CurrencyHolder
import it.josephbalzano.currencyconverter.view.model.CurrencyItem

/**
 * Created by Joseph Balzano 06/11/2019
 */
class CurrencyAdapter(private val listener: ViewHolderListener) :
    RecyclerView.Adapter<CurrencyHolder>() {
    private var items = mutableListOf<CurrencyItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder =
        CurrencyHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.currency_item,
                parent,
                false
            ), listener, items
        )

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) =
        holder.bind(items[position])

    override fun onBindViewHolder(
        holder: CurrencyHolder,
        position: Int,
        payloads: MutableList<Any>
    ) =
        if (payloads.isEmpty()) super.onBindViewHolder(holder, position, payloads)
        else holder.bind(items[position].value)

    override fun getItemCount(): Int = items.size

    fun updateItems(elements: List<CurrencyItem>) {
        val diffCallback = DiffCurrencyUtils(elements, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)

        items.clear()
        items.addAll(elements)
    }

    fun swipeToUp(itemIndex: Int) {
        items.swap(itemIndex)
        notifyItemMoved(itemIndex, 0)
        notifyItemChanged(0)
    }

    interface ViewHolderListener {
        fun onItemClick(item: CurrencyItem, indexOf: Int)
        fun onValueChanged(value: Double)
    }
}
