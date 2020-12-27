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
    override fun onHandleIntent(intent: Intent?) {

        val draw = intent?.getIntExtra("draw",0)
        val goalsAgainst = intent?.getIntExtra("goalsAgainst",0)
        val goalsDifference = intent?.getIntExtra("goalsDifference",0)
        val goalsFor = intent?.getIntExtra("goalsFor",0)
        val loss = intent?.getIntExtra("loss",0)
        val name = intent?.getStringExtra("name")
        val played = intent?.getIntExtra("played",0)
        val teamid = intent?.getStringExtra("teamid")
        val total = intent?.getIntExtra("total",0)
        val win = intent?.getIntExtra("win",0)
        val teamXX = intent?.getParcelableExtra<TeamXX>("teamXX")

        val standingTable = Table(
           draw,goalsAgainst, goalsDifference!!,goalsFor,loss,
            name,played,teamid,total,win,teamXX
        )

        leagueViewModel.insertTableStandings(standingTable)

    }
}