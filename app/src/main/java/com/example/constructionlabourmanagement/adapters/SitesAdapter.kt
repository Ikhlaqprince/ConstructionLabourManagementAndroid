package com.example.constructionlabourmanagement.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.constructionlabourmanagement.R
import com.example.constructionlabourmanagement.models.Site

class SitesAdapter(
    private val sites: List<Site>,
    private val onItemClick: (Site) -> Unit
) : RecyclerView.Adapter<SitesAdapter.SiteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_site, parent, false)
        return SiteViewHolder(view)
    }

    override fun onBindViewHolder(holder: SiteViewHolder, position: Int) {
        val site = sites[position]
        holder.bind(site)
        holder.itemView.setOnClickListener { onItemClick(site) }
    }

    override fun getItemCount(): Int = sites.size

    class SiteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.siteNameTextView)
        private val locationTextView: TextView = itemView.findViewById(R.id.siteLocationTextView)
        private val statusTextView: TextView = itemView.findViewById(R.id.siteStatusTextView)

        fun bind(site: Site) {
            nameTextView.text = site.name
            locationTextView.text = site.location
            statusTextView.text = site.status
        }
    }
}