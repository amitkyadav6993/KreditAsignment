package com.amityadav.kreditbeeandroidsample.presentation.album.activity

import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import com.amityadav.kreditbeeandroidsample.R
import com.amityadav.kreditbeeandroidsample.databinding.ActivityAlbumsBinding
import com.amityadav.kreditbeeandroidsample.domain.model.AlbumItem
import com.amityadav.kreditbeeandroidsample.domain.model.api.Status
import com.amityadav.kreditbeeandroidsample.presentation.album.viewmodel.AlbumViewModel
import com.amityadav.kreditbeeandroidsample.presentation.base.BaseActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.amityadav.kreditbeeandroidsample.presentation.base.bindingViewModel
import com.amityadav.kreditbeeandroidsample.utils.ViewPagerAdapter

class AlbumActivity  : BaseActivity() {

    private val albumList = ArrayList<AlbumItem>()
    private lateinit var mBinding: ActivityAlbumsBinding

    override val layoutId: Int get() = R.layout.activity_albums

    val mViewModel: AlbumViewModel by bindingViewModel<AlbumViewModel>(
        BR.model,
        this,
        AlbumViewModel::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = binding as ActivityAlbumsBinding

        observeAlbumList()
        initialisePager()

    }

    private fun observeAlbumList() {
        mViewModel.getAlbumListData().observe(this, { viewModelResponse ->
            when(viewModelResponse.status){
                Status.SUCCESS -> {
                    val response = viewModelResponse.data
                    if (response is List<*>) {
                        albumList.clear()
                        albumList.addAll(response as List<AlbumItem>)
                        initialisePager()
                    }else{
                        showSnackbar(getString(R.string.data_error))
                    }
                }

                Status.DEFAULT_ERROR -> {
                    showSnackbar(getString(R.string.api_error))
                }
            }

        })
    }

    private fun initialisePager() {

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        adapter.addAllTabs(albumList)
        mBinding.pager.adapter = adapter

        TabLayoutMediator(mBinding.tabs, mBinding.pager) { tab, position ->
            tab.text = if (position<albumList.size){
                albumList[position].title
            }else{
                ""
            }
        }.attach()

    }

}