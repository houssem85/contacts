package com.streamwide.contacts.data.repository

import com.streamwide.contacts.data.model.Contact
import com.streamwide.contacts.utils.Resource

interface ContactRepository {
    suspend fun getContacts() : Resource<List<Contact>>
}