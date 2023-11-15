package com.estudos.estatistica.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.hideKeyboard(vararg editText: AppCompatEditText){
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    editText.forEach {
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}