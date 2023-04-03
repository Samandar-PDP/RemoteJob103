package com.sdk.remotejobs103.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.sdk.remotejobs103.all.AllFragment
import com.sdk.remotejobs103.favorite.FavoriteFragment
import com.sdk.remotejobs103.search.SearchFragment

class ViewPagerAdapter(fragmentManager: FragmentManager):
    FragmentStatePagerAdapter(fragmentManager) {

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> AllFragment()
            1 -> SearchFragment()
            else -> FavoriteFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when(position){
            0 -> "All Jobs"
            1 -> "Search"
            else -> "Favorite"
        }
    }
}