package com.streamwide.contacts.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.streamwide.contacts.data.model.Contact

@Dao
interface ContactDao {
    @Query("SELECT * FROM Contact")
    suspend fun getContacts(): List<Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(list: List<Contact>)

    @Query("DELETE FROM Contact")
    suspend fun deleteAllContacts()
}