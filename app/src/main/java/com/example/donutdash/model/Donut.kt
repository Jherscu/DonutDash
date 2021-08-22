package com.example.donutdash.model

import androidx.annotation.StringRes
import com.google.android.material.chip.ChipGroup

data class Donut(
    @StringRes val stringResourceId: Int,
    val toppingView: ChipGroup
)
