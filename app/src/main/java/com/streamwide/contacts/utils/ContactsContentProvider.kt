package com.streamwide.contacts.utils

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.ContactsContract
import com.streamwide.contacts.data.model.Contact
import javax.inject.Inject

class ContactsContentProvider @Inject constructor(private val contentResolver: ContentResolver){

    private fun retrieveAvatar(contactId: Long): String? {
        return contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            null,
            "${ContactsContract.Data.CONTACT_ID} =? AND ${ContactsContract.Data.MIMETYPE} = '${ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE}'",
            arrayOf(contactId.toString()),
            null
        )?.use {
            if (it.moveToFirst()) {
                val contactUri = ContentUris.withAppendedId(
                    ContactsContract.Contacts.CONTENT_URI,
                    contactId
                )
                Uri.withAppendedPath(
                    contactUri,
                    ContactsContract.Contacts.Photo.CONTENT_DIRECTORY
                ).toString()
            } else null
        }
    }

    private fun retrievePhoneNumber(contactId: Long): String{
        val result: MutableList<String> = mutableListOf()
        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} =?",
            arrayOf(contactId.toString()),
            null
        )?.use {
            if (it.moveToFirst()) {
                do {
                    result.add(it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)))
                } while (it.moveToNext())
            }
        }
        return result.joinToString (separator = " - ")
    }

    fun retrieveAllContacts(): List<Contact> {
        val result: MutableList<Contact> = mutableListOf()
        contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            CONTACT_PROJECTION,
            null,
             null,
            null,
            null
        )?.use {
            if (it.moveToFirst()) {
                do {
                    val contactId = it.getLong(it.getColumnIndex(CONTACT_PROJECTION[0]))
                    val name = it.getString(it.getColumnIndex(CONTACT_PROJECTION[2])) ?: ""
                    val hasPhoneNumber = it.getString(it.getColumnIndex(CONTACT_PROJECTION[3])).toInt()
                    val phoneNumber = if (hasPhoneNumber > 0) {
                        retrievePhoneNumber(contactId)
                    } else ""
                    val avatar = retrieveAvatar(contactId)
                    val contact = Contact(contactId, name, phoneNumber, avatar)
                    result.add(contact)
                } while (it.moveToNext())
            }
        }
        return result
    }

    companion object{
        val CONTACT_PROJECTION = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.HAS_PHONE_NUMBER,
        )
    }
}