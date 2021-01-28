package com.sstudio.submissionbajetpackpro.core.vo

class NetworkState(val status: Status, val msg: String) {
    enum class Status {
        LOADING,
        SUCCESS,
        FAILED,
        EMPTY
    }

    companion object {
        val SUCCESS: NetworkState = NetworkState(Status.SUCCESS, "Success")
        val LOADING: NetworkState = NetworkState(Status.LOADING,"Loading")
    }
}