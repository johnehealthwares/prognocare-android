package com.ehealthinformatics.prognocare

import com.ehealthinformatics.prognocare.feature.settings.TapCounter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TapCounterTest {

    @Test
    fun `seven taps within window reveal panel`() {
        val counter = TapCounter(requiredTaps = 7, windowMillis = 1_500)
        var now = 0L
        repeat(7) {
            counter.onTap(now)
            now += 200
        }
        assertTrue(counter.revealed)
    }

    @Test
    fun `fewer than required taps do not reveal`() {
        val counter = TapCounter(requiredTaps = 7, windowMillis = 1_500)
        repeat(6) { counter.onTap(it * 200L) }
        assertFalse(counter.revealed)
    }

    @Test
    fun `slow taps reset the counter`() {
        val counter = TapCounter(requiredTaps = 3, windowMillis = 1_000)
        counter.onTap(0)
        counter.onTap(2_000)
        counter.onTap(2_100)
        assertFalse(counter.revealed)
    }

    @Test
    fun `reset clears revealed state`() {
        val counter = TapCounter(requiredTaps = 2, windowMillis = 1_000)
        counter.onTap(0)
        counter.onTap(1)
        assertTrue(counter.revealed)
        counter.reset()
        assertFalse(counter.revealed)
    }
}