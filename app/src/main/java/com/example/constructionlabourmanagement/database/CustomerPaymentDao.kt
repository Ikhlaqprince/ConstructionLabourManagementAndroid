package com.example.constructionlabourmanagement.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.constructionlabourmanagement.models.CustomerPayment

@Dao
interface CustomerPaymentDao {
    @Insert
    suspend fun insert(payment: CustomerPayment)

    @Query("SELECT * FROM customer_payments WHERE siteId = :siteId ORDER BY paymentDate DESC")
    suspend fun getCustomerPaymentsBySite(siteId: Int): List<CustomerPayment>

    @Query("SELECT SUM(amount) FROM customer_payments WHERE siteId = :siteId")
    suspend fun getTotalCustomerPaymentBySite(siteId: Int): Double
}