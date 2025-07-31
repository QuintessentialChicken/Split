package com.example.split.utils

import android.R.attr.digits
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.ui.text.TextRange
import androidx.core.text.isDigitsOnly
import kotlin.math.min

class DigitOnlyInputTransformation : InputTransformation {
    override fun TextFieldBuffer.transformInput() {
        if (!asCharSequence().isDigitsOnly()) revertAllChanges()
    }
}

// TODO This makes it so that backspace deletes the entire thing
class CurrencyOutputTransformation : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        val digits = this.toString().filter{ it.isDigit() }

        // Convert digits to cents (e.g., "1234" -> "12.34")
        val cents = digits.toIntOrNull() ?: return

        replace(0, length, formatCurrency(cents, "EUR"))
    }
}