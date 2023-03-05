package com.artemissoftware.core.util

import org.junit.Assert
import org.junit.Test

class StringUtilTest {

    @Test
    fun `enter name with 2 words get first letter from first and second word`() {

        val name = "Bruce Wayne"
        val initials = "BW"
        Assert.assertEquals(initials, StringUtil.getInitials(name))
    }

    @Test
    fun `enter name with 1 words get first letter from first word`() {

        val name = "Bruce"
        val initials = "BR"
        Assert.assertEquals(initials, StringUtil.getInitials(name))
    }

    @Test
    fun `enter name with more than 2 words get first letter from the first word and second letter from last word`() {

        val name = "Bruce Wayne Senior"
        val initials = "BS"
        Assert.assertEquals(initials, StringUtil.getInitials(name))
        val name2 = "Bruce Wayne Senior the third"
        val initials2 = "BT"
        Assert.assertEquals(initials2, StringUtil.getInitials(name2))
    }

    @Test
    fun `enter name with 2 words and multiple space get first letter from first and second word`() {

        val name = "Bruce   Wayne"
        val name3 = "   Bruce   Wayne    "
        val initials = "BW"
        Assert.assertEquals(initials, StringUtil.getInitials(name))
        Assert.assertEquals(initials, StringUtil.getInitials(name3))
    }
}