package com.sstudio.submissionbajetpackpro.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.sstudio.submissionbajetpackpro.favorite.di.viewModelModule
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null){

            loadKoinModules(viewModelModule)

            val pagerAdapter = context?.let { SectionsPagerAdapter(it, childFragmentManager) }
            with(pager_container){
                addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
                adapter = pagerAdapter
            }
            tab_layout.setupWithViewPager(pager_container)
        }
    }
}