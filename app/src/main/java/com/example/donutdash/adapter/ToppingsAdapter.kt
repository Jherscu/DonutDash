package com.example.donutdash.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.donutdash.R
import com.example.donutdash.model.Donut
import com.google.android.material.chip.ChipGroup

class ToppingsAdapter(
    val context: Context,
    private val dataset: List<Donut>
) : RecyclerView.Adapter<ToppingsAdapter.ToppingsViewHolder>() {

    class ToppingsViewHolder(view: View, context: Context) : RecyclerView.ViewHolder(view) {
        val donutString: TextView = TextView(context)
        val chipGroup: ChipGroup = view.findViewById(R.id.view_topping)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToppingsViewHolder {
        //create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_topping, parent, false)

        return ToppingsViewHolder(adapterLayout, context)
    }

    override fun onBindViewHolder(holder: ToppingsViewHolder, position: Int) {
        val item = dataset[position]
        holder.donutString.text = context.resources.getString(item.stringResourceId)
    }

    override fun getItemCount(): Int = dataset.size

}