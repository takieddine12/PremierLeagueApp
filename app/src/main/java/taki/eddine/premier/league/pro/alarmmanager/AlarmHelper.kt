package taki.eddine.premier.league.pro.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import taki.eddine.premier.league.pro.Constants.ALARM_REQUEST_CODE

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
object AlarmHelper {

    fun setAlarm(context : Context) {
        (context.getSystemService(Context.ALARM_SERVICE)as AlarmManager).apply {
            Intent(context, AlarmReceiver::class.java).apply {
                action = "sport.app.action"
                PendingIntent.getBroadcast(context, ALARM_REQUEST_CODE, this, PendingIntent.FLAG_UPDATE_CURRENT).apply {
                    when {
                        Build.VERSION.SDK_INT >= 23 -> {
                            setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),this)
                        }
                        Build.VERSION.SDK_INT in 21..22 -> {
                            setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),this)
                        }
                    }
                }
            }
        }
    }
}