package com.ubb.movieapp

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.ubb.movieapp.connectivity.ConnectivityReceiver

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {
    var mSnackBar: Snackbar? = null
    open var connected: Boolean = true
    private var connectivityReceiver: ConnectivityReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityReceiver = ConnectivityReceiver()
        registerReceiver(connectivityReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
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
            connected = false
        } else {
            mSnackBar?.dismiss()
            connected = true
        }
        Log.d("CONNECTED: ", connected.toString())
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onStop() {
        super.onStop()
        this.unregisterReceiver(connectivityReceiver)
    }
    /**
     * Callback will be called when there is change
     */
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showMessage(isConnected)
    }
}