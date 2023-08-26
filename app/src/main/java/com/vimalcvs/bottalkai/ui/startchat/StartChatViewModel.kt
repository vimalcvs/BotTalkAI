package com.vimalcvs.bottalkai.ui.startchat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vimalcvs.bottalkai.domain.use_case.app.IsThereUpdateUseCase
import com.vimalcvs.bottalkai.domain.use_case.language.GetCurrentLanguageCodeUseCase
import com.vimalcvs.bottalkai.domain.use_case.upgrade.IsFirstTimeUseCase
import com.vimalcvs.bottalkai.domain.use_case.upgrade.IsProVersionUseCase
import com.vimalcvs.bottalkai.domain.use_case.upgrade.SetFirstTimeUseCase
import com.vimalcvs.bottalkai.domain.use_case.upgrade.SetProVersionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartChatViewModel @Inject constructor(
    private val isProVersionUseCase: IsProVersionUseCase,
    private val setProVersionUseCase: SetProVersionUseCase,
    private val isFirstTimeUseCase: IsFirstTimeUseCase,
    private val setFirstTimeUseCase: SetFirstTimeUseCase,
    private val isThereUpdateUseCase: IsThereUpdateUseCase,
    private val getCurrentLanguageCodeUseCase: GetCurrentLanguageCodeUseCase,
) :
    ViewModel() {

    val isProVersion = mutableStateOf(false)
    val isFirstTime = mutableStateOf(true)
    val isThereUpdate = mutableStateOf(false)
    val currentLanguageCode = mutableStateOf("en")


    fun isThereUpdate() = viewModelScope.launch {
        isThereUpdate.value = isThereUpdateUseCase()
    }

    fun getProVersion() = viewModelScope.launch {
        isProVersion.value = isProVersionUseCase()
    }

    fun getFirstTime() = viewModelScope.launch {
        isFirstTime.value = isFirstTimeUseCase()
    }

    fun setProVersion(isPro: Boolean) {
        setProVersionUseCase(isPro)
        isProVersion.value = isPro
    }

    fun setFirstTime(isFirstTime: Boolean) = setFirstTimeUseCase(isFirstTime)


    fun getCurrentLanguageCode() = viewModelScope.launch {
        currentLanguageCode.value = getCurrentLanguageCodeUseCase()
    }
}