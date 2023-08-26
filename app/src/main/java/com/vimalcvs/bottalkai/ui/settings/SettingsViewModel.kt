package com.vimalcvs.bottalkai.ui.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vimalcvs.bottalkai.domain.use_case.language.GetCurrentLanguageUseCase
import com.vimalcvs.bottalkai.domain.use_case.profile.SetDarkModeUseCase
import com.vimalcvs.bottalkai.domain.use_case.upgrade.IsProVersionUseCase
import com.vimalcvs.bottalkai.domain.use_case.upgrade.SetProVersionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val setDarkModeUseCase: SetDarkModeUseCase,
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val isProVersionUseCase: IsProVersionUseCase,
    private val setProVersionUseCase: SetProVersionUseCase
) :
    ViewModel() {


    val isProVersion = mutableStateOf(false)

    private val _currentLanguage = MutableStateFlow<String>("")
    val currentLanguage get() = _currentLanguage.asStateFlow()
    fun setDarkMode(isDarkMode: Boolean) = setDarkModeUseCase(isDarkMode)

    fun getCurrentLanguage() = viewModelScope.launch {
        _currentLanguage.value = getCurrentLanguageUseCase()
    }

    fun getProVersion() = viewModelScope.launch {
        isProVersion.value = isProVersionUseCase()
    }

    fun setProVersion(isPro: Boolean) {
        setProVersionUseCase(isPro)
        isProVersion.value = isPro
    }
}