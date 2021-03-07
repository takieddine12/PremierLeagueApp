package taki.eddine.premier.league.pro.ui.appui

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PowerManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import com.fxn.OnBubbleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.view.*
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

        supportActionBar?.title = getString(R.string.leaguefixtures)

        setUi()
        getCurrentRound()
        AlarmHelper.setAlarm(this@MainActivity)
    }

    private fun setUi() {
        pagerAdapter = PagerAdapter(supportFragmentManager, this)
        binding.apply {
            viewPager.adapter = pagerAdapter
            viewPager.offscreenPageLimit = 5
            bubbleTabBar.setupBubbleTabBar(binding.viewPager)
            bubbleTabBar.setSelected(0, false)
            bubbleTabBar.addBubbleListener(object : OnBubbleClickListener {
                override fun onBubbleClick(id: Int) {
                    when (id) {
                        R.id.fixtures -> {
                            binding.viewPager.currentItem = 0
                            supportActionBar?.title = getString(R.string.leaguefixtures)
                        }
                        R.id.standings -> {
                            binding.viewPager.currentItem = 1
                            supportActionBar?.title = getString(R.string.leaguestandings)
                        }
                        R.id.livescores -> {
                            binding.viewPager.currentItem = 2
                            supportActionBar?.title = getString(R.string.leaguelivescores)
                        }
                        R.id.topscorers -> {
                            binding.viewPager.currentItem = 3
                            supportActionBar?.title = getString(R.string.leaguetopscorers)
                        }
                        R.id.news -> {
                            binding.viewPager.currentItem = 4
                            supportActionBar?.title = getString(R.string.leaguenews)
                        }
                    }
                }
            })
        }
    }

    private fun getLanguagePreference() {
        val languagesSharedPreference = getSharedPreferences("languagesPrefs", Context.MODE_PRIVATE)
        val language = languagesSharedPreference.getString("language", "en")
        val code = languagesSharedPreference.getString("code", "en-EN")

        val locale = Locale(language!!, code!!)
        val configuration = resources.configuration
        val displayMetrics = resources.displayMetrics
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, displayMetrics)

    }

    private fun getCurrentRound() {
        lifecycleScope.launch {
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
                                    val  currentRound = it.events[i].intRound
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

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
    }
}



