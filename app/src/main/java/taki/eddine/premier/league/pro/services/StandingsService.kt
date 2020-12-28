package taki.eddine.premier.league.pro.services

import android.app.IntentService
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import taki.eddine.premier.league.pro.models.Table
import taki.eddine.premier.league.pro.models.TeamXX
import taki.eddine.premier.league.pro.models.TeamXXX
import taki.eddine.premier.league.pro.mvvm.LeagueRepository
import taki.eddine.premier.league.pro.mvvm.LeagueViewModel
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class StandingsService : IntentService("Debugging") {
    @Inject
    lateinit var repository: LeagueRepository

    lateinit var leagueViewModel: LeagueViewModel
    override fun onCreate() {
        super.onCreate()
        leagueViewModel  = LeagueViewModel(repository)
    }
    override fun onHandleIntent(intent: Intent?) {

        val standingTable = intent?.getParcelableExtra<Table>("standingTable")
        leagueViewModel.insertTableStandings(standingTable!!)

    }
}