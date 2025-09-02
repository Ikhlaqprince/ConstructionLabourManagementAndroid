package com.example.constructionlabourmanagement.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val username: String,
    val password: String,
    val company: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)