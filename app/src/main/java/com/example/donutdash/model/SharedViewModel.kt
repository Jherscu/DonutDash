package com.example.donutdash.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

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
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    // Date for this order
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    // Time for this order
    private val _time = MutableLiveData<String>()
    val time: LiveData<String> = _time

    // Quantity of cupcakes in order
    // private val _quantity = MutableLiveData<Int>()
    // val quantity: LiveData<String> = _quantity

    // Price of the order
    // private val _price = MutableLiveData<Double>()
    // val price: LiveData<String> = Transformations.map(_price) {

    // }

    // Reset options for order
    init {
        resetOrder()
    }

    // list of toppings
    val toppings = listOf(
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
     * Returns true if a name has not been selected for the order yet. Returns false otherwise.
     */
    fun hasNoNameSet(): Boolean {
        return _name.value.isNullOrEmpty()
    }

    /**
     * Set the pickup date for this order.
     *
     * @param pickupDate is the date set for pickup.
     */
    fun setDate(pickupDate: String) {
        if (pickupDate != "Select Pickup Date") {
            _date.value = pickupDate
        }
    }

    /**
     * Returns true if a date has not been selected for the order yet. Returns false otherwise.
     */
    fun hasNoDateSet(): Boolean {
        return _date.value.isNullOrEmpty()
    }

    /**
     * Set the pickup time for this order.
     *
     * @param pickupTime is the date set for pickup.
     */
    fun setTime(pickupTime: String) {
        if (pickupTime != "Select Pickup Time") {
            _time.value = pickupTime
        }
    }

    /**
     * Returns true if a time has not been selected for the order yet. Returns false otherwise.
     */
    fun hasNoTimeSet(): Boolean {
        return _time.value.isNullOrEmpty()
    }

    // Day to (AM to PM)
    private val _businessHours = mapOf<String, Pair<Double, Double>>(
        "Sun" to (7.00 to 5.30),
        "Mon" to (6.30 to 8.00),
        "Tue" to (6.30 to 8.00),
        //closed on Wednesday
        "Thu" to (7.00 to 8.00),
        "Fri" to (7.30 to 9.30),
        "Sat" to (6.00 to 10.00),
    )

    private val _hoursList = listOf<Double>(
        6.00,
        6.30,
        7.00,
        7.30,
        8.00,
        8.30,
        9.00,
        9.30,
        10.00,
        10.30,
        11.00,
        11.30,
        12.00,
        12.30,
        1.00,
        1.30,
        2.00,
        2.30,
        3.00,
        3.30,
        4.00,
        4.30,
        5.00,
        5.30,
        6.00,
        6.30,
        7.00,
        7.30,
        8.00,
        8.30,
        9.00,
        9.30,
        10.00,
    )

    /**
     * Returns list of available dates for pickup of order.
     */
    fun getPickupDates(): List<String> {
        val dateOptions = mutableListOf("Select Pickup Date")
        val calendarRightNow = Calendar.getInstance()

        val dayFormatter = SimpleDateFormat("EEE LLL dd", Locale.getDefault())
        val dayFormatCheck = SimpleDateFormat("EEE", Locale.getDefault())

        // If this current day is a day on which the business is open, add it as a possible delivery date
        // Then check for each other day of the week after today
        repeat (7) {
            if (dayFormatCheck.format(calendarRightNow.time) in _businessHours.keys) {
                dateOptions.add(dayFormatter.format(calendarRightNow.time))
                calendarRightNow.add(Calendar.DATE, 1)
            } else {calendarRightNow.add(Calendar.DATE, 1)}
        }

        return dateOptions.toList()
    }

    /**
     * Returns list of available times for pickup of order within selected pickup date.
     */
    fun getPickupTimes(fullDate: String): List<String> {

        val date = Regex("""\A\w\w\w""").find(fullDate)?.value

        var hours: Pair<Double, Double> = (0.0 to 0.0)

        // Gets hours of operation for selected delivery date
        for (day in _businessHours.keys) {
            if (date == day) {
                hours = _businessHours.getValue(day)
            }
        }

        return when (hours) {
            (0.0 to 0.0) -> {
                // Returns placeholder for timeslots
                listOf("")
            }
            else -> {
                // Gets list of pickup times from opening to close of business
                val start = _hoursList.indexOf(hours.first)
                val end = _hoursList.lastIndexOf(hours.second)

                val doubleList = _hoursList.subList(start, end).map { dbl -> dbl.toString() }

                val formattedList :List<String> = doubleList.map { dbl ->
                    val hour = Regex("""^\d+""").find(dbl)?.value
                    val min = Regex("""\d$""").find(dbl)?.value
                    try {
                        when (hour!!.length) {
                            2 -> "$hour:${min}0"
                            1 -> "0$hour:${min}0"
                            else -> throw IOException("Hours are invalid")
                        }
                    } catch (exception: IOException) {
                        return listOf("Time Format Error")
                    }
                }

                val finalList = mutableListOf("Select Pickup Time").plus(formattedList).toList()

                finalList
            }
        }
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
        // _quantity.value = 0
        // _price.value = 0.0
    }
}