package it.josephbalzano.currencyconverter

fun <T> MutableList<T>.swap(index1: Int) {
    val itemToMove = this[index1]
    removeAt(index1)
    add(0, itemToMove)
}