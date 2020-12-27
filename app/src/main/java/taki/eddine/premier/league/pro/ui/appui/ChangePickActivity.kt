package taki.eddine.premier.league.pro.ui.appui

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
import maes.tech.intentanim.CustomIntent
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
                                           Intent(this@ChangePickActivity,ActivitySettings::class.java).apply {
                                               if(Constants.checkConnectivity(this@ChangePickActivity)){
                                                   putExtra("isChecked",true)
                                                   putExtra("teamId",teamXX.idTeam)
                                                   putExtra("team",teamXX.strTeam)
                                                   setResult(NOTIFICATION_TEAM_PICK,intent)
                                                   finish()
                                               } else {
                                                   putExtra("isChecked",false)
                                                   putExtra("teamId",0)
                                                   putExtra("team","No Team Selected")
                                                   setResult(NOTIFICATION_TEAM_PICK,intent)
                                                   finish()
                                               }
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
      Intent(this@ChangePickActivity,ActivitySettings::class.java).apply {
          intent.putExtra("isChecked",false)
          intent.putExtra("teamId",0)
          intent.putExtra("team","No Team Selected")
          startActivity(this)
          CustomIntent.customType(this@ChangePickActivity, "fadein-to-fadeout")
          finish()
      }
    }

    companion object {
        private const val NOTIFICATION_TEAM_PICK = 3000

    }
}