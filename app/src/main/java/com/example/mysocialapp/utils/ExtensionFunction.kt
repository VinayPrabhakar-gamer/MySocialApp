package com.example.mysocialapp.utils

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun TextInputEditText.isInputValid(parentLayout: TextInputLayout, errorMessage: String): Boolean {

    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            parentLayout.error = null
        }

        override fun afterTextChanged(p0: Editable?) {}

    })

    if(this.text.toString().isBlank()) {
        parentLayout.error = errorMessage
        return false
    }

    return true
}