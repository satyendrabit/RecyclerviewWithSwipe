package com.codingchallenge.provider

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Defines the Message table.
 */
@Entity
data class Message(@ColumnInfo(name = "subject") var subject: String?) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    @ColumnInfo(name = "read")
    var read: Boolean? = false
}