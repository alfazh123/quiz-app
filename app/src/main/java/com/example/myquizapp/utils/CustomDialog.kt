package com.example.myquizapp.utils

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.myquizapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs

class CustomDialog {
    fun showDialog(callback: (Boolean?)->Unit, context: Context, title: String = "Confirmation", message: String = "Are you sure?", buttonPositiveText: String = "Ok", buttonNegativeText: String = "Cancel") {
        val builder = MaterialAlertDialogBuilder(context)
        val inflater = View.inflate(context, R.layout.custom_dialog_layout, null)
        builder.setView(inflater)
        val dialog = builder.create()

        val titleDialog = inflater.findViewById<TextView>(R.id.title_dialog)
        titleDialog.text = title

        val messageDialog = inflater.findViewById<TextView>(R.id.message_dialog)
        messageDialog.text = message

        val positiveBtn = inflater.findViewById<Button>(R.id.btn_ok)
        positiveBtn.text = buttonPositiveText
        positiveBtn.setOnClickListener {
            callback(true)
            dialog.dismiss()
        }
        val negativeBtn = inflater.findViewById<Button>(R.id.btn_cancel)
        negativeBtn.text = buttonNegativeText
        negativeBtn.setOnClickListener {
            callback(false)
            dialog.dismiss()
        }
        dialog.show()
    }
}