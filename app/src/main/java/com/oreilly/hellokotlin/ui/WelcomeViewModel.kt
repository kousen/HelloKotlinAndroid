package com.oreilly.hellokotlin.ui

import androidx.lifecycle.*
import com.oreilly.hellokotlin.astro.AstroApi
import com.oreilly.hellokotlin.astro.AstroResult

class WelcomeViewModel : ViewModel() {
    val astroResult: LiveData<AstroResult> = liveData {
        val result = AstroApi.retrofitService.getAstroResult()
        emit(result)
    }
}