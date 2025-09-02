package com.example.constructionlabourmanagement.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.constructionlabourmanagement.database.AppDatabase
import com.example.constructionlabourmanagement.databinding.ActivityDashboardBinding
import com.example.constructionlabourmanagement.models.Site
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var db: AppDatabase
    private var userId: Int = 0
    private val sitesList = mutableListOf<Site>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getIntExtra("USER_ID", 0)
        if (userId == 0) {
            finish()
            return
        }

        db = AppDatabase.getDatabase(this)

        setupViews()
        loadData()
    }

    private fun setupViews() {
        binding.addSiteButton.setOnClickListener {
            showAddSiteDialog()
        }

        binding.addLabourButton.setOnClickListener {
            showAddLabourDialog()
        }

        binding.sitesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.sitesRecyclerView.adapter = SitesAdapter(sitesList) { site ->
            val intent = Intent(this, SiteManagementActivity::class.java)
            intent.putExtra("SITE_ID", site.id)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            val totalSites = withContext(Dispatchers.IO) {
                db.siteDao().getSiteCountByUser(userId)
            }
            binding.totalSitesText.text = totalSites.toString()

            val totalLabours = withContext(Dispatchers.IO) {
                db.labourDao().getLabourCountByUser(userId)
            }
            binding.totalLaboursText.text = totalLabours.toString()

            val activeProjects = withContext(Dispatchers.IO) {
                db.siteDao().getActiveSiteCountByUser(userId)
            }
            binding.activeProjectsText.text = activeProjects.toString()

            // Load sites
            val sites = withContext(Dispatchers.IO) {
                db.siteDao().getSitesByUser(userId)
            }
            sitesList.clear()
            sitesList.addAll(sites)
            binding.sitesRecyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun showAddSiteDialog() {
        // Implementation for add site dialog
    }

    private fun showAddLabourDialog() {
        // Implementation for add labour dialog
    }
}