package com.streamwide.contacts.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey
    val contactId: Long,
    val name: String,
    val phoneNumber: String,
    val avatarPath: String?
)