package com.sachinrana.nagp.ebroker.eBroker.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime

object Util {

    fun isValidTradingTime(): Boolean {
        return if (LocalDate.now().dayOfWeek == DayOfWeek.SATURDAY || LocalDate.now().dayOfWeek == DayOfWeek.SUNDAY) {
            false
        } else !(LocalDateTime.now().hour < 9 || LocalDateTime.now().hour > 17)
    }

}