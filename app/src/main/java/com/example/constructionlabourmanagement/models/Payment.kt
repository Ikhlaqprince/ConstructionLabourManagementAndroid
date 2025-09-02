package com.example.constructionlabourmanagement.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "payments",
    indices = [Index(value = ["siteId", "labourId"])],
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
data class Payment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val siteId: Int,
    val labourId: Int,
    val amount: Double,
    val paymentDate: Long
)