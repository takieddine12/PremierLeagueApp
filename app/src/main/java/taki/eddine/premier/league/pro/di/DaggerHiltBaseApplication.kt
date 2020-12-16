package taki.eddine.premier.league.pro.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import dagger.hilt.android.HiltAndroidApp
import taki.eddine.premier.league.pro.R
import timber.log.Timber

@HiltAndroidApp
class DaggerHiltBaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        createNotification()

    }

    private fun createNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel  = NotificationChannel(
                getString(R.string.channelId),getString(R.string.channelString),
                NotificationManager.IMPORTANCE_HIGH)

            val ringtone  = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            notificationChannel.enableVibration(true)
            notificationChannel.setSound(ringtone,audioAttributes)
            notificationChannel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC

            val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}