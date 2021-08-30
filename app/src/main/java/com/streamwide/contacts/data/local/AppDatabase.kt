package com.streamwide.contacts.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.streamwide.contacts.data.model.Contact

@Database(entities = [Contact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao() : ContactDao

    companion object{
        const val DATABASE_NAME = "CONTACT_DATABASE"
    }
}