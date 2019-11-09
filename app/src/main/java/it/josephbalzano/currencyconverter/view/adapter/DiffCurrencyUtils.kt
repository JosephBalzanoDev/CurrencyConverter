package it.josephbalzano.currencyconverter.view.adapter

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import it.josephbalzano.currencyconverter.view.model.CurrencyItem

class DiffCurrencyUtils(
    private val newItems: List<CurrencyItem>,
    private val oldItems: List<CurrencyItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].currencyCode == newItems[newItemPosition].currencyCode
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newItemPosition == 0

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val bundle = Bundle()
        bundle.putDouble("value", newItems[newItemPosition].value)
        return bundle
    }
}