package com.ckfce.adventofcode.day4

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GuardLog(private val log: List<String>) {
    val sorted = sortLog()
    val days = mutableListOf<SleepRecord>()

    fun sortLog(): List<String> {
        return log.asSequence()
            .sortedBy { extractDate(it) }
            .toList()
    }

    fun extractDate(line: String): LocalDateTime {
        return LocalDateTime.parse(line.subSequence(1, 17),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    }

    fun parse() {
        var position = 0
        while(position != log.size) {
            position = parseDayRecord(position)
        }
    }

    fun parseDayRecord(start: Int): Int {
        // start guard
        val guardId = extractGuard(sorted[start])
        val date = extractDate(sorted[start]).toLocalDate()

        var currentLine = start + 1
        while (currentLine != sorted.size && notGuard(sorted[currentLine])) {
            days.add(
                SleepRecord(date, guardId,
                    extractDate(sorted[currentLine]).minute,
                    extractDate(sorted[currentLine + 1]).minute)
            )
            currentLine += 2
        }
        return currentLine
    }

    fun notGuard(line: String): Boolean {
        return !line.contains("Guard")
    }

    fun extractGuard(e: String): Int {
        return "Guard #([\\d]+) begins"
            .toRegex()
            .find(e)
            ?.groups
            ?.get(1)
            ?.value!!.toInt()
    }

    fun findSleepiest(): Int {
        val guardMinutesAsleep = mutableMapOf<Int, Int>()
        for (sr in days) {
            val c = guardMinutesAsleep[sr.guardId] ?: 0
            guardMinutesAsleep[sr.guardId] = c + (sr.endMinute - sr.startMinute)
        }

        var sleepiestId = 0
        var sleepiestMinutes = 0
        for (guardAndMinute in guardMinutesAsleep.entries) {
            if (guardAndMinute.value > sleepiestMinutes) {
                sleepiestId = guardAndMinute.key
                sleepiestMinutes = guardAndMinute.value
            }
        }

        return sleepiestId
    }

    /**
     * Find the minute that a guard is most likely to be asleep.
     */
    fun yawnyMinute(guardToCheckId: Int): Int {
        val minuteToDaysSlept = mutableMapOf<Int, Int>()
        for (sr in days) {
            if (sr.guardId == guardToCheckId) {
                for (i in sr.startMinute .. sr.endMinute) {
                    if (minuteToDaysSlept[i] == null) {
                        minuteToDaysSlept[i] = 1
                    } else {
                        minuteToDaysSlept[i] = minuteToDaysSlept[i]!!.plus( 1)
                    }
                }
            }
        }

        var sleepiestMinute = 0
        var daysAsleep = 0
        for (md in minuteToDaysSlept.entries) {
            if (md.value > daysAsleep) {
                sleepiestMinute = md.key
                daysAsleep = md.value
            }
        }

        return sleepiestMinute
    }

    fun frequentMinute(): GuardMinute {
        val guardMinuteCounts = mutableMapOf<GuardMinute, Int>()
        for (sr in days) {
            for (i in sr.startMinute .. sr.endMinute) {
                val gm = GuardMinute(sr.guardId, i)
                val c = guardMinuteCounts[gm] ?: 0
                guardMinuteCounts[gm] = c + 1
            }
        }

        var maxGuardMinute = GuardMinute(0, 0)
        var maxCount = 0
        for (gmc in guardMinuteCounts.entries) {
            if (gmc.value > maxCount) {
                maxGuardMinute = gmc.key
                maxCount = gmc.value
            }
        }
        return maxGuardMinute
    }
}

data class SleepRecord(val date: LocalDate, val guardId: Int,
    val startMinute: Int, val endMinute: Int)

data class GuardMinute(val guardId: Int, val minute: Int)