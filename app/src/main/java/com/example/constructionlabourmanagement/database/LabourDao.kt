package com.example.constructionlabourmanagement.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.constructionlabourmanagement.models.Labour

@Dao
interface LabourDao {
    @Insert
    suspend fun insert(labour: Labour): Long

    @Query("SELECT * FROM labours WHERE id = :id")
    suspend fun getLabourById(id: Int): Labour?

    @Query("SELECT l.* FROM labours l JOIN site_labours sl ON l.id = sl.labourId WHERE sl.siteId = :siteId ORDER BY l.name")
    suspend fun getLaboursBySite(siteId: Int): List<Labour>

    @Query("SELECT COUNT(DISTINCT l.id) FROM labours l JOIN site_labours sl ON l.id = sl.labourId JOIN sites s ON sl.siteId = s.id WHERE s.userId = :userId")
    suspend fun getLabourCountByUser(userId: Int): Int
}