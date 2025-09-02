package com.example.constructionlabourmanagement.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.constructionlabourmanagement.models.Site

@Dao
interface SiteDao {
    @Insert
    suspend fun insert(site: Site): Long

    @Query("SELECT * FROM sites WHERE userId = :userId ORDER BY createdAt DESC")
    suspend fun getSitesByUser(userId: Int): List<Site>

    @Query("SELECT * FROM sites WHERE id = :id AND userId = :userId")
    suspend fun getSiteByIdAndUser(id: Int, userId: Int): Site?

    @Query("SELECT COUNT(*) FROM sites WHERE userId = :userId")
    suspend fun getSiteCountByUser(userId: Int): Int

    @Query("SELECT COUNT(*) FROM sites WHERE userId = :userId AND status = 'active'")
    suspend fun getActiveSiteCountByUser(userId: Int): Int
}