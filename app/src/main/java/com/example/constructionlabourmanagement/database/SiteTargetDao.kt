package com.example.constructionlabourmanagement.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.constructionlabourmanagement.models.SiteTarget

@Dao
interface SiteTargetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(siteTarget: SiteTarget)

    @Query("SELECT * FROM site_targets WHERE siteId = :siteId")
    suspend fun getSiteTarget(siteId: Int): SiteTarget?

    @Query("SELECT COALESCE(SUM(targetAmount), 0) FROM site_targets st JOIN sites s ON st.siteId = s.id WHERE s.userId = :userId")
    suspend fun getTotalTargetAmountByUser(userId: Int): Double
}