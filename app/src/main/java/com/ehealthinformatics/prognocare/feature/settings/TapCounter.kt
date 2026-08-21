package com.ehealthinformatics.prognocare.feature.settings

/**
 * Counts taps toward revealing the hidden server-config panel.
 * Taps that are more than [windowMillis] apart reset the counter.
 * Once the [requiredTaps] threshold is reached the panel is unlocked.
 */
class TapCounter(
    private val requiredTaps: Int = 7,
    private val windowMillis: Long = 1_500,
) {
    private var lastTapTime: Long = 0L
    private var count = 0
    var revealed = false
        private set

    fun onTap(timestampMillis: Long = System.currentTimeMillis()) {
        if (revealed) return
        if (timestampMillis - lastTapTime > windowMillis) {
            count = 0
        }
        lastTapTime = timestampMillis
        count++
        if (count >= requiredTaps) {
            revealed = true
        }
    }

    fun reset() {
        count = 0
        lastTapTime = 0L
        revealed = false
    }
}