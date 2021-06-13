package com.amityadav.kreditbeeandroidsample.presentation.album.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.GridLayoutManager
import com.amityadav.kreditbeeandroidsample.R
import com.amityadav.kreditbeeandroidsample.databinding.FragmentPhotosBinding
import com.amityadav.kreditbeeandroidsample.domain.model.PhotoItem
import com.amityadav.kreditbeeandroidsample.domain.model.api.Status
import com.amityadav.kreditbeeandroidsample.presentation.album.adapter.PhotosAdapter
import com.amityadav.kreditbeeandroidsample.presentation.album.viewmodel.PhotoViewModel
import com.amityadav.kreditbeeandroidsample.presentation.base.BaseFragment
import com.amityadav.kreditbeeandroidsample.presentation.base.bindingViewModel
import com.amityadav.kreditbeeandroidsample.utils.ClickStatus
import com.amityadav.kreditbeeandroidsample.utils.RecyclerViewPaginator

class PhotosFragment : BaseFragment() {

    lateinit var mBinding: FragmentPhotosBinding
    private var albumId: String? = null

    //all video list
    private var gridLayoutManager: GridLayoutManager? = null
    private var photosList: ArrayList<PhotoItem>? = null
    private var photosAdapter: PhotosAdapter? = null

    //pagination
    private val LIMIT = 10
    private var TOTAL_PAGES = 100
    private val PAGE_START = 1
    private var CURRENT_PAGE = PAGE_START
    private var isLoading = false
    private var isLastPage = false


    override val layoutId: Int get() = R.layout.fragment_photos

    val mViewModel: PhotoViewModel by bindingViewModel(
            BR.photoViewModel,
            this,
        PhotoViewModel::class.java
    )

    val mContext: Context get() = context!!
    val mActivity: Activity get() = activity!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = binding as FragmentPhotosBinding

        setupUi()
        init()
        setViewModel()

    }

    fun setupUi(){
        albumId = arguments?.getString("id")
        firstApiCall()
    }


    fun setViewModel() {
        observePhotoResponse()
    }

    fun init() {
        photosList = ArrayList<PhotoItem>()
        mBinding.swipeRefresh.setOnRefreshListener {
            if (photosAdapter != null) {
                photosList?.clear()
                photosAdapter?.clearDataList()
            }

            if (mBinding.swipeRefresh.isEnabled) {
                isLastPage = false
                isLoading = false
                CURRENT_PAGE = 1
                TOTAL_PAGES = 0
                mBinding.swipeRefresh.isRefreshing = false
                firstApiCall()
            }
        }

        setPhotosAdapter()
        observeRecycleViewScroll()
    }

    fun firstApiCall(){
        setScreenVisibility(ClickStatus.ON_SHOW_SHIMMER, null)
        mViewModel.fetchPhotoListData(albumId, LIMIT, CURRENT_PAGE)
    }

    fun observeRecycleViewScroll(){
        mBinding.allRecyclerView.clearOnScrollListeners()
        mBinding.allRecyclerView.addOnScrollListener(object : RecyclerViewPaginator(mBinding.allRecyclerView) {
            override fun loadMore(start: Int, count: Long) {
                isLoading = true
                CURRENT_PAGE += 1
                mBinding.allRecyclerView.post {
                    photosAdapter?.addNullData()
                    loadAllNextPage()
                }
            }

            override fun totalPageCount(): Int {
                return TOTAL_PAGES
            }

            override fun isLastPage(): Boolean {
                return CURRENT_PAGE == TOTAL_PAGES
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })
    }

    private fun loadAllNextPage() {
        mViewModel.fetchPhotoListData(albumId, LIMIT, CURRENT_PAGE)
    }

    private fun setPhotosAdapter() {
        photosAdapter = PhotosAdapter(photosList!!)
        gridLayoutManager = GridLayoutManager(mContext, 3)
        mBinding.allRecyclerView.layoutManager = gridLayoutManager
        mBinding.allRecyclerView.adapter = photosAdapter
    }

    private fun observePhotoResponse() {
        mViewModel.getPhotoListData().observe(viewLifecycleOwner) { viewModelResponse ->
            isLoading = false
            when (viewModelResponse.status) {
                Status.SUCCESS -> if (viewModelResponse.data is List<*>) {
                    val response = viewModelResponse.data as ArrayList<PhotoItem>
                    if (!response.isNullOrEmpty()) {
                        if (CURRENT_PAGE == 1) {
                            photosAdapter?.setDataList(response)
                        } else {
                            photosAdapter?.removeNull()
                            photosAdapter?.addAllData(response)
                        }
                        setScreenVisibility(ClickStatus.ON_SHOW_DATA, null)
                    } else {
                        handleApiError()
                    }
                }

                Status.DEFAULT_ERROR -> {
                    if (photosAdapter?.itemCount==0) {
                        setScreenVisibility(ClickStatus.ON_SHOW_ERROR, viewModelResponse.data as String)
                    }else{
                        handleApiError()
                    }
                }

            }
        }
    }

    fun handleApiError(){
        if (CURRENT_PAGE != 1) {
            if (CURRENT_PAGE<TOTAL_PAGES){
                photosAdapter?.showRetry()
            }else {
                isLastPage = true
                photosAdapter?.removeNull()
            }
        }else {
            setScreenVisibility(ClickStatus.NO_DATA, getString(R.string.data_error))
        }
    }

    private fun setScreenVisibility(status: ClickStatus, errorMessage: String?) {
        when (status) {
            ClickStatus.ON_SHOW_DATA -> {
                stopShimmerEffect()
                mBinding.shimmerVideoList.visibility = View.GONE
            }

            ClickStatus.ON_SHOW_ERROR -> {
                stopShimmerEffect()
                mBinding.shimmerVideoList.visibility = View.GONE
                showSnackbar(resources.getString(R.string.api_error))
            }
            ClickStatus.NO_INTERNET -> {
                stopShimmerEffect()
                mBinding.shimmerVideoList.visibility = View.GONE
                showSnackbar(resources.getString(R.string.internet_check))
            }
            ClickStatus.ON_SHOW_SHIMMER -> {
                startShimmerEffect()
                mBinding.shimmerVideoList.visibility = View.VISIBLE
            }
            ClickStatus.NO_DATA -> {
                stopShimmerEffect()
                mBinding.shimmerVideoList.visibility = View.GONE
                showSnackbar(resources.getString(R.string.data_error))
            }
        }
    }

    fun stopShimmerEffect() {
        if (mBinding.shimmerVideoList.isShimmerStarted) {
            mBinding.shimmerVideoList.stopShimmer()
        }
    }

    fun startShimmerEffect() {
        if (!mBinding.shimmerVideoList.isShimmerStarted) {
            mBinding.shimmerVideoList.startShimmer()
        }
    }
}