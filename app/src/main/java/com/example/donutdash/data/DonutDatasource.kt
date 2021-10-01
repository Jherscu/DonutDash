package com.example.donutdash.data

import com.example.donutdash.R
import com.example.donutdash.model.Donut

class DonutDatasource {

    private fun getStringResourceByName(name: String): Int {
        return when (name) {
            "Chocolate" -> R.string.chocolate
            "Berry" -> R.string.berry
            "Vanilla" -> R.string.vanilla
            "Caramel" -> R.string.caramel
            "Taro" -> R.string.taro
            "Churro" -> R.string.churro
            "Lingonberry Jam" -> R.string.lingonberry_jam
            "Boston Creme" -> R.string.boston_creme
            "Powdered" -> R.string.powdered
            "Apple Fritter" -> R.string.apple_fritter
            else -> -1
        }
    }

    fun loadDonuts(flavorMap: Map<String, Int>): List<Donut> {
        val donutList = mutableListOf<Donut>()
        for (entry in flavorMap) {
            repeat(entry.value) {
                donutList.add(Donut(getStringResourceByName(entry.key)))
            }
        }
        return donutList
    }
}