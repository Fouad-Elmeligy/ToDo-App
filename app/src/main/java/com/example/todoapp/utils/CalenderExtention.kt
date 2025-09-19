package com.example.todoapp.utils

import java.util.Calendar

fun Calendar.getFormattedDate(): String {
    return "${get(Calendar.YEAR)} / ${get(Calendar.MONDAY) + 1} / ${get(Calendar.DATE)}"
}

fun Calendar.getFormattedTime(): String {
    return "${get(Calendar.HOUR_OF_DAY)} : ${get(Calendar.MINUTE)}"
}