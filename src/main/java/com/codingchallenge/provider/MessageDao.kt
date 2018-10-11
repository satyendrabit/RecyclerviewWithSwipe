package com.codingchallenge.provider

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


/**
 * Defines all operations supported on the Message table.
 */
@Dao
interface MessageDao {
    /**
     * Bulk insert of messages.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: List<Message>)

    @Update
    fun update(data: Message)

    @Query("SELECT * FROM Message")
    fun getMessageList(): LiveData<List<Message>>
}