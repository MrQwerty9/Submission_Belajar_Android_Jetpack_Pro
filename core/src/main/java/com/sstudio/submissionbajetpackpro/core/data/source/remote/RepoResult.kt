package com.sstudio.submissionbajetpackpro.core.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.vo.NetworkState

/**
 * RepoSearchResult from a search, which contains LiveData<List<Repo>> holding query data,
 * and a LiveData<String> of network error state.
 */
data class RepoResult<T>(
    val data: LiveData<PagedList<T>>,
    val state: MutableLiveData<NetworkState>
)