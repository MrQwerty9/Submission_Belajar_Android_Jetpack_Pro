package com.sstudio.submissionbajetpackpro.ui.movie.home


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.MovieHome
import com.sstudio.submissionbajetpackpro.core.utils.ListType
import com.sstudio.submissionbajetpackpro.core.utils.StartSnapHelper
import kotlinx.android.synthetic.main.item_movie_tv_home.view.*

class MovieHomeParentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemMovieClick: ((Movie) -> Unit)? = null
    var onItemClick: ((ListType) -> Unit)? = null
    private var mList = arrayListOf<MovieHome?>()
    private val listPosition = HashMap<Int, Int>()
    private var sharedPool = RecycledViewPool()
    private lateinit var context: Context

    fun updateList(list: List<MovieHome?>) {
        mList = list as ArrayList<MovieHome?>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val cellViewHolder = CellViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie_tv_home, parent, false)
        )
        cellViewHolder.onCreated()
        return cellViewHolder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        val cellViewHolder = viewHolder as CellViewHolder
        val p = if (listPosition.containsKey(position) && listPosition[position]!! >= 0) {
            listPosition[position]!!
        } else {
            0
        }
        cellViewHolder.getLinearLayoutManager().scrollToPositionWithOffset(p, 0)

        mList[position]?.let { viewHolder.bind(it) }

    }

    override fun onViewRecycled(viewHolder: RecyclerView.ViewHolder) {
        val position = viewHolder.adapterPosition
        val cellViewHolder = viewHolder as CellViewHolder
        val firstVisiblePosition =
            cellViewHolder.getLinearLayoutManager().findFirstVisibleItemPosition()
        listPosition[position] = firstVisiblePosition
        super.onViewRecycled(viewHolder)
    }


    override fun getItemCount(): Int {
        if (mList.isNullOrEmpty()) {
            return 0
        }
        return mList.size
    }

    private inner class CellViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_list_movie_now_playing)

        private var homeChildAdapter = MovieHomeChildAdapter()

        fun bind(movie: MovieHome) {
            with(itemView) {

                tv_list_type.text = when (movie.listType) {
                    ListType.NOW_PLAYING ->
                        "Now Playing"
                    ListType.TOP_RATED ->
                        "Top Rated"
                    ListType.POPULAR ->
                        "popular"
                    ListType.UPCOMING ->
                        "upcoming"
                    else ->
                        ""
                }
                tv_show_more.setOnClickListener {
                    onItemClick?.invoke(movie.listType)
                }

                //child
                homeChildAdapter.setMovie(movie)
                homeChildAdapter.onItemClick = {
                    onItemMovieClick?.invoke(it)
                }
            }
        }

        fun getLinearLayoutManager(): LinearLayoutManager {
            return recyclerView.layoutManager as LinearLayoutManager
        }

        fun onCreated() {
            // inflate inner item, find innerRecyclerView by ID…
            val innerLLM = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            innerLLM.initialPrefetchItemCount = 7 // depends on screen size
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = innerLLM
                setRecycledViewPool(sharedPool)
                adapter = homeChildAdapter

                // this is needed if you are working with CollapsingToolbarLayout, I am adding this here just in case I forget.
                isNestedScrollingEnabled = false
            }

            //optional
            StartSnapHelper().attachToRecyclerView(this.recyclerView)
        }
    }
}
