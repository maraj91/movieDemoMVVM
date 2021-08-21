package com.android.moviewdemo.utils.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build

class NetworkMonitorUtil(context: Context) {

    private var mContext = context

    private lateinit var networkCallback: NetworkCallback

    lateinit var result: ((isAvailable: Boolean, type: ConnectionType?) -> Unit)


    fun register() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // Use NetworkCallback for Android 9 and above
            val connectivityManager = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager.activeNetwork == null) {
                // UNAVAILABLE
                result(false, ConnectionType.NoInternet)
            }
            // Check when the connection changes
            networkCallback = object : NetworkCallback() {
                override fun onLost(network: Network) {
                    super.onLost(network)
                    // UNAVAILABLE
                    result(false, ConnectionType.NoInternet)
                }
                override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    when {
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            // WIFI
                            result(true, ConnectionType.Wifi)
                        }
                        else -> {
                            // CELLULAR
                            result(true, ConnectionType.Cellular)
                        }
                    }
                }
            }
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            // Use Intent Filter for Android 8 and below
            val intentFilter = IntentFilter()
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
            mContext.registerReceiver(networkChangeReceiver, intentFilter)
        }
    }

    fun unregister() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val connectivityManager =
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } else {
            mContext.unregisterReceiver(networkChangeReceiver)
        }
    }

    private val networkChangeReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager.activeNetwork ?: return result(false, ConnectionType.NoInternet)
                val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return result(false, ConnectionType.NoInternet)
                 when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> result(true, ConnectionType.Wifi)
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> result(true, ConnectionType.Cellular)
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> result(true, ConnectionType.Ethernet)
                    else -> result(false, ConnectionType.NoInternet)
                }
            } else {
                @Suppress("DEPRECATION")
                connectivityManager.run {
                    connectivityManager.activeNetworkInfo?.run {
                        when (type) {
                            ConnectivityManager.TYPE_WIFI -> result(true, ConnectionType.Wifi)
                            ConnectivityManager.TYPE_MOBILE -> result(true, ConnectionType.Cellular)
                            ConnectivityManager.TYPE_ETHERNET -> result(true, ConnectionType.Ethernet)
                            else -> result(false, ConnectionType.NoInternet)
                        }

                    }
                }
            }
        }
    }
}