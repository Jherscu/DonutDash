package com.example.donutdash.model

import androidx.annotation.IntegerRes
import androidx.annotation.StringRes

data class Order(
    // Name
    @StringRes val stringNameResourceId: Int,
    // Date and time for pickup
    @IntegerRes val integerDateResourceId: Int,
    // String containing all donuts ordered
    @StringRes val stringDonutsResourceId: Int,
    // Shows order total price
    @IntegerRes val integerPriceResourceId: Int,
    // Shows order donut amount
    @IntegerRes val integerAmountResourceId: Int,
)
