package com.sstudio.submissionbajetpackpro.core.data

import android.util.Log
import com.sstudio.submissionbajetpackpro.core.data.source.remote.ApiResponse
import com.sstudio.submissionbajetpackpro.core.utils.AppExecutors
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {

    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(Resource.Loading())
//            emitAll(loadFromDB().map { Resource.Success(it) })
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
//                    Handler().postDelayed(Runnable { Log.d("mytag", "networkounce success ${apiResponse.data}") }, 3000)
//                    deleteOldDB()
//                    saveCallResult(apiResponse.data)
                    emit(Resource.Success(apiResponse.data as ResultType))
//                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is ApiResponse.Empty -> {
                    Log.d("mytag", "networkounce empty")
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is ApiResponse.Failed -> {
                    Log.d("mytag", "networkounce error")
                    onFetchFailed()
                    emit(Resource.Error<ResultType>(apiResponse.errorMessage))
                }
            }
        } else {
            emitAll(loadFromDB().map { Resource.Success(it) })
        }
    }

    protected abstract suspend fun showData(data: RequestType): ResultType?

    protected fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun deleteOldDB()

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resource<ResultType>> = result
}