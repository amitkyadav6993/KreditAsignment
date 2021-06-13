package com.amityadav.kreditbeeandroidsample.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.amityadav.kreditbeeandroidsample.domain.model.AlbumItem
import com.amityadav.kreditbeeandroidsample.presentation.album.fragment.PhotosFragment
import java.util.*

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val mFragmentList: MutableList<AlbumItem> = ArrayList()

    fun addAllTabs(list: ArrayList<AlbumItem>) {
        mFragmentList.addAll(list)
    }

    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        val item = mFragmentList[position]
        bundle.putString("id", item.id)
        val photoFragment = PhotosFragment()
        photoFragment.arguments = bundle
        return photoFragment
    }
}