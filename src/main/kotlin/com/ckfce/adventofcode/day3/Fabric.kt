package com.ckfce.adventofcode.day3

/**
 * size is square inches
 */
class Fabric(private val size: Int) {

    val inches = Array(size) { Array(size) { mutableListOf<Claim>()} }
    val claims = mutableListOf<Claim>()

    fun measureClaim(claimStr: String) {
        val claim = parseClaim(claimStr)
        claims.add(claim)

        for (w in 0 until  claim.width) {
            for (h in 0 until claim.height) {
                inches[claim.topOffset + h][claim.leftOffset + w].add(claim)
            }
        }
    }

    fun parseClaim(claimStr: String): Claim {
        val splitOnAt = claimStr
            .substring(1)
            .split("@")
        val number = splitOnAt[0].trim().toInt()
        val coordHalves = splitOnAt[1].split(":")
        val offsets = coordHalves[0].split(",")
        val leftOffset = offsets[0].trim().toInt()
        val topOffset = offsets[1].trim().toInt()
        val dimensions = coordHalves[1].split("x")
        val width = dimensions[0].trim().toInt()
        val height = dimensions[1].trim().toInt()

        return Claim(number, leftOffset, topOffset, width, height)
    }

    fun countOverlappingInches(): Int {
        var overlapping = 0
        for (y in 0 until size) {
            for (x in 0 until size) {
                if (inches[y][x].size > 1) {
                    overlapping++
                }
            }
        }
        return overlapping
    }

    fun findCleanClaims(): List<Claim> {
        return claims.asSequence()
            .filter { noOverlaps(it) }
            .toList()
    }

    private fun noOverlaps(claim: Claim): Boolean {
        for (w in 0 until  claim.width) {
            for (h in 0 until claim.height) {
                if (inches[claim.topOffset + h][claim.leftOffset + w].size > 1) {
                    return false
                }
            }
        }
        return true
    }

}

data class Claim(val number: Int,
    val leftOffset: Int, val topOffset: Int,
    val width: Int, val height: Int)

