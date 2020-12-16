package taki.eddine.premier.league.pro.appui

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PowerManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.datastore.preferences.core.clear
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.fxn.OnBubbleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import taki.eddine.premier.league.pro.adapters.PagerAdapter
import taki.eddine.premier.league.pro.alarmmanager.AlarmHelper
import taki.eddine.premier.league.pro.Constants
import taki.eddine.premier.league.pro.mvvm.LeagueViewModel
import taki.eddine.premier.league.pro.R
import taki.eddine.premier.league.pro.databinding.ActivityMainBinding
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val leagueViewModel: LeagueViewModel by viewModels()
    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getLanguagePreference()
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = ""
        binding.toolbarTitle.text = getString(R.string.leaguefixtures)

        setUi()
        getCurrentRound()
        gameNotification()
        AlarmHelper.setAlarm(this@MainActivity)
    }

    private fun setUi() {
        pagerAdapter = PagerAdapter(supportFragmentManager, this)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.offscreenPageLimit = 5
        binding.bubbleTabBar.setupBubbleTabBar(binding.viewPager)
        binding.bubbleTabBar.setSelected(0, false)
        binding.bubbleTabBar.addBubbleListener(object : OnBubbleClickListener {
            override fun onBubbleClick(id: Int) {
                when (id) {
                    R.id.fixtures -> {
                        binding.viewPager.currentItem = 0
                        binding.toolbarTitle.text = getString(R.string.leaguefixtures)
                    }
                    R.id.standings -> {
                        binding.viewPager.currentItem = 1
                        binding.toolbarTitle.text = getString(R.string.leaguestandings)
                    }
                    R.id.livescores -> {
                        binding.viewPager.currentItem = 2
                        binding.toolbarTitle.text = getString(R.string.leaguelivescores)
                    }
                    R.id.topscorers -> {
                        binding.viewPager.currentItem = 3
                        binding.toolbarTitle.text = getString(R.string.leaguetopscorers)
                    }
                    R.id.news -> {
                        binding.viewPager.currentItem = 4
                        binding.toolbarTitle.text = getString(R.string.leaguenews)
                    }
                }
            }
        })
    }
    private fun getLanguagePreference(){
        val languagesSharedPreference  = getSharedPreferences("languagesPrefs",Context.MODE_PRIVATE)
        val language = languagesSharedPreference.getString("language","en")
        val code = languagesSharedPreference.getString("code","en-EN")

        val locale = Locale(language!!,code!!)
        val configuration =  resources.configuration
        val displayMetrics = resources.displayMetrics
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration,displayMetrics)

    }
    private fun getCurrentRound()  { lifecycleScope.launch {
            val dataStore = createDataStore(name = "fixturesPrefs")
            if(Constants.checkConnectivity(this@MainActivity)){
                leagueViewModel.getCurrentRound(4328, "2020-2021")
                    .observe(this@MainActivity, androidx.lifecycle.Observer {
                        if (!it.events.isNullOrEmpty()) {
                            val deviceDate = Calendar.getInstance().time
                            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            val formattedDate = simpleDateFormat.format(deviceDate)
                            for (i in it.events.indices) {
                                val gameDate = it.events[i].dateEvent
                                if (gameDate.equals(formattedDate, true)) {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        dataStore.edit { preferences ->
                                            val currentRound = it.events[i].intRound?.toInt()
                                            preferences[Constants.ROUND_PREFERENCES] = currentRound!!
                                        }
                                    }
                                }
                                else if (!it.events[i].intHomeScore.isNullOrEmpty()) {
                                    var currentRound = it.events[i].intRound?.takeLast(i)?.toInt()
                                    CoroutineScope(Dispatchers.IO).launch {
                                        dataStore.edit {preferences ->
                                            preferences.clear()
                                            preferences[Constants.ROUND_PREFERENCES] = currentRound!!
                                        }
                                    }
                                }
                            }
                        } else {
                            Timber.d("No Internet Connection")
                        }
                    })
            }

        } }
    private fun gameNotification() {
        // TODO : Get Vibration Details
        val sharedPrefsVibration = getSharedPreferences("vibrationPrefs", Context.MODE_PRIVATE)
        val isVibrationEnabled = sharedPrefsVibration?.getBoolean("vibrate",false)

        // TODO : Get Team Details
        val sharedPreferences = getSharedPreferences("fcmPrefs", Context.MODE_PRIVATE)
        val isTeamPicked = sharedPreferences?.getBoolean("isTeamChecked", false)
        val teamID = sharedPreferences?.getInt("teamId", 0)

        // TODO : Handling data and notification
        if (isTeamPicked!!) {
            CoroutineScope(Dispatchers.Main).launch {
                leagueViewModel.getTeamGames(teamID!!)
                    .observe(this@MainActivity, androidx.lifecycle.Observer {
                        if(!it.results.isNullOrEmpty()){
                            it.results.map { data ->
                                // if (date == data.dateEvent && formattedTime == data.strTime?.substring(0,5) ) {
                                //setUpNotification(date,formattedTime, data.strEvent!!,isVibrationEnabled!!)
                                setUpNotification(this@MainActivity, setGameDate(), setGameTime(), data.strEvent!!,isVibrationEnabled!!)
                            }
                        }
                    })
            }
        }
    }
    private fun setUpNotification(context : FragmentActivity, date : String, time : String, strEvent : String, isVibrationEnabled : Boolean){
        //TODO : Wake up Device Before Triggering the notification
        var counter = 1
        val intent = Intent(context, MainActivity::class.java)
        val taskBuilder = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        (context.getSystemService(Context.POWER_SERVICE) as PowerManager).also { powerManager ->
            val cpuLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
                Constants.wakeLockTag)
            cpuLock.acquire(15000)

        }

        NotificationCompat.Builder(context,context.getString(R.string.channelId)).apply {
            setContentTitle(date.plus(" / ").plus(time))
            setContentText(strEvent)
            setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            setContentIntent(taskBuilder)
            setChannelId(context.getString(R.string.channelId))
            setDefaults(NotificationCompat.DEFAULT_SOUND)
            priority = NotificationCompat.PRIORITY_MAX
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            if(isVibrationEnabled){
                setVibrate(longArrayOf(1000L, 1000L, 1000L, 1000L))
            }
            NotificationManagerCompat.from(context).notify(counter++,build())
        }
    }
    private fun setGameDate() : String {
        // TODO : Comparing Date and Time
        val deviceDate = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return  simpleDateFormat.format(deviceDate)
    }
    private fun setGameTime() : String {
        // TODO : Get Local Time
        val calendar = Calendar.getInstance()
        calendar.get(Calendar.HOUR)
        calendar.get(Calendar.MINUTE)
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.time)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
    }
}