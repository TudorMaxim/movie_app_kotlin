package com.ubb.movieapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.ubb.movieapp.connectivity.ConnectivityReceiver
import java.lang.Error
import java.lang.Exception

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {
    var mSnackBar: Snackbar? = null
    private var connectivityReceiver: ConnectivityReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityReceiver = ConnectivityReceiver()
        registerReceiver(connectivityReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    fun isConnected(): Boolean {
        return connectivityReceiver!!.isConnectedOrConnecting(this)
    }

    open fun showMessage(isConnected: Boolean) {
        if (!isConnected) {
            val messageToUser = "You are offline"
            mSnackBar = Snackbar.make(
                findViewById(R.id.root),
                messageToUser,
                Snackbar.LENGTH_INDEFINITE
            ) //Assume "rootLayout" as the root layout of every activity.
            mSnackBar?.show()
        } else {
            mSnackBar?.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onStop() {
        super.onStop()
        try {
            this.unregisterReceiver(connectivityReceiver)
        } catch (error: Exception) { }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            this.unregisterReceiver(connectivityReceiver)
        } catch (error: Exception) { }
    }
    /**
     * Callback will be called when there is change
     */
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showMessage(isConnected)
    }
}