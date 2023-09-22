package com.example.plando.binding

import android.text.Editable
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

object StaticBindingAdapters {
    @JvmStatic
    @BindingAdapter("autoVisiilbe")
    fun setAutoGone(view: View, isAutoVisiilbe: Boolean) {
        view.apply {
            this.visibility = if (isAutoVisiilbe) View.GONE else View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("validation")
    fun setValidation(view: TextInputLayout, rule: Rule) {
        view.apply {
            this.applyErrorCheck(editText?.text, rule)
            editText?.addTextChangedListener { this.applyErrorCheck(it, rule) }
        }
    }

    private fun TextInputLayout.applyErrorCheck(text: Editable?, rule: Rule) {
        this.error = if (rule.validate(text.toString())) {
            this.isErrorEnabled = true
            rule.errorMessage()
        } else {
            this.isErrorEnabled = false
            null
        }
    }

    enum class Rule {
        NOT_EMPTY {
            override fun validate(string: String) = string.isEmpty()
            override fun errorMessage(): String = "Required Field!"
        };

        abstract fun validate(string: String): Boolean
        abstract fun errorMessage(): String
    }

}