package com.example.baseproject.ui.splash

import androidx.lifecycle.viewModelScope
import com.example.baseproject.R
import com.example.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {

    val actionSPlash = MutableSharedFlow<SplashActionState>()

    val splashTitle = MutableStateFlow(R.string.splash)

    init {
        viewModelScope.launch {
            delay(1000)
            actionSPlash.emit(SplashActionState.Finish)
        }
    }

}

sealed class SplashActionState {
    object Finish : SplashActionState()
}