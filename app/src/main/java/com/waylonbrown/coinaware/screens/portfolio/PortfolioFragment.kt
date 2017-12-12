package com.waylonbrown.coinaware.screens.portfolio

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.waylonbrown.coinaware.base.BaseRecyclerViewFragment
import com.waylonbrown.coinaware.api.CoinPriceFetcher
import com.waylonbrown.coinaware.util.DummyPortfolioDataProvider
import com.waylonbrown.coinaware.util.DummyPortfolioDataProvider.PortfolioListItem
import com.waylonbrown.coinaware.screens.portfolio.PortfolioAdapter.PortfolioHeaderViewHolder
import kotlinx.android.synthetic.main.page_recyclerview.*

class PortfolioFragment : BaseRecyclerViewFragment(), PortfolioHeaderViewHolder.ListItemClickedListener {
    
    lateinit var portfolioAdapter: PortfolioAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        portfolioAdapter = PortfolioAdapter(layoutInflater, this)
        recyclerView.adapter = portfolioAdapter
//        val dividerItemDecoration = DividerItemDecoration(recyclerView.context,
//                (recyclerView.layoutManager as LinearLayoutManager).orientation)
//        dividerItemDecoration.setDrawable(activity.resources.getDrawable())
//        recyclerView.addItemDecoration(dividerItemDecoration)
        portfolioAdapter.updateItems(DummyPortfolioDataProvider().getDummyData())
        
        swipeRefreshLayout.setOnRefreshListener { 
            CoinPriceFetcher()
                    .getFromBTCtoUSDPrice(object : CoinPriceFetcher.FetchCoinPriceListener {
                        override fun coinPriceFetched(price: Float) {
                            onCoinPriceFetch(price)
                            swipeRefreshLayout.isRefreshing = false
                        }

                        override fun onFetchFail() {
                            Toast.makeText(activity, "Failure fetching coin price", 
                                    Toast.LENGTH_SHORT).show()
                            swipeRefreshLayout.isRefreshing = false
                        }
                    })
        }
    }

    fun onCoinPriceFetch(price: Float) {
        Toast.makeText(activity, "$price", Toast.LENGTH_SHORT).show()
    }
    
    override fun itemClicked(data: PortfolioListItem) {
        Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
    }

}