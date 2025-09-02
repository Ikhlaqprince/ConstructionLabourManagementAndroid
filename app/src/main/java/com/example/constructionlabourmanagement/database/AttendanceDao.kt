package com.example.constructionlabourmanagement.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.constructionlabourmanagement.models.Attendance

@Dao
interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(attendance: Attendance)

    @Query("SELECT * FROM attendance WHERE siteId = :siteId ORDER BY attendanceDate DESC, labourId LIMIT 50")
    suspend fun getAttendanceBySite(siteId: Int): List<Attendance>

    @Query("SELECT SUM(attendanceType) FROM attendance WHERE labourId = :labourId AND siteId = :siteId AND attendanceDate >= :startDate")
    suspend fun getDaysWorked(labourId: Int, siteId: Int, startDate: Long): Double
}