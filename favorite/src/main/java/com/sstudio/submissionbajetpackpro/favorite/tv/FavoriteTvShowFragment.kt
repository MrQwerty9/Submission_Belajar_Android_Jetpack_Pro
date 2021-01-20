package com.sstudio.submissionbajetpackpro.favorite.tv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sstudio.submissionbajetpackpro.core.ui.tv.TvAdapter
import com.sstudio.submissionbajetpackpro.favorite.R
import com.sstudio.submissionbajetpackpro.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_favorite_tv_show.*
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteTvShowFragment : Fragment() {

    private val viewModel: FavoriteTvShowViewModel by viewModel()
    private lateinit var tvAdapter: TvAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_tv_show, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        itemTouchHelper.attachToRecyclerView(rv_list_tv_show)
        if (activity != null) {
            tvAdapter = TvAdapter()

            progress_bar.visibility = View.VISIBLE
            viewModel.listTv?.observe(viewLifecycleOwner, { listTv ->
                tvAdapter.submitList(listTv)
                rv_list_tv_show.adapter = tvAdapter
                progress_bar.visibility = View.GONE
            })

            tvAdapter.onItemClick = {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_MOVIE_TV, DetailActivity.IS_MOVIE)
                intent.putExtra(DetailActivity.EXTRA_DETAIL, it.id)
                startActivity(intent)
            }

            with(rv_list_tv_show) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }
    }
    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val courseEntity = tvAdapter.getSwipedData(swipedPosition)
                courseEntity?.let { viewModel.deleteFavorite(it.id) }

                val snackbar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) { _ ->
                    courseEntity?.let { viewModel.addFavorite(it.id) }
                }
                snackbar.show()
            }
        }
    })
}