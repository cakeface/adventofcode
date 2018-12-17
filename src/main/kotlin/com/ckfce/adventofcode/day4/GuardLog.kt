package com.ckfce.adventofcode.day4

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GuardLog(private val log: List<String>) {

    fun sortLog(): List<String> {
        return log.asSequence()
            .sortedBy { extractDate(it) }
            .toList()
    }

    fun extractDate(line: String): LocalDateTime {
        return LocalDateTime.parse(line.subSequence(1, 17),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    }
}