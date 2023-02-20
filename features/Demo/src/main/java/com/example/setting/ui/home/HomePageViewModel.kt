package com.example.setting.ui.home

import androidx.lifecycle.viewModelScope
import com.example.core.base.BaseViewModel
import com.example.setting.model.HomePageItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

private const val TAG = "Test_Flow"

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val repository: HomeRepository
) : BaseViewModel() {

    val listHomePage = MutableStateFlow<List<HomePageItem>>(listOf())

    init {
//        getListSong()
//        useCase3()
        useCase2()
//        useCase1()
    }

    private fun getListSong() {
        repository.getDataAsync()
            .flowOn(Dispatchers.IO)
            .onStart {
                isLoading.value = true
            }.onCompletion {
                isLoading.value = false
            }.map {
                repository.handleResponse(it)
            }.onEach {
                listHomePage.value = it
            }.catch {
                messageError.value = it.message
            }.launchIn(viewModelScope)
    }

    //cho n flow chay song song, khi nao tat ca cung xong thi update len view
    private fun useCase1() {
        val flow1 = repository.delayFlow(1000)
        val flow2 = repository.delayFlow(2000)
        val flow3 = repository.delayFlow(3000)

        flow1.zip(flow2) { p1, p2 -> Pair(p1, p2) }
            .zip(flow3) { p1, p2 -> arrayListOf(p1.first, p1.second, p2) }
            .flowOn(Dispatchers.IO)
            .onStart {
                Timber.tag(TAG).d("onStart: ")
            }.onCompletion {
                Timber.tag(TAG).d("onCompletion: ${it?.message}")
            }.onEach {
                //on Result
                Timber.tag(TAG).d("onEach: $it")
            }.catch {
                messageError.value = it.message
                Timber.tag(TAG).d("catch: ${it.message}")
            }.launchIn(viewModelScope)
    }

    //cho n flow chay song song, cai nao xong truoc hien thi truoc, khi nao tat ca cung xong thi update len view
    private fun useCase2() {
        val flow1 = repository.delayFlow(1000).map { Pair("Flow1", it) }
        val flow2 = repository.delayFlow(2000).map { Pair("Flow2", it) }
        val flow3 = repository.delayFlow(3000).map { Pair("Flow3", it) }

        merge(flow1, flow2, flow3)
            .flowOn(Dispatchers.IO)
            .onStart {
                Timber.tag(TAG).d("onStart: ")
            }.onCompletion {
                Timber.tag(TAG).d("onCompletion: ${it?.message}")
            }.onEach {
                //on Result
                Timber.tag(TAG).d("onEach: ${it.first} ${it.second}")
            }.catch {
                messageError.value = it.message
                Timber.tag(TAG).d("catch: ${it.message}")
            }.launchIn(viewModelScope)
    }

    //chay tuan tu n flow, khi nao tat ca cung xong thi update len view
    private fun useCase3() {
        val flow1 = repository.delayFlow(1500)
        val flow2 = repository.delayFlow(2000)
        val flow3 = repository.delayFlow(3000)

        flow1.flatMapMerge { p1 -> flow2.map { Pair(p1, it) } }
            .flatMapConcat { p1 -> flow3.map { arrayListOf(p1.first, p1.second, it) } }
            .flowOn(Dispatchers.IO)
            .onStart {
                Timber.tag(TAG).d("onStart: ")
            }.onCompletion {
                Timber.tag(TAG).d("onCompletion: ${it?.message}")
            }.onEach {
                //on Result
                Timber.tag(TAG).d("onEach: %s", it)
            }.catch {
                messageError.value = it.message
                Timber.tag(TAG).d("catch: ${it.message}")
            }.launchIn(viewModelScope)
    }

    private fun useCase4() {

    }

}
