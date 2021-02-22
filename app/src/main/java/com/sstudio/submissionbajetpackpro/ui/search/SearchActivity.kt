package com.sstudio.submissionbajetpackpro.ui.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.sstudio.submissionbajetpackpro.R
import kotlinx.android.synthetic.main.activity_search_movie.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class SearchActivity : AppCompatActivity() {

    companion object{
//        var queryChannel: MutableLiveData<String> = MutableLiveData()
        var queryChannel = BroadcastChannel<String>(Channel.CONFLATED)
    }

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)

        supportActionBar?.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        val pagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        with(pager_container){
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
            adapter = pagerAdapter
        }
        tab_layout.setupWithViewPager(pager_container)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.search_activity_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager?
        if (searchManager != null) {
            val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.queryHint = resources.getString(R.string.search)
            searchView.onActionViewExpanded()
            menu.findItem(R.id.search).expandActionView()
            if (searchViewModel.query != "") {
                searchView.setQuery(searchViewModel.query, false)
                searchView.clearFocus()
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(mQuery: String): Boolean {
                    lifecycleScope.launch {
                        queryChannel.send(mQuery)
                    }
                    searchViewModel.query = mQuery
                    return true
                }

                override fun onQueryTextChange(mQuery: String): Boolean {
                    if (mQuery.isNotEmpty()) {
                        lifecycleScope.launch {
                            Log.d("mytag", "query $mQuery")
                            queryChannel.send(mQuery)
                        }
                            searchViewModel.query = mQuery
                    }
                    return true
                }
            })
        }
        return true
    }

//    override fun onDestroy() {
//        if (isFinishing) {
//            queryChannel.value = ""
//        }
//        super.onDestroy()
//    }
}