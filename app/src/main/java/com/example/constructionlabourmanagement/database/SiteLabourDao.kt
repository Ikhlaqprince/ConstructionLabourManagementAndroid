package com.example.constructionlabourmanagement.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.constructionlabourmanagement.models.SiteLabour

@Dao
interface SiteLabourDao {
    @Insert
    suspend fun insert(siteLabour: SiteLabour)

    @Query("DELETE FROM site_labours WHERE siteId = :siteId AND labourId = :labourId")
    suspend fun delete(siteId: Int, labourId: Int)

    @Query("SELECT COUNT(*) FROM site_labours WHERE siteId = :siteId")
    suspend fun getLabourCountBySite(siteId: Int): Int

    @Transaction
    suspend fun assignLabourToSite(siteId: Int, labourId: Int) {
        insert(SiteLabour(siteId, labourId))
    }
}