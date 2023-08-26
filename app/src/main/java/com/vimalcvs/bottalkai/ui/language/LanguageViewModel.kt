package com.vimalcvs.bottalkai.ui.language

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vimalcvs.bottalkai.domain.use_case.language.GetCurrentLanguageCodeUseCase
import com.vimalcvs.bottalkai.domain.use_case.language.SetCurrentLanguageCodeUseCase
import com.vimalcvs.bottalkai.domain.use_case.language.SetCurrentLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val getCurrentLanguageCodeUseCase: GetCurrentLanguageCodeUseCase,
    private val setCurrentLanguageCodeUseCase: SetCurrentLanguageCodeUseCase,
    private val setCurrentLanguageUseCase: SetCurrentLanguageUseCase
) :
    ViewModel() {

    val selectedValue = mutableStateOf("")

    private val _currentLanguage = MutableStateFlow<String>("")
    val currentLanguage get() = _currentLanguage.asStateFlow()


    fun getCurrentLanguage() = viewModelScope.launch {
        _currentLanguage.value = getCurrentLanguageCodeUseCase()
        selectedValue.value = _currentLanguage.value
    }

    fun setCurrentLanguage(languageCode: String, language: String) = viewModelScope.launch {
        setCurrentLanguageCodeUseCase(languageCode)
        setCurrentLanguageUseCase(language)
    }
}