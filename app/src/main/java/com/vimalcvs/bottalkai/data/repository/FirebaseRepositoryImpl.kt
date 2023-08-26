package com.vimalcvs.bottalkai.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.vimalcvs.bottalkai.domain.repository.FirebaseRepository
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) :
    FirebaseRepository {
    override suspend fun isThereUpdate(): Boolean {
        // Uncomment this block to enable Firebase Remote Config

//        try {
//            val current_version: Int = BuildConfig.VERSION_CODE
//            Log.e("version_code", current_version.toString())
//
//            val version = firestore.collection("app").document("app_info")
//                .get().await()
//            val versionCode = version.data?.get("app_version_code").toString().toInt()
//            Log.e("version_code", version.toString())
//
//            return versionCode != current_version
//
//        } catch (e: Exception) {
//            Log.e("Exception", e.toString())
//            return false
//        }

        return false
    }
}