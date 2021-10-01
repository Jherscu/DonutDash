package com.example.donutdash.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.donutdash.R
import java.text.NumberFormat
import java.util.*

class LargeOrderDialogFragment : DialogFragment() {
    // This instance of the interface sends the button choice to the host activity
    internal lateinit var listener: LargeOrderDialogListener

    // Interface must be implemented in host activity to receive results
    // by overriding these methods.
    interface LargeOrderDialogListener {
        fun onLargeOrderDialogPositiveClick()
        fun onLargeOrderDialogNegativeClick()
    }

    // Instantiate the LargeOrderDialogListener before the fragment enters onCreate()
    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            // Instantiate the DialogListener so events can be sent to the host
            listener = context as LargeOrderDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                (context.toString() +
                        " must implement LargeOrderDialogListener")
            )
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let {
            // Build the dialog
            val builder = AlertDialog.Builder(it)

            builder.setTitle(R.string.large_order)

                .setMessage(
                    getString(
                        R.string.large_order_query,
                        NumberFormat.getCurrencyInstance(Locale.US)
                            .format(com.example.donutdash.model.PRICE_LARGE_ORDER).toString()
                    )
                )

                .setPositiveButton(R.string.yes) { _, _ ->
                    // Send the positive button event back to the host activity
                    listener.onLargeOrderDialogPositiveClick()
                }

                .setNegativeButton(R.string.no) { _, _ ->
                    // Send the negative button event back to the host activity
                    listener.onLargeOrderDialogNegativeClick()
                }

                .create()
        }
    }

}