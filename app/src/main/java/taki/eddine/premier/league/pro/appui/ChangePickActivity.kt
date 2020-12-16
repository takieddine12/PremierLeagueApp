package taki.eddine.premier.league.pro.appui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import taki.eddine.premier.league.pro.adapters.NotificationAdapter
import taki.eddine.premier.league.pro.Constants
import taki.eddine.premier.league.pro.mvvm.LeagueViewModel
import taki.eddine.premier.league.pro.webauthentification.NetworkStatesHandler
import taki.eddine.premier.league.pro.databinding.ActivityTeamChangerBinding
import taki.eddine.premier.league.pro.models.TeamX
import taki.eddine.premier.league.pro.uilisteners.NotificationListener

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class ChangePickActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTeamChangerBinding
    private val NOTIFICATION_TEAM_PICK = 3000
    private lateinit var notificationAdapter : NotificationAdapter

    private val leagueViewModel: LeagueViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamChangerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.teamsrecycler.layoutManager = LinearLayoutManager(this)
        binding.teamsrecycler.setHasFixedSize(true)

        lifecycleScope.launch {
            leagueViewModel.getAllTeams(4328).observe(this@ChangePickActivity, Observer {
                when (it.status) {
                    NetworkStatesHandler.Status.LOADING -> {
                          binding.teamPickerProgress.visibility = View.VISIBLE
                    }
                    NetworkStatesHandler.Status.SUCCESS -> {
                        notificationAdapter = NotificationAdapter(it.data!!.teams!!, this@ChangePickActivity,object : NotificationListener{
                            override fun getNotificationDetails(teamXX: TeamX) {
                                           val intent = Intent(this@ChangePickActivity,ActivitySettings::class.java)
                                           if(Constants.checkConnectivity(this@ChangePickActivity)){
                                               intent.putExtra("isChecked",true)
                                               intent.putExtra("teamId",teamXX.idTeam)
                                               intent.putExtra("team",teamXX.strTeam)
                                               setResult(NOTIFICATION_TEAM_PICK,intent)
                                               finish()
                                           } else {
                                               intent.putExtra("isChecked",false)
                                               intent.putExtra("teamId",0)
                                               intent.putExtra("team","No Team Selected")
                                               setResult(NOTIFICATION_TEAM_PICK,intent)
                                               finish()
                                           }
                                       }
                                    })

                        binding.teamsrecycler.adapter = notificationAdapter
                        binding.teamPickerProgress.visibility = View.INVISIBLE
                    }
                    NetworkStatesHandler.Status.ERROR -> {
                        binding.teamPickerProgress.visibility = View.INVISIBLE
                    }
                }
            })
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@ChangePickActivity,ActivitySettings::class.java)
        intent.putExtra("isChecked",false)
        intent.putExtra("teamId",0)
        intent.putExtra("team","No Team Selected")
        finish()
    }
}