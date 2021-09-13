package com.example.donutdash.ui.main

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.NumberPicker
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
        // creates the number pickers
        initPickers()
    }

    /**
     * Initializes the number pickers for the amount of donuts per flavor.
     */
    private fun initPickers() {
        val pickerArray: Array<String> = (0..25).map { n -> "$n" }.toList().toTypedArray()
        binding!!.apply {
            numberChocolate.apply {
                maxValue = 25
                minValue = 0
                displayedValues = pickerArray
                setOnKeyListener { view, keycode, _ ->
                    respondToKeyEvent(view, keycode)
                }
            }
            numberBerry.apply {
                maxValue = 25
                minValue = 0
                displayedValues = pickerArray
                setOnKeyListener { view, keycode, _ ->
                    respondToKeyEvent(view, keycode)
                }
            }
            numberVanilla.apply {
                maxValue = 25
                minValue = 0
                displayedValues = pickerArray
                setOnKeyListener { view, keycode, _ ->
                    respondToKeyEvent(view, keycode)
                }
            }
            numberCaramel.apply {
                maxValue = 25
                minValue = 0
                displayedValues = pickerArray
                setOnKeyListener { view, keycode, _ ->
                    respondToKeyEvent(view, keycode)
                }
            }
            numberTaro.apply {
                maxValue = 25
                minValue = 0
                displayedValues = pickerArray
                setOnKeyListener { view, keycode, _ ->
                    respondToKeyEvent(view, keycode)
                }
            }
            numberChurro.apply {
                maxValue = 25
                minValue = 0
                displayedValues = pickerArray
                setOnKeyListener { view, keycode, _ ->
                    respondToKeyEvent(view, keycode)
                }
            }
            numberLingonberryJam.apply {
                maxValue = 25
                minValue = 0
                displayedValues = pickerArray
                setOnKeyListener { view, keycode, _ ->
                    respondToKeyEvent(view, keycode)
                }
            }
            numberBostonCreme.apply {
                maxValue = 25
                minValue = 0
                displayedValues = pickerArray
                setOnKeyListener { view, keycode, _ ->
                    respondToKeyEvent(view, keycode)
                }
            }
            numberPowdered.apply {
                maxValue = 25
                minValue = 0
                displayedValues = pickerArray
                setOnKeyListener { view, keycode, _ ->
                    respondToKeyEvent(view, keycode)
                }
            }
            numberAppleFritter.apply {
                maxValue = 25
                minValue = 0
                displayedValues = pickerArray
                setOnKeyListener { view, keycode, _ ->
                    respondToKeyEvent(view, keycode)
                }
            }
        }
    }

    /**
     * Takes user to next stage of donut order, and sets overall amount of donuts ordered.
     */
    fun nextScreen() {
        // list of all number pickers in the fragment
        val numberPickerList: List<NumberPicker> = listOf(
            binding!!.numberChocolate,
            binding!!.numberBerry,
            binding!!.numberVanilla,
            binding!!.numberCaramel,
            binding!!.numberTaro,
            binding!!.numberChurro,
            binding!!.numberLingonberryJam,
            binding!!.numberBostonCreme,
            binding!!.numberPowdered,
            binding!!.numberAppleFritter
        )

        val tempList = mutableListOf<Int>()

        for (picker in numberPickerList) {
            if (picker.value != 0) {
                tempList.add(picker.value)
            }
        }

        val array: IntArray = tempList.toList().toIntArray()

        sharedViewModel.setOverallQuantity(*array)

        findNavController().navigate(R.id.action_flavorFragment_to_toppingsFragment)
    }

    /**
     * Close soft keyboard when enter is pressed on NumberPicker
     */
    private fun respondToKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // hide keyboard
            (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
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