package com.adrianlazaro.moviesapp.ui

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.adrianlazaro.moviesapp.common.Scope
import kotlinx.coroutines.CoroutineDispatcher

abstract class BaseViewModel(uiDispatcher: CoroutineDispatcher) : ViewModel(),
    Scope by Scope.Impl(uiDispatcher) {

    init {
        initScope()
    }

    @CallSuper
    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}