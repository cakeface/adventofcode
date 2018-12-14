package com.ckfce.adventofcode.day1


class Calibrator(private val calibrations: List<String>) {

    private fun frequencyInts(): List<Int> {
        return calibrations.asSequence()
            .filter { it.isNotBlank() }
            .map { it.toInt() }
            .toList()
    }

    fun calibrate(): Int {
        return frequencyInts().asSequence().sum()
    }

    fun cycle(): Int {
        val seen = mutableListOf<Int>()
        var s = 0
        seen.add(s)
        while (true) {
            for (f in frequencyInts()) {
                s += f
                if (seen.contains(s)) {
                    return s
                }
                seen.add(s)
            }
        }
    }
}