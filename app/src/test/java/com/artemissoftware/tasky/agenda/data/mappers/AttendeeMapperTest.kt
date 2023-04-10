package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.tasky.agenda.domain.models.Attendee
import com.artemissoftware.tasky.util.FakeData.attendeeDto
import org.junit.Assert
import org.junit.Test

class AttendeeMapperTest {

    @Test
    fun `map AttendeeDto to Attendee`() {
        val attendee = Attendee(
            fullName = "Bruce Wayne",
            id = "123",
            email = "batman@email.com",
        )

        Assert.assertEquals(attendee, attendeeDto.toAttendee())
    }
}
