package com.example.constructionlabourmanagement.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "labours")
data class Labour(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val contact: String? = null,
    val joiningDate: Long,
    val wageRate: Double
)