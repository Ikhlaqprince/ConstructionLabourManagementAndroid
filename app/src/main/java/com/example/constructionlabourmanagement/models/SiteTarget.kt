package com.example.constructionlabourmanagement.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "site_targets",
    indices = [Index(value = ["siteId"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = Site::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("siteId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SiteTarget(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val siteId: Int,
    val targetAmount: Double
)