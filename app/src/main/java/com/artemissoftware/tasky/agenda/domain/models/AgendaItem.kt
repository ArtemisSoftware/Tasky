package com.artemissoftware.tasky.agenda.domain.models

sealed class AgendaItem(
    val itemId: String? = null,
    val itemTitle: String,
    val itemDescription: String? = null,
    val itemRemindAt: Long,
    val itemTime: Long
){

    data class Reminder(
        val id: String? = null,
        val title: String,
        val description: String? = null,
        val remindAt: Long,
        val time: Long
    ) : AgendaItem(
        itemId = id,
        itemTitle = title,
        itemDescription = description,
        itemRemindAt = remindAt,
        itemTime = time
    ){

    }


    companion object{
        val mockReminder = Reminder(
            id = "fdfgdf",
            title = "This is a reminder",
            description =  "This is the description of the reminder",
            remindAt = 33L,
            time = 333L
        )
    }

}
