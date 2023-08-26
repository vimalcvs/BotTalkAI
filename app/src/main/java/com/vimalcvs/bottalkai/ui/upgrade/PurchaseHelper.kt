package com.vimalcvs.bottalkai.ui.upgrade

import android.app.Activity
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.google.common.collect.ImmutableList
import com.vimalcvs.bottalkai.common.Constants
import com.vimalcvs.bottalkai.common.ProVersionTypeEnum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PurchaseHelper(val activity: Activity) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private lateinit var billingClient: BillingClient
    private lateinit var productDetails: ProductDetails
    private lateinit var purchase: Purchase

    private val _proWeeklyPrice = MutableStateFlow("")
    val proWeeklyPrice = _proWeeklyPrice.asStateFlow()

    private val _proMonthlyPrice = MutableStateFlow("")
    val proMonthlyPrice = _proMonthlyPrice.asStateFlow()

    private val _proYearlyPrice = MutableStateFlow("")
    val proYearlyPrice = _proYearlyPrice.asStateFlow()

    private val _buyEnabled = MutableStateFlow(false)
    val buyEnabled = _buyEnabled.asStateFlow()

    private val _statusText = MutableStateFlow("Initializing...")
    val statusText = _statusText.asStateFlow()

    private val _purchased = MutableStateFlow(false)
    val purchased = _purchased.asStateFlow()


    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            if (billingResult.responseCode ==
                BillingClient.BillingResponseCode.OK
                && purchases != null
            ) {
                for (purchase in purchases) {
                    completePurchase(purchase)
                }
            } else if (billingResult.responseCode ==
                BillingClient.BillingResponseCode.USER_CANCELED
            ) {
                _statusText.value = "Purchase Canceled"
            } else {
                _statusText.value = "Purchase Error"
            }
        }

    fun billingSetup() {
        billingClient = BillingClient.newBuilder(activity)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(
                billingResult: BillingResult
            ) {
                if (billingResult.responseCode ==
                    BillingClient.BillingResponseCode.OK
                ) {
                    _statusText.value = "Billing Client Connected"
                    queryProduct(Constants.PRODUCT_ID)
                } else {
                    _statusText.value = "Billing Client Connection Failure"
                }
            }

            override fun onBillingServiceDisconnected() {
                _statusText.value = "Billing Client Connection Lost"
            }
        })
    }

    fun queryProduct(productId: String) {
        val queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
            .setProductList(
                ImmutableList.of(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(productId)
                        .setProductType(
                            BillingClient.ProductType.SUBS
                        )
                        .build()
                )
            )
            .build()

        billingClient.queryProductDetailsAsync(
            queryProductDetailsParams
        ) { billingResult, productDetailsList ->
            if (productDetailsList.isNotEmpty()) {
                productDetails = productDetailsList[0]
//                _proWeeklyPrice.value =
//                    productDetails.subscriptionOfferDetails!![0].pricingPhases.pricingPhaseList[0].formattedPrice
//
//                _proMonthlyPrice.value =
//                    productDetails.subscriptionOfferDetails!![1].pricingPhases.pricingPhaseList[0].formattedPrice
//
//                _proYearlyPrice.value =
//                    productDetails.subscriptionOfferDetails!![2].pricingPhases.pricingPhaseList[0].formattedPrice


                val weeklyPricing =
                    productDetails.subscriptionOfferDetails?.find { it.basePlanId == Constants.WEEKLY_BASE_PLAN }?.pricingPhases?.pricingPhaseList?.getOrNull(
                        0
                    )?.formattedPrice
                val monthlyPricing =
                    productDetails.subscriptionOfferDetails?.find { it.basePlanId == Constants.MONTHLY_BASE_PLAN }?.pricingPhases?.pricingPhaseList?.getOrNull(
                        0
                    )?.formattedPrice
                val yearlyPricing =
                    productDetails.subscriptionOfferDetails?.find { it.basePlanId == Constants.YEARLY_BASE_PLAN }?.pricingPhases?.pricingPhaseList?.getOrNull(
                        0
                    )?.formattedPrice

                if (weeklyPricing != null) {
                    _proWeeklyPrice.value = weeklyPricing
                }
                if (monthlyPricing != null) {
                    _proMonthlyPrice.value = monthlyPricing
                }
                if (yearlyPricing != null) {
                    _proYearlyPrice.value = yearlyPricing
                }

            } else {
                _statusText.value = "No Matching Products Found"
                _buyEnabled.value = false
            }
        }
    }

    private fun completePurchase(item: Purchase) {
        purchase = item
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            _purchased.value = true
        }
    }

    fun makePurchase(proType: ProVersionTypeEnum) {
        val subOfferToken = when (proType) {
            ProVersionTypeEnum.WEEKLY -> productDetails.subscriptionOfferDetails!!.find { it.basePlanId == Constants.WEEKLY_BASE_PLAN }!!.offerToken
            ProVersionTypeEnum.MONTHLY -> productDetails.subscriptionOfferDetails!!.find { it.basePlanId == Constants.MONTHLY_BASE_PLAN }!!.offerToken
            ProVersionTypeEnum.YEARLY -> productDetails.subscriptionOfferDetails!!.find { it.basePlanId == Constants.YEARLY_BASE_PLAN }!!.offerToken
        }

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(
                ImmutableList.of(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(productDetails)
                        .setOfferToken(
                            subOfferToken
                        )
                        .build()
                )
            )
            .build()

        billingClient.launchBillingFlow(activity, billingFlowParams)
    }

//    fun consumePurchase() {
//        val consumeParams = ConsumeParams.newBuilder()
//            .setPurchaseToken(purchase.purchaseToken)
//            .build()
//
//        coroutineScope.launch {
//            val result = billingClient.consumePurchase(consumeParams)
//
//            if (result.billingResult.responseCode ==
//                BillingClient.BillingResponseCode.OK
//            ) {
//                _statusText.value = "Purchase Consumed"
//                _buyEnabled.value = true
////                _consumeEnabled.value = false
//            }
//        }
//    }

//    fun restorePurchase(): Boolean {
//        val queryPurchasesParams = QueryPurchasesParams.newBuilder()
//            .setProductType(BillingClient.ProductType.SUBS)
//            .build()
//
//        billingClient.queryPurchasesAsync(
//            queryPurchasesParams
//        ) { _, purchases ->
//            if (purchases.isNotEmpty()) {
//                purchase = purchases.first()
//                _purchased.value = true
//                _checkPurchased.value = true
//                return true
//            } else {
//                _purchased.value = false
//                _checkPurchased.value = false
//                return true
//            }
//        }
//    }

    fun restorePurchase(callback: (Boolean) -> Unit) {
        val queryPurchasesParams = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.SUBS)
            .build()

        var isPurchased = false

        billingClient.queryPurchasesAsync(queryPurchasesParams) { _, purchases ->
            if (purchases.isNotEmpty()) {
                purchase = purchases.first()
                _purchased.value = true
                isPurchased = true
            } else {
                _purchased.value = false
                isPurchased = false
            }
            callback(isPurchased)
        }
    }

    fun checkPurchase(callback: (Boolean) -> Unit) {
        val queryPurchasesParams = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.SUBS)
            .build()
        var isPurchased = false

        billingClient.queryPurchasesAsync(queryPurchasesParams) { _, purchases ->
            if (purchases.isNotEmpty()) {
                purchase = purchases.first()
                isPurchased = true
            } else {
                isPurchased = false
            }
            callback(isPurchased)
        }
    }


}