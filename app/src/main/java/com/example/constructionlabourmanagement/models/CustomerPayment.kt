package com.example.constructionlabourmanagement.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "customer_payments",
    indices = [Index(value = ["siteId"])],
    foreignKeys = [
        ForeignKey(
            entity = Site::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("siteId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CustomerPayment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val siteId: Int,
    val amount: Double,
    val paymentDate: Long
)