package taki.eddine.premier.league.pro.appui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import taki.eddine.premier.league.pro.adapters.NotificationAdapter
import taki.eddine.premier.league.pro.mvvm.LeagueViewModel
import taki.eddine.premier.league.pro.webauthentification.NetworkStatesHandler
import taki.eddine.premier.league.pro.databinding.ActivityTeamPickerBinding
import taki.eddine.premier.league.pro.models.TeamX
import taki.eddine.premier.league.pro.objects.InternetReceiverCheck
import taki.eddine.premier.league.pro.uilisteners.NotificationListener

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class TeamPickerActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTeamPickerBinding
    private lateinit var notificationAdapter : NotificationAdapter
    private val leagueViewModel: LeagueViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.teamsRecycler.layoutManager = LinearLayoutManager(this)
        binding.teamsRecycler.setHasFixedSize(true)
        binding.skipBtn.setOnClickListener {
           getSharedPreferences("pickTeam",Context.MODE_PRIVATE).apply {
               edit().apply {
                   putBoolean("isTeamChecked",false)
                   apply()
               }
           }

            startActivity(Intent(this@TeamPickerActivity,MainActivity::class.java))
            finish()
        }

        getSharedPreferences("pickedPrefs",Context.MODE_PRIVATE).apply {
            if(!getBoolean("firstStart",true)) {
                Intent(this@TeamPickerActivity, MainActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }

        InternetReceiverCheck(this@TeamPickerActivity).observe(this@TeamPickerActivity,
            Observer { isConnected ->
            lifecycleScope.launch {
                        if(isConnected){
                            leagueViewModel.getAllTeams(4328).observe(this@TeamPickerActivity, Observer {
                                if (!it.data?.teams.isNullOrEmpty()) {
                                    when (it.status) {
                                        NetworkStatesHandler.Status.LOADING -> {

                                        }
                                        NetworkStatesHandler.Status.SUCCESS -> {
                                            notificationAdapter = NotificationAdapter(it.data!!.teams!!,
                                                this@TeamPickerActivity,
                                                object : NotificationListener {
                                                    override fun getNotificationDetails(teamXX: TeamX) {
                                                        getSharedPreferences("pickTeam", Context.MODE_PRIVATE).apply {
                                                            edit().apply {
                                                                putBoolean("isTeamChecked", true)
                                                                putInt("teamId", teamXX.idTeam!!)
                                                                putString("team", teamXX.strTeam)
                                                                apply()
                                                            }
                                                        }
                                                         Intent(this@TeamPickerActivity, MainActivity::class.java).also {
                                                             startActivity(it)
                                                             getSharedPreferences("pickedPrefs", Context.MODE_PRIVATE).apply {
                                                                 edit().apply {
                                                                     putBoolean("firstStart", false)
                                                                     apply()
                                                                 }
                                                             }
                                                         }
                                                    }
                                                })
                                            binding.teamsRecycler.adapter = notificationAdapter
                                            binding.teamPickerProgress.visibility = View.INVISIBLE
                                            binding.skipBtn.isEnabled = true
                                        }
                                        NetworkStatesHandler.Status.ERROR -> {
                                            binding.teamPickerProgress.visibility = View.INVISIBLE
                                            binding.skipBtn.isEnabled = false
                                        }
                                    }
                                }
                                else {
                                    binding.skipBtn.isEnabled = false
                                    Toast.makeText(this@TeamPickerActivity, "No Internet Connection", Toast.LENGTH_SHORT).show()
                                }
                            })
                        } else {
                            notificationAdapter = NotificationAdapter(mutableListOf(), this@TeamPickerActivity, object : NotificationListener { override fun getNotificationDetails(teamXX: TeamX) {
                            }})
                            binding.teamsRecycler.adapter = notificationAdapter
                            binding.skipBtn.isEnabled = false
                            Toast.makeText(this@TeamPickerActivity, "No Internet Connection", Toast.LENGTH_SHORT).show()
                        }
                    }
            })
    }


    ///TODO : need to fetch the current round of games


}
