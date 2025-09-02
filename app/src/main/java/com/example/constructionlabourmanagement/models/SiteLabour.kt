package com.example.constructionlabourmanagement.models

import androidx.room.Entity

@Entity(tableName = "site_labours", primaryKeys = ["siteId", "labourId"])
data class SiteLabour(
    val siteId: Int,
    val labourId: Int
)