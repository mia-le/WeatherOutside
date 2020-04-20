package hu.ait.weatherapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import hu.ait.weatherapp.data.City
import hu.ait.weatherapp.data.CityWeatherResult
import kotlinx.android.synthetic.main.city_dialog.view.*
import java.util.*

class CityDialog : DialogFragment() {

    interface CityHandler {
        fun cityCreated(city: City)
    }

    lateinit var cityHandler: CityHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is CityHandler) {
            cityHandler = context
        } else {
            throw RuntimeException(
                getString(R.string.error_not_handled)
            )
        }
    }

    lateinit var etName: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setTitle(getString(R.string.dialog_name))
        val dialogView = requireActivity().layoutInflater.inflate(
            R.layout.city_dialog, null
        )
        etName = dialogView.etName

        dialogBuilder.setView(dialogView)

        dialogBuilder.setPositiveButton("Ok") { dialog, which ->
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, which ->
        }

        return dialogBuilder.create()
    }

    override fun onResume() {
        super.onResume()

        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            //Field validation
            if (etName.text.isNotEmpty()) {
                    handleCityCreate()
                dialog!!.dismiss()
            } else {
                etName.error = "This field can not be empty"
            }
        }
    }

    private fun handleCityCreate() {
        cityHandler.cityCreated(
            City(
                etName.text.toString()
            )
        )
    }
}