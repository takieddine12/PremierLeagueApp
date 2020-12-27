package taki.eddine.premier.league.pro.alarmmanager

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import taki.eddine.premier.league.pro.Constants
import taki.eddine.premier.league.pro.R
import taki.eddine.premier.league.pro.mvvm.LeagueViewModel
import taki.eddine.premier.league.pro.ui.appui.MainActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint()
 class AlarmReceiver : BroadcastReceiver() {

  //  lateinit var leagueViewModel : LeagueViewModel

    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context,"Class Triggered",Toast.LENGTH_SHORT).show()
        if (intent?.action == "sport.app.action") {
             //  gameNotification(context,leagueViewModel)
        }
    }

    private fun gameNotification(context: Context?,leagueViewModel: LeagueViewModel) {
        // TODO : Get Vibration Details
        val sharedPrefsVibration = context?.getSharedPreferences("vibrationPrefs", Context.MODE_PRIVATE)
        val isVibrationEnabled = sharedPrefsVibration?.getBoolean("vibrate", false)

        // TODO : Get Team Details
        val sharedPreferences = context?.getSharedPreferences("fcmPrefs", Context.MODE_PRIVATE)
        val isTeamPicked = sharedPreferences?.getBoolean("isTeamChecked", false)
        val teamID = sharedPreferences?.getInt("teamId", 0)

        // TODO : Handling data and notification
        if (isTeamPicked!!) {
            CoroutineScope(Dispatchers.Main).launch {
                leagueViewModel.getTeamGames(teamID!!)
                    .observeForever {
                        if (!it.results.isNullOrEmpty()) {
                            it.results.map { data ->
                                if (gameDate() == data.dateEvent && gameTime() == data.strTime?.substring(0, 5)) {

                                    //TODO : Wake up Device Before Triggering the notification
                                    var counter = 1
                                    val intent = Intent(context, MainActivity::class.java)
                                    val taskBuilder = TaskStackBuilder.create(context).run {
                                        addNextIntentWithParentStack(intent)
                                        getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)
                                    }
                                    (context.getSystemService(Context.POWER_SERVICE) as PowerManager).also { powerManager ->
                                        val cpuLock = powerManager.newWakeLock(
                                            PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
                                            Constants.wakeLockTag
                                        )
                                        cpuLock.acquire(15000)

                                    }

                                    NotificationCompat.Builder(context, context.getString(R.string.channelId)).apply {
                                        setContentTitle(data.dateEvent.plus(" / ").plus(data.strTime))
                                        setContentText(data.strEvent)
                                        setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                                        setContentIntent(taskBuilder)
                                        setChannelId(context.getString(R.string.channelId))
                                        setDefaults(NotificationCompat.DEFAULT_SOUND)
                                        priority = NotificationCompat.PRIORITY_MAX
                                        setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                        if (isVibrationEnabled!!) {
                                            setVibrate(longArrayOf(1000L, 1000L, 1000L, 1000L))
                                        }
                                        NotificationManagerCompat.from(context)
                                            .notify(counter++, build())
                                    }
                                }
                            }
                        }
                    }
            }
        }
    }
    private fun gameDate() : String {
        // TODO : Comparing Date and Time
        val deviceDate = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return  simpleDateFormat.format(deviceDate)
    }
    private fun gameTime() : String {
        // TODO : Get Local Time
        val calendar = Calendar.getInstance()
        calendar.get(Calendar.HOUR)
        calendar.get(Calendar.MINUTE)
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.time)
    }
}