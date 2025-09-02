package com.example.constructionlabourmanagement.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.constructionlabourmanagement.fragments.AttendanceFragment
import com.example.constructionlabourmanagement.fragments.CustomerFragment
import com.example.constructionlabourmanagement.fragments.LabourFragment
import com.example.constructionlabourmanagement.fragments.PaymentsFragment
import com.example.constructionlabourmanagement.fragments.WeeklyFragment

class SiteManagementPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val siteId: Int,
    private val userId: Int
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LabourFragment.newInstance(siteId, userId)
            1 -> AttendanceFragment.newInstance(siteId, userId)
            2 -> PaymentsFragment.newInstance(siteId, userId)
            3 -> CustomerFragment.newInstance(siteId, userId)
            4 -> WeeklyFragment.newInstance(siteId, userId)
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}