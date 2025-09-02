package com.example.constructionlabourmanagement.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "attendance",
    indices = [Index(value = ["siteId", "labourId", "attendanceDate"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = Site::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("siteId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Labour::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("labourId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Attendance(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val siteId: Int,
    val labourId: Int,
    val attendanceDate: Long,
    val attendanceType: Double // 1.0 (full day), 0.5 (half day), 0.0 (absent)
)