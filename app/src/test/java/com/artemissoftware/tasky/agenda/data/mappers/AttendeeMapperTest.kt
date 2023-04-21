package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.tasky.agenda.domain.models.Attendee
import com.artemissoftware.tasky.util.FakeData.attendeeDto
import com.artemissoftware.tasky.util.FakeData.attendeeEntity
import com.artemissoftware.tasky.util.FakeData.eventAttendeeDto
import org.junit.Assert
import org.junit.Test

class AttendeeMapperTest {

    @Test
    fun `map AttendeeDto to Attendee`() {
        val attendee = Attendee(
            fullName = "Bruce Wayne",
            id = "123",
            email = "batman@email.com",
            isGoing = true,
        )

        Assert.assertEquals(attendee, attendeeDto.toAttendee())
    }

    @Test
    fun `map Attendee to AttendeeEntity`() {
        val attendee = Attendee(
            fullName = "Bruce Wayne",
            id = "123",
            email = "batman@email.com",
            isGoing = true,
        )

        Assert.assertEquals(attendeeEntity, attendee.toEntity(eventId = "eventId"))
    }

    @Test
    fun `map EventAttendeeDto to AttendeeEntity`() {
        Assert.assertEquals(attendeeEntity, eventAttendeeDto.toEventEntity())
    }

    @Test
    fun `map AttendeeEntity to Attendee`() {
        val attendee = Attendee(
            fullName = "Bruce Wayne",
            id = "123",
            email = "batman@email.com",
            isGoing = true,
        )

        Assert.assertEquals(attendee, attendeeEntity.toAttendee())
    }
}
