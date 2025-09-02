package com.example.constructionlabourmanagement.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.constructionlabourmanagement.models.Payment

@Dao
interface PaymentDao {
    @Insert
    suspend fun insert(payment: Payment)

    @Query("SELECT * FROM payments WHERE siteId = :siteId ORDER BY paymentDate DESC, labourId LIMIT 50")
    suspend fun getPaymentsBySite(siteId: Int): List<Payment>

    @Query("SELECT SUM(amount) FROM payments WHERE labourId = :labourId AND siteId = :siteId AND paymentDate >= :startDate")
    suspend fun getAmountPaid(labourId: Int, siteId: Int, startDate: Long): Double
}