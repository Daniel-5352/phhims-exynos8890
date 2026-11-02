//SPDX-License-Identifier: GPL-2.0
package me.phh.sip

import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.TelephonyNetworkSpecifier
import android.telephony.SubscriptionManager

object ImsNetworkRequestBuilder {
    fun buildForSubscription(subId: Int): NetworkRequest {
        val builder = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addCapability(NetworkCapabilities.NET_CAPABILITY_IMS)

        if (SubscriptionManager.isValidSubscriptionId(subId)) {
            try {
                builder.setNetworkSpecifier(
                    TelephonyNetworkSpecifier.Builder()
                        .setSubscriptionId(subId)
                        .build()
                )
            } catch (t: Throwable) {
                // If TelephonyNetworkSpecifier fails on older/custom framework, proceed without specifier
            }
        }

        return builder.build()
    }
}
