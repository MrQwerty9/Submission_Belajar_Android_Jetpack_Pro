package com.sstudio.submissionbajetpackpro.core.utils

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private const val RESOURCE: String = "GLOBAL"
    private var idlingResource: CountingIdlingResource? = null

    fun getIdlingResource(): CountingIdlingResource? {
        idlingResource = CountingIdlingResource(RESOURCE)
        return idlingResource
    }

    fun increment() {
        idlingResource?.increment()
    }

    fun decrement() {
        if (idlingResource?.isIdleNow == false) {
            idlingResource?.decrement()
        }
    }
}