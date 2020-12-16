package taki.eddine.premier.league.pro

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.datastore.preferences.core.preferencesKey
import androidx.lifecycle.LiveData

object Constants {

     const val ALARM_REQUEST_CODE = 1001
     var dialogCounter = 0
     const val NOTIFICATION_TEAM_PICK = 3000
     val ROUND_PREFERENCES = preferencesKey<Int>("roundPrefs")
     const val wakeLockTag = "alarm:tag"

     //4013070
     //8673533

     fun checkConnectivity(context: Context) : Boolean{
          val connectivityManager =  context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
          val networkInfo = connectivityManager.activeNetworkInfo
          return (networkInfo != null && networkInfo.isConnected)
     }

}
fun Context.showToast(context: Context, msg : String) = Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
