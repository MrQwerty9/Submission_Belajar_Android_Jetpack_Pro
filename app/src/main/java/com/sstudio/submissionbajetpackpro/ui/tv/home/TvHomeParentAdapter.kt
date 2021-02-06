package com.sstudio.submissionbajetpackpro.ui.tv.home


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.domain.model.TvHome
import com.sstudio.submissionbajetpackpro.core.utils.ListType
import com.sstudio.submissionbajetpackpro.utils.StartSnapHelper
import kotlinx.android.synthetic.main.item_movie_tv_home.view.*

class TvHomeParentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemMoreClick: ((ListType) -> Unit)? = null
    var onItemTvClick: ((Tv) -> Unit)? = null
    private var mList = arrayListOf<TvHome?>()
    private val listPosition = HashMap<Int, Int>()
    private var sharedPool = RecycledViewPool()
    private lateinit var context: Context

    fun updateList(list: List<TvHome?>) {
        mList = list as ArrayList<TvHome?>
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

        mList[position]?.let { cellViewHolder.bind(it) }
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

        private var homeChildAdapter = TvHomeChildAdapter()

        fun bind(tvHome: TvHome) {
            with(itemView) {
                tv_list_type.text = when (tvHome.listType) {
                    ListType.ON_AIR_TV_SHOW ->
                        "Now Playing"
                    ListType.TOP_RATED_TV_SHOW ->
                        "Top Rated"
                    ListType.POPULAR_TV_SHOW ->
                        "popular"
                    else ->
                        "Airing Today"
                }
                tv_show_more.setOnClickListener {
                    onItemMoreClick?.invoke(tvHome.listType)
                }
            }

            //child
            homeChildAdapter.setMovie(tvHome)
            homeChildAdapter.onItemTvClick = {
                onItemTvClick?.invoke(it)
            }
        }

        fun getLinearLayoutManager(): LinearLayoutManager {
            return recyclerView.layoutManager as LinearLayoutManager
        }

        fun onCreated() {
            val innerLLM = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = innerLLM
                setRecycledViewPool(sharedPool)
                adapter = homeChildAdapter

                isNestedScrollingEnabled = false
            }

            //optional
            StartSnapHelper().attachToRecyclerView(this.recyclerView)
        }
    }
}
