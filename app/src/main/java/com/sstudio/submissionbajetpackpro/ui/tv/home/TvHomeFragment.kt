package com.sstudio.submissionbajetpackpro.ui.tv.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.utils.DataTemp
import com.sstudio.submissionbajetpackpro.core.utils.Params
import com.sstudio.submissionbajetpackpro.ui.detail.DetailActivity
import com.sstudio.submissionbajetpackpro.ui.detail.DetailData
import com.sstudio.submissionbajetpackpro.ui.search.SearchActivity
import com.sstudio.submissionbajetpackpro.ui.tv.list.TvListActivity
import kotlinx.android.synthetic.main.fragment_tv_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class TvHomeFragment : Fragment() {
    private lateinit var homeParentAdapter: TvHomeParentAdapter
    private val viewModel: TvHomeViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tv_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {

            setHasOptionsMenu(true)

            homeParentAdapter = TvHomeParentAdapter()
            observeData()
            swipe_layout.setOnRefreshListener {
                viewModel.refresh()
                observeData()
                swipe_layout.isRefreshing = false
            }
            with(rv_list_tv_show) {
                layoutManager = LinearLayoutManager(context)
                adapter = homeParentAdapter
//                setHasFixedSize(true)
            }
            homeParentAdapter.onItemMoreClick = {
                val intent = Intent(context, TvListActivity::class.java)
                intent.putExtra(TvListActivity.PARAMS_EXTRA, Params.MovieParams(listType = it))
                startActivity(intent)
            }
            homeParentAdapter.onItemTvClick = {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DETAIL, DetailData(it.id, it.originalName, DetailData.Type.TV_SHOW))
                startActivity(intent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_button_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.search -> {
                startActivity(Intent(context, SearchActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeData() {
        viewModel.listTv?.observe(viewLifecycleOwner) {
            if (it != null) {
                val listTvHome = it.map { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            progress_bar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            progress_bar.visibility = View.GONE
                        }
                        is Resource.Error -> {
                            progress_bar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                    resource.data
                }
                homeParentAdapter.updateList(listTvHome)
            }
        }

        viewModel.listAllGenre?.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let { DataTemp.listTvGenre = it}
                }
            }
        }
    }
}
