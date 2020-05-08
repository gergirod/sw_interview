package ger.girod.interview.ui.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerViewScrollListener(private val gridLayoutManager: GridLayoutManager) : RecyclerView.OnScrollListener() {

    private var visibleThresholds : Int = 5
    private var currentPage : Int = 1
    private var previousTotalItemCount : Int = 0
    private var isLoading : Boolean = true
    private val startPageIndex : Int = 0

    init {
        visibleThresholds *= gridLayoutManager.spanCount
    }

    fun getLastVisibleItem(lastVisiblesPosition : List<Int>) : Int {

        var maxSize = 0

        for (i in lastVisiblesPosition.indices) {
            if (i == 0) {
                maxSize = lastVisiblesPosition[i]
            } else if (lastVisiblesPosition[i] > maxSize) {
                maxSize = lastVisiblesPosition[i]
            }
        }

        return maxSize

    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        var totalItemcount = gridLayoutManager.itemCount
        var lastVisiblesPosition = gridLayoutManager.findLastVisibleItemPosition()

        if(isLoading && (totalItemcount> previousTotalItemCount)) {
            isLoading = false
            previousTotalItemCount = totalItemcount
        }

        if(!isLoading && (lastVisiblesPosition + visibleThresholds ) > totalItemcount) {
            currentPage++
            onLoadMore(currentPage, totalItemcount, recyclerView)
            isLoading = true
        }
    }

    fun resetState() {
        this.currentPage = this.startPageIndex
        this.previousTotalItemCount = 0
        this.isLoading = true
    }

    abstract fun onLoadMore(page : Int, totalItemsCount : Int, view : RecyclerView)
}