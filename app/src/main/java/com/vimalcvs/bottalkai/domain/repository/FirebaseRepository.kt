package com.vimalcvs.bottalkai.domain.repository

interface FirebaseRepository {
    suspend fun isThereUpdate(): Boolean
}