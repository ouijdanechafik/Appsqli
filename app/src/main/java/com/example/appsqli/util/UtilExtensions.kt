package com.example.appsqli.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.Toast



object UtilExtensions {

    fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
        val intent = Intent(this, it)
        intent.putExtras(Bundle().apply(extras))
        startActivity(intent)
    }

    fun Context.myToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun EditText.setTextEditable(text: String) {
        this.text = Editable.Factory.getInstance().newEditable(text)
    }
}