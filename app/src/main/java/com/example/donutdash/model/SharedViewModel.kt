package com.example.donutdash.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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

    // Position of selected date
    private val _datePosition = MutableLiveData<Int>()
    val datePosition: LiveData<Int> = _datePosition

    // Position of selected time
    private val _timePosition = MutableLiveData<Int>()
    val timePosition: LiveData<Int> = _timePosition

    // Quantity of chocolate donuts in order
    private val _chocolateQuantity = MutableLiveData<Int>()
    val chocolateQuantity: LiveData<Int> = _chocolateQuantity

    // Quantity of berry donuts in order
    private val _berryQuantity = MutableLiveData<Int>()
    val berryQuantity: LiveData<Int> = _berryQuantity

    // Quantity of vanilla donuts in order
    private val _vanillaQuantity = MutableLiveData<Int>()
    val vanillaQuantity: LiveData<Int> = _vanillaQuantity

    // Quantity of caramel donuts in order
    private val _caramelQuantity = MutableLiveData<Int>()
    val caramelQuantity: LiveData<Int> = _caramelQuantity

    // Quantity of taro donuts in order
    private val _taroQuantity = MutableLiveData<Int>()
    val taroQuantity: LiveData<Int> = _taroQuantity

    // Quantity of churro donuts in order
    private val _churroQuantity = MutableLiveData<Int>()
    val churroQuantity: LiveData<Int> = _churroQuantity

    // Quantity of lingonberry jam donuts in order
    private val _lingonberryJamQuantity = MutableLiveData<Int>()
    val lingonberryJamQuantity: LiveData<Int> = _lingonberryJamQuantity

    // Quantity of boston creme donuts in order
    private val _bostonCremeQuantity = MutableLiveData<Int>()
    val bostonCremeQuantity: LiveData<Int> = _bostonCremeQuantity

    // Quantity of powdered donuts in order
    private val _powderedQuantity = MutableLiveData<Int>()
    val powderedQuantity: LiveData<Int> = _powderedQuantity

    // Quantity of apple fritter donuts in order
    private val _appleFritterQuantity = MutableLiveData<Int>()
    val appleFritterQuantity: LiveData<Int> = _appleFritterQuantity

    // Overall quantity of the donuts in the order
    private val _overallQuantity = MutableLiveData<Int>()
    val overallQuantity: LiveData<Int> = _overallQuantity

    // Price of the order
    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {
        cost -> cost.toString()
    }

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
     * Set the pickup date for this order, and save its position in the spinner.
     *
     * @param pickupDate is the date set for pickup.
     * @param optionPosition is its position in the spinner.
     */
    fun setDate(pickupDate: String, optionPosition: Int) {
        if (optionPosition != 0) {
            _date.value = pickupDate
            _datePosition.value = optionPosition
        }
    }

    /**
     * Returns true if a date has not been selected for the order yet. Returns false otherwise.
     */
    fun hasNoDateSet(): Boolean {
        return _date.value.isNullOrEmpty()
    }

    /**
     * Set the pickup time for this order, and save its position in the spinner.
     *
     * @param pickupTime is the date set for pickup.
     * @param optionPosition is its position in the spinner.
     */
    fun setTime(pickupTime: String, optionPosition: Int) {
        if (optionPosition != 0) {
            _time.value = pickupTime
            _timePosition.value = optionPosition
        }
        if (optionPosition == -2) {
            _time.value = pickupTime
            _timePosition.value = 0
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

                val doubleList = _hoursList.subList(start, (end + 1)).map { dbl -> dbl.toString() }

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
                val secondListA = formattedList.subList(0, formattedList.indexOf("12:00")).map { time -> "$time A.M." }

                val secondListB = formattedList.subList(formattedList.indexOf("12:00"), formattedList.size).map { time -> "$time P.M." }

                val finalList =  mutableListOf("Select Pickup Time").plus(secondListA.plus(secondListB)).toList()

                finalList
            }
        }
    }

    /**
     * Sets the quantity of whatever flavor it is passed
     *
     * @param quantity is the amount of donuts for the flavor.
     * @param flavor is the flavor of the donut to adjust.
     */
    fun setFlavorQuantity(quantity: Int, flavor: String) {
        when (flavor) {
            "Chocolate" -> _chocolateQuantity.value = quantity
            "Berry" -> _berryQuantity.value = quantity
            "Vanilla" -> _vanillaQuantity.value = quantity
            "Caramel" -> _caramelQuantity.value = quantity
            "Taro" -> _taroQuantity.value = quantity
            "Churro" -> _churroQuantity.value = quantity
            "Lingonberry Jam" -> _lingonberryJamQuantity.value = quantity
            "Boston Creme" -> _bostonCremeQuantity.value = quantity
            "Powdered" -> _powderedQuantity.value = quantity
            "Apple Fritter" -> _appleFritterQuantity.value = quantity
        }
    }

    /**
     * Sets the price of the order at each stage of the order process.
     *
     * @param currentTotal is the total after each stage of the order process.
     */
    fun setPrice(currentTotal: Double) {
        _price.value = currentTotal
    }

    /**
     * Sets the overall amount of donuts in the order.
     *
     * @param donuts is the amount for each donut in the order
     */
    fun setOverallQuantity(vararg donuts: Int) {
        _overallQuantity.value = donuts.toList().reduce {acc: Int, i: Int -> acc + i }
    }

    /**
     * Reset order by returning name, date, time, and quantity values to blank state.
     */
    fun resetOrder() {
        _name.value = ""
        _date.value = ""
        _time.value = ""
        _timePosition.value = 0
        _datePosition.value = 0
        _chocolateQuantity.value = 0
        _berryQuantity.value = 0
        _vanillaQuantity.value = 0
        _caramelQuantity.value = 0
        _taroQuantity.value = 0
        _churroQuantity.value = 0
        _lingonberryJamQuantity.value = 0
        _bostonCremeQuantity.value = 0
        _powderedQuantity.value = 0
        _appleFritterQuantity.value = 0
        _overallQuantity.value = 0
        _price.value = 0.0
    }
}