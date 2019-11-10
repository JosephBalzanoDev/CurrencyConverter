package it.josephbalzano.currencyconverter

/**
 * Change position of passed index item from it to 0
 */
fun <T> MutableList<T>.swap(index1: Int) {
    val itemToMove = this[index1]
    removeAt(index1)
    add(0, itemToMove)
}