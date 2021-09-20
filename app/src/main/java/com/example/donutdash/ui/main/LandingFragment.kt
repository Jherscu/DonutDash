package com.example.donutdash.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.donutdash.R
import com.example.donutdash.databinding.FragmentLandingBinding
import com.example.donutdash.model.SharedViewModel

/**
 * [LandingFragment] is the home fragment for the Donut Dash App.
 * It displays all pending orders as well as the new order and cancel order buttons.
 */
class LandingFragment : Fragment() {

    // Gets data binding from fragment_landing.xml
    private var binding: FragmentLandingBinding? = null

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to bind this fragment to a shared view model as opposed to a singular one using 'by viewModels()'
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentLandingBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            // must first be created as variables in data binding layout in corresponding fragment_landing.xml
            landingFragment = this@LandingFragment
            viewModel = sharedViewModel
            // associates lifecycle owner of fragment to LiveData object variables, so they can be tracked through the app
            lifecycleOwner = viewLifecycleOwner
        }
    }

    fun loadOrders() {
        TODO("take completed order/orders and load into recyclerview")
    }

    override fun onResume() {
        super.onResume()
        /* if list of saved, completed orders exists */
        //   loadOrders()
    }

    fun startNewOrder() {
        findNavController().navigate(R.id.action_landingFragment_to_newOrderFragment)
    }

    fun cancelExistingOrder() {
        TODO(
            "Removes existing order from recyclerview and sends dialog or" +
                    " snackbar or dropdown that order is canceled with vendor"
        )
    }


    /**
     * This method is called when the fragment is destroyed. The added implementation
     * resets the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}