package com.example.donutdash.ui.main

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.donutdash.R
import com.example.donutdash.databinding.FragmentNewOrderBinding
import com.example.donutdash.model.SharedViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * [NewOrderFragment] is the first fragment displayed when starting a new order of donuts.
 * Preceded by [LandingFragment], this view displays the name field, as well as location and date options.
 */
class NewOrderFragment : Fragment() {

    // Gets data binding from fragment_new_order.xml
    private var binding: FragmentNewOrderBinding? = null

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to bind this fragment to a shared view model as opposed to a singular one using 'by viewModels()'
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentNewOrderBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            // Must first be created as variables in data binding layout in corresponding fragment_new_order.xml
            newOrderFragment = this@NewOrderFragment
            viewModel = sharedViewModel
            // Associates lifecycle owner of fragment to LiveData object variables, so they can be tracked through the app
            lifecycleOwner = viewLifecycleOwner
            // When text in name box changes, set the _name variable in sharedViewModel
            nameInputEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    sharedViewModel.setName(s.toString())
                }
            })
            nameInputEditText.setOnKeyListener { view, keycode, _ ->
                respondToKeyEvent(view, keycode)
            }
        }

        val dSpinner = binding!!.dateSpinner
        val tSpinner = binding!!.timeSpinner

        createSpinnerAdapter(requireContext(), dSpinner, sharedViewModel.getPickupDates().toTypedArray())
        createSpinnerAdapter(requireContext(), tSpinner, sharedViewModel.toppings.toTypedArray())
    }

    /**
     * Close soft keyboard when enter is pressed on EditText
     */
    private fun respondToKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // hide keyboard
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            // transfer focus to submit button
            binding?.dateSpinner?.requestFocus()
            return true
        }
        return false
    }

    /**
     * Creates a spinner adapter for the time and date spinners.
     */
    private fun createSpinnerAdapter(context: Context, spinner: Spinner, array: Array<String>) {
        ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            array
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    /**
     * Takes user to next stage of donut order.
     */
    fun nextScreen() {
        if (sharedViewModel.hasNoNameSet()) {
            // returns focus to name editText and creates explanatory snackbar
            binding?.nameInputEditText?.requestFocus()
            Snackbar.make(requireView(),"Please enter your name", Snackbar.LENGTH_LONG)
                .setAction("DISMISS", View.OnClickListener {})
                .show()
            return
        }

        findNavController().navigate(R.id.action_newOrderFragment_to_flavorFragment)
    }

    /**
     * Resets order and returns to LandingFragment.
     */
    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_newOrderFragment_to_landingFragment)
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