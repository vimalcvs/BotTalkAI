package com.vimalcvs.bottalkai.domain.use_case.profile

import com.vimalcvs.bottalkai.domain.repository.PreferenceRepository
import javax.inject.Inject

class SetDarkModeUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke(isDarkMode: Boolean) = preferenceRepository.setDarkMode(isDarkMode)
}
