package com.example.donutdash.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.donutdash.R
import com.example.donutdash.databinding.FragmentOrderSummaryBinding
import com.example.donutdash.model.SharedViewModel

/**
 * [OrderSummaryFragment] is the final fragment in the donut order lifecycle, preceded by [ToppingsFragment].
 * It displays the order summary, and sends it to both the [LandingFragment] and the user.
 */
class OrderSummaryFragment : Fragment() {

    // Gets data binding from fragment_order_summary.xml
    private var binding: FragmentOrderSummaryBinding? = null

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to bind this fragment to a shared view model as opposed to a singular one using 'by viewModels()'
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentOrderSummaryBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            // must first be created as variables in data binding layout in corresponding fragment_order_summary.xml
            orderSummaryFragment = this@OrderSummaryFragment
            viewModel = sharedViewModel
            // associates lifecycle owner of fragment to LiveData object variables, so they can be tracked through the app
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}