package com.streamwide.contacts.data.repository

import com.streamwide.contacts.data.local.ContactDao
import com.streamwide.contacts.data.model.Contact
import com.streamwide.contacts.utils.ContactsContentProvider
import com.streamwide.contacts.utils.ErrorFactory
import com.streamwide.contacts.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val contactsContentProvider : ContactsContentProvider,
    private val contactDao : ContactDao,
    private val errorFactory: ErrorFactory
) : ContactRepository {

    override suspend fun getContacts() = try {
        val localContacts = contactDao.getContacts()
        val contacts = contactsContentProvider.retrieveAllContacts()
        val isContactsChanged = localContacts != contacts
        if(isContactsChanged){
            contactDao.deleteAllContacts()
            contactDao.insertContacts(contacts)
            Resource.success(contacts)
        }else{
            Resource.success(localContacts)
        }
    }catch (ex:Exception){
        Resource.error(errorFactory.createErrorMessage(ex))
    }
}