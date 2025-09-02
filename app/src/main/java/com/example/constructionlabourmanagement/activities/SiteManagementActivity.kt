package com.example.constructionlabourmanagement.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.constructionlabourmanagement.database.AppDatabase
import com.example.constructionlabourmanagement.databinding.ActivitySiteManagementBinding
import com.example.constructionlabourmanagement.adapters.SiteManagementPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class SiteManagementActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySiteManagementBinding
    private lateinit var db: AppDatabase
    private var siteId: Int = 0
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySiteManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        siteId = intent.getIntExtra("SITE_ID", 0)
        userId = intent.getIntExtra("USER_ID", 0)
        
        if (siteId == 0 || userId == 0) {
            finish()
            return
        }

        db = AppDatabase.getDatabase(this)
        setupViews()
    }

    private fun setupViews() {
        // Set up ViewPager with tabs
        val pagerAdapter = SiteManagementPagerAdapter(this, siteId, userId)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // Connect TabLayout with ViewPager
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(com.example.constructionlabourmanagement.R.string.labour_tab)
                1 -> getString(com.example.constructionlabourmanagement.R.string.attendance_tab)
                2 -> getString(com.example.constructionlabourmanagement.R.string.payments_tab)
                3 -> getString(com.example.constructionlabourmanagement.R.string.customer_tab)
                4 -> getString(com.example.constructionlabourmanagement.R.string.weekly_tab)
                else -> "Tab $position"
            }
        }.attach()
    }
}