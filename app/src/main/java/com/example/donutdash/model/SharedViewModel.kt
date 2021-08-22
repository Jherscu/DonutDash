package com.example.donutdash.model

import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    /**
     * [SharedViewModel] hold information pertaining to the donut order's lifecycle
     * such as price, quantity, flavor, toppings, and pickup date.
     * It also maintains the necessary calculations to keep them up to date.
     */

    val toppings = listOf<String>(
        "Sprinkles",
        "Strawberries",
        "Oreo Crumble",
        "Ice Cream",
        "Caramel Drizzle",
        "Marshmallow Fluff",
        "Jam",
        "Pop Rocks",)

}