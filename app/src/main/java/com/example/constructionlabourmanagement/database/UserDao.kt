package com.example.constructionlabourmanagement.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.constructionlabourmanagement.models.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): User?

    @Query("SELECT COUNT(*) FROM users WHERE username = :username")
    suspend fun checkUsernameExists(username: String): Int
}