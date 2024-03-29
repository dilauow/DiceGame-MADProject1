package com.example.dicegame

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation

class YouLostDialogBox : DialogFragment() {

    private var message: String? = null
    private var won: Boolean? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        arguments?.let {
            message = it.getString("MESSAGE")
            won = it.getBoolean("WON")
        }

        return activity?.let {
            // Use the Builder class for convenient dialog fraconstruction
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;

            if(won == true){
                builder.setView(inflater.inflate(R.layout.fragment_youwon,null)).setNegativeButton(R.string.Ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog

                    })
            }
            else{
                builder.setView(inflater.inflate(R.layout.fragment_you_lost,null))
                    .setNegativeButton(R.string.Ok,
                        DialogInterface.OnClickListener { dialog, id ->
                            // User cancelled the dialog
                        })
            }

            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        fun newInstance(message:String,won:Boolean) =
            YouLostDialogBox().apply {
                arguments = Bundle().apply {
                    putString("MESSAGE", message)
                    putBoolean("WON",won)
                }
             }

    }
}
