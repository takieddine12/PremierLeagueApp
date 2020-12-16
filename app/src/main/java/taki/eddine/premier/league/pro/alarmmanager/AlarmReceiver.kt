package taki.eddine.premier.league.pro.alarmmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*
import taki.eddine.premier.league.pro.R


// TODO : Broadcast Receiver To Start Service

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class AlarmReceiver :  BroadcastReceiver () {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "sport.app.action") {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(
                    context?.getString(R.string.channelId),
                    context?.getString(R.string.channelString),
                    NotificationManager.IMPORTANCE_HIGH
                )

                val ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()
                notificationChannel.apply {
                    enableVibration(true)
                    setSound(ringtone, audioAttributes)
                    lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                }


                (context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {
                    createNotificationChannel(notificationChannel)
                }
            }
        }
    }
}