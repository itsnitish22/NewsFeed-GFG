package com.nitishsharma.newsfeed.main.utils

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object Utility {
    //function to convert and format date in desired form
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateTime(dateTime: String): String? {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy h:mm a", Locale.ENGLISH)
        val parsedDate = LocalDateTime.parse(dateTime, inputFormatter)

        return parsedDate.format(outputFormatter)
    }

    fun Context.toast(message: String) {
        Toast.makeText(
            this, message,
            if (message.length <= 25) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
        ).show()
    }

    fun Fragment.toast(msg: String) {
        requireContext().toast(msg)
    }
}