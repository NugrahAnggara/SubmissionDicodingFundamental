package com.nugrahanggara.submissiondicoding.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nugrahanggara.submissiondicoding.Fragment.FollowersFragment
import com.nugrahanggara.submissiondicoding.Fragment.FollowingFragment

class SectionPagerAdapter(activity : FragmentActivity,private val data : String?) : FragmentStateAdapter(activity)
{
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment
    {
        return when(position){
            0 -> FollowingFragment.newInstance(data)
            1 -> FollowersFragment.newInstance(data)
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}