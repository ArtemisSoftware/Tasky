package com.artemissoftware.core.util.extensions

import org.junit.Assert
import org.junit.Test

class StringExtTest {

    @Test
    fun `enter name with 2 words get first letter from first and second word`() {

        val name = "Bruce Wayne"
        val initials = "BW"
        Assert.assertEquals(initials, name.asInitials())
    }

    @Test
    fun `enter name with 1 words get first letter from first word`() {

        val name = "Bruce"
        val initials = "B"
        Assert.assertEquals(initials, name.asInitials())
    }
}