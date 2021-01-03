package taki.eddine.premier.league.pro.objects

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.IBinder
import androidx.lifecycle.LiveData

class InternetReceiverCheck(var context: Context) : LiveData<Boolean>(){


    override fun onActive() {
        super.onActive()
        IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).apply {
            context.registerReceiver(internetReceiver,this)
        }

    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(internetReceiver)
    }


     private val internetReceiver = object : BroadcastReceiver(){
         override fun peekService(myContext: Context?, service: Intent?): IBinder {
             return super.peekService(myContext, service)
         }

         override fun onReceive(context: Context?, intent: Intent?) {
             intent?.let {
                 val networkInfo = it.extras?.get(ConnectivityManager.EXTRA_NETWORK_INFO) as NetworkInfo
                 val isConnected = networkInfo.isConnected
                 if(isConnected){
                     when(networkInfo.type){
                         ConnectivityManager.TYPE_WIFI ->{
                            postValue(true)
                         }
                         ConnectivityManager.TYPE_MOBILE -> {
                            postValue(true)
                         }
                         ConnectivityManager.TYPE_ETHERNET -> {
                                postValue(true)
                         }
                     }
                 } else {
                    postValue(false)
                 }
             }
         }
     }
}