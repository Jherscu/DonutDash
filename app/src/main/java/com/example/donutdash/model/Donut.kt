package com.example.donutdash.model

import androidx.annotation.StringRes

data class Donut(
    @StringRes val stringResourceId: Int,
    val toppings: List<String>,
)
