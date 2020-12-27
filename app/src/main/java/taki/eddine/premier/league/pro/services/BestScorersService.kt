package taki.eddine.premier.league.pro.services

import android.app.IntentService
import android.content.Intent
import android.content.IntentSender
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import taki.eddine.premier.league.pro.mvvm.LeagueRepository
import taki.eddine.premier.league.pro.mvvm.LeagueViewModel
import taki.eddine.premier.league.pro.topscorersui.ResultMainModel
import taki.eddine.premier.league.pro.topscorersui.ResultX
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class BestScorersService : IntentService("Debugging") {

    @Inject
    lateinit var repository: LeagueRepository
    lateinit var leagueViewModel: LeagueViewModel

    override fun onCreate() {
        super.onCreate()
        leagueViewModel = LeagueViewModel(repository)
    }
    override fun onHandleIntent(intent: Intent?) {

        val goals = intent?.getStringExtra("goals")
        val playerKey = intent?.getStringExtra("playerKey")
        val playerName = intent?.getStringExtra("playerName")
        val playerPlace = intent?.getStringExtra("playerPlace")
        val teamKey = intent?.getStringExtra("teamKey")
        val teamName = intent?.getStringExtra("teamName")
        val resultX = intent?.getParcelableExtra<ResultX>("resultX")

        val resultModel = ResultMainModel(goals,playerKey,playerName,playerPlace,teamKey,teamName,resultX!!)
        leagueViewModel.insertTopScorers(resultModel)
    }
}