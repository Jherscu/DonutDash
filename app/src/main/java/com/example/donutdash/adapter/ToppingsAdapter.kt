package com.example.donutdash.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.donutdash.R
import com.example.donutdash.model.Donut
import com.example.donutdash.model.SharedViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class ToppingsAdapter(
    val context: Context,
    private val dataset: List<Donut>,
    val viewModel: SharedViewModel
) : RecyclerView.Adapter<ToppingsAdapter.ToppingsViewHolder>() {

    class ToppingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val donutString: TextView = view.findViewById(R.id.donut_string)
        val chipGroup: ChipGroup = view.findViewById(R.id.donut_chip_group)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToppingsViewHolder {
        // Create a new view

        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.donut_item, parent, false)

        return ToppingsViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ToppingsViewHolder, position: Int) {

        val toppingList = mutableListOf<String>()

        // Set donut string
        val item = dataset[position]
        holder.donutString.text = context.resources.getString(item.stringResourceId)

        // Requires at least one selection in chipGroup to be picked
        holder.chipGroup.isSelectionRequired = true

        // Set toppings chipgroup
        val toppings = mapOf(
            R.id.chip_0 to "Sprinkles",
            R.id.chip_1 to "Strawberries",
            R.id.chip_2 to "Oreo Crumble",
            R.id.chip_3 to "Ice Cream",
            R.id.chip_4 to "Caramel Drizzle",
            R.id.chip_5 to "Marshmallow Fluff",
            R.id.chip_6 to "Jam",
            R.id.chip_7 to "Pop Rocks",
            R.id.chip_8 to "NO TOPPING",
        )

        // Inflates a choice style chip from its layout for each topping,
        // and assigns its text
        for (topping in toppings) {

            val chip: Chip = LayoutInflater.from(context)
                .inflate(R.layout.choice_chip, holder.chipGroup, false) as Chip

            chip.text = topping.value

            chip.id = topping.key

            // If the chip is already selected on click, the topping is removed.
            // If the chip is not selected on click, the topping is added.
            chip.setOnClickListener {
                if (it.isSelected) {
                    toppingList.remove(chip.text.toString())
                } else {
                    toppingList.add(chip.text.toString())
                }
            }

            if (topping.key == R.id.chip_8) {
                chip.isSelected = true
            }

            holder.chipGroup.addView(chip)

            // viewModel.addToDonutList(chip.text.toString(), toppingList)
        }

    }

    override fun getItemCount(): Int = dataset.size

}