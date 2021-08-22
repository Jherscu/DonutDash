package com.example.donutdash.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// Donut order base prices
private const val PRICE_PER_DONUT = 3.29

private const val PRICE_FIRST_TOPPING = 0.25

private const val PRICE_PER_EXTRA_TOPPING = 0.75

// Order modifiers
private const val PRICE_SAME_DAY_PICKUP = 5.00

private const val PRICE_LARGE_ORDER = 10.00

// Tip amounts
private const val FIFTEEN_PERCENT_TIP = 0.15

private const val EIGHTEEN_PERCENT_TIP = 0.18

private const val TWENTY_PERCENT_TIP = 0.20

/**
 * [SharedViewModel] hold information pertaining to the donut order's lifecycle
 * such as price, quantity, flavor, toppings, and pickup date.
 * It also maintains the necessary calculations to keep them up to date.
 */
class SharedViewModel: ViewModel() {

    // Name for this order
    private val _name: MutableLiveData<String>()
    val name: LiveData<String> = _name

    // Date for this order
    private val _date: MutableLiveData<String>()
    val date: LiveData<String> = _date

    // Time for this order
    private val _time: MutableLiveData<String>()
    val time: LiveData<String> = _time

    // List of possible pickup days
    val dateOptions: List<String> = getPickupDate()

    // List of possible pickup times
    val timeOptions: List<String> = getPickupTime()

    // Quantity of cupcakes in order
    private val _quantity: MutableLiveData<Int>()
    val quantity: LiveData<String> = _quantity

    // Reset options for order
    init {
        resetOrder()
    }

    // list of toppings
    val toppings = listOf<String>(
        "Sprinkles",
        "Strawberries",
        "Oreo Crumble",
        "Ice Cream",
        "Caramel Drizzle",
        "Marshmallow Fluff",
        "Jam",
        "Pop Rocks",
        "NO TOPPING",)


    /**
     * Set the name for this order.
     *
     * @param customerName is the name of the customer.
     */
    fun setName(customerName: String) {
        _name.value = customerName
    }

    /**
     * Set the pickup date for this order.
     *
     * @param pickupDate is the date set for pickup.
     */
    fun setDate(pickupDate: String) {
        _date.value = pickupDate
    }

    /**
     * Set the pickup time for this order.
     *
     * @param pickupTime is the date set for pickup.
     */
    fun setDate(pickupTime: String) {
        _time.value = pickupTime
    }

    fun setQuantity(vararg donuts: Int) {
        TODO("intake donut amounts from flavor fragment and accumulate them to total # of donuts")
    }

    /**
     * Reset order by returning name, date, time, and quantity values to blank state
     */
    fun resetOrder() {
        _name.value = ""
        _date.value = ""
        _time.value = ""
        _quantity.value = 0
    }
}