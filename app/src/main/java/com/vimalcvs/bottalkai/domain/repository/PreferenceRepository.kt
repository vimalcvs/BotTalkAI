package com.vimalcvs.bottalkai.domain.repository

interface PreferenceRepository {
    fun setDarkMode(isDarkMode: Boolean)
    fun getDarkMode(): Boolean
    fun setCurrentLanguage(language: String)
    fun getCurrentLanguage(): String
    fun setCurrentLanguageCode(language: String)
    fun getCurrentLanguageCode(): String
    fun isProVersion(): Boolean
    fun setProVersion(isProVersion: Boolean)
    fun isFirstTime(): Boolean
    fun setFirstTime(isFirstTime: Boolean)
    fun getFreeMessageCount(): Int
    fun setFreeMessageCount(count: Int)
}