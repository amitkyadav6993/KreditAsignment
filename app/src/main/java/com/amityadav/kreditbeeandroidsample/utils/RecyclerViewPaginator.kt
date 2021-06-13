package com.amityadav.kreditbeeandroidsample.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewPaginator(recyclerView: androidx.recyclerview.widget.RecyclerView) : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {

    /*
     * This is the Page Limit for each request
     * i.e. every request will fetch 19 transactions
     * */
    private val batchSize = 19L

    /*
     * Variable to keep track of the current page
     * */
    private var currentPage: Int = 0

    /*
     * This variable is used to set
     * the threshold. For instance, if I have
     * set the page limit to 20, this will notify
     * the app to fetch more transactions when the
     * user scrolls to the 18th cb_item_look of the list.
     * */
    private val threshold = 2

    /*
     * This is a hack to ensure that the app is notified
     * only once to fetch more data. Since we use
     * scrollListener, there is a possibility that the
     * app will be notified more than once when user is
     * scrolling. This means there is a chance that the
     * same data will be fetched from the backend again.
     * This variable is to ensure that this does NOT
     * happen.
     * */
    private var endWithAuto = false

    /*
     * We pass the RecyclerView to the constructor
     * of this class to get the LayoutManager
     * */
    private val layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager?

    private val startSize: Int
        get() = ++currentPage

    private val maxSize: Long
        get() = currentPage + batchSize


    /*
     * I have created two abstract methods:
     * isLastPage() where the UI can specify if
     * this is the last page - this data usually comes from the backend.
     *
     * loadMore() where the UI can specify to load
     * more data when this method is called.
     *
     * We can also specify another method called
     * isLoading() - to let the UI display a loading View.
     * Since I did not need to display this, I have
     * commented it out.
     * */
    abstract fun isLoading(): Boolean
    abstract fun isLastPage(): Boolean
    abstract fun totalPageCount(): Int

    init {
        recyclerView.addOnScrollListener(this)
        this.layoutManager = recyclerView.layoutManager
    }

    override fun onScrollStateChanged(recyclerView: androidx.recyclerview.widget.RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            val visibleItemCount = layoutManager!!.childCount
            val totalItemCount = layoutManager.itemCount

            var firstVisibleItemPosition = 0
            if (layoutManager is LinearLayoutManager) {
                firstVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

            } else if (layoutManager is androidx.recyclerview.widget.GridLayoutManager) {
                firstVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            }

            if (isLoading() || isLastPage()){
                return
            }
            //if (currentPage == 10) return

            if (visibleItemCount + firstVisibleItemPosition + threshold >= totalItemCount) {
                loadMore(startSize, maxSize)
//                if (!endWithAuto) {
//                    endWithAuto = true
//                    loadMore(startSize, maxSize)
//                }
            } else {
                endWithAuto = false
            }
        }
    }


    /*
     * I have added a reset method here
     * that can be called from the UI because
     * if we have a filter option in the app,
     * we might need to refresh the whole data set
     * */
    fun reset() {
        currentPage = 0
    }

    abstract fun loadMore(start: Int, count: Long)
}