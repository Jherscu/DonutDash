package com.example.donutdash.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.donutdash.R
import com.example.donutdash.databinding.FragmentFlavorBinding
import com.example.donutdash.model.SharedViewModel

/**
 * [FlavorFragment] is the second fragment in the donut order lifecycle, preceded by [NewOrderFragment]. In this view, the flavors
 * and quantity are both selected.
 */
class FlavorFragment : Fragment() {

    // Gets data binding from fragment_flavor.xml
    private var binding: FragmentFlavorBinding? = null

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to bind this fragment to a shared view model as opposed to a singular one using 'by viewModels()'
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentFlavorBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            // must first be created as variables in data binding layout in corresponding fragment_flavor.xml
            flavorFragment = this@FlavorFragment
            viewModel = sharedViewModel
            // associates lifecycle owner of fragment to LiveData object variables, so they can be tracked through the app
            lifecycleOwner = viewLifecycleOwner
        }
    }

    /**
     * Takes user to next stage of donut order.
     */
    fun nextScreen() {
        findNavController().navigate(R.id.action_flavorFragment_to_toppingsFragment)
    }

    /**
     * Resets order and returns to LandingFragment.
     */
    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_flavorFragment_to_landingFragment)
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