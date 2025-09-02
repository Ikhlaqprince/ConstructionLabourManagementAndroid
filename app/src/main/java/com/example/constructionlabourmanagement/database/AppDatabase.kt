package com.example.constructionlabourmanagement.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.constructionlabourmanagement.models.*

@Database(
    entities = [
        User::class,
        Site::class,
        Labour::class,
        SiteLabour::class,
        Attendance::class,
        Payment::class,
        CustomerPayment::class,
        SiteTarget::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun siteDao(): SiteDao
    abstract fun labourDao(): LabourDao
    abstract fun siteLabourDao(): SiteLabourDao
    abstract fun attendanceDao(): AttendanceDao
    abstract fun paymentDao(): PaymentDao
    abstract fun customerPaymentDao(): CustomerPaymentDao
    abstract fun siteTargetDao(): SiteTargetDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "construction_labour_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}