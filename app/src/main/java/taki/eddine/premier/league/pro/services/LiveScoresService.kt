package taki.eddine.premier.league.pro.services

import android.app.IntentService
import android.content.Intent
import android.content.res.Configuration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import taki.eddine.premier.league.pro.livescoresdata.EventTwo
import taki.eddine.premier.league.pro.mvvm.LeagueRepository
import taki.eddine.premier.league.pro.mvvm.LeagueViewModel
import taki.eddine.premier.league.pro.objects.UtilsClass
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class LiveScoresService : IntentService("Debugging") {

    @Inject
    lateinit var repository: LeagueRepository
    lateinit var leagueViewModel: LeagueViewModel


    override fun onCreate() {
        super.onCreate()
        leagueViewModel = LeagueViewModel(repository)
    }
    override fun onHandleIntent(intent: Intent?) {

//        val strHomeTeam = intent?.getStringExtra("strHomeTeam")
//        val strAwayTeam = intent?.getStringExtra("strAwayTeam")
//        val strHomeTeamBadge = intent?.getStringExtra("strHomeTeamBadge")
//        val strAwayTeamBadge = intent?.getStringExtra("strAwayTeamBadge")
//        val intHomeScore = intent?.getIntExtra("intHomeScore",1)
//        val intAwayScore = intent?.getIntExtra("intAwayScore",1)
//        val strProgress = intent?.getStringExtra("strProgress")
//        val strEventTime = intent?.getStringExtra("strEventTime")
//        val dateEvent = intent?.getStringExtra("dateEvent")
//        val updated = intent?.getStringExtra("updated")
//        val strLeague = intent?.getStringExtra("strLeague")
//
//        val liveScorersModel = EventTwo(
//            strHomeTeam,strAwayTeam,strHomeTeamBadge,
//            strAwayTeamBadge,intHomeScore,intAwayScore,
//            strProgress,strEventTime,dateEvent,updated,strLeague
//        )
        val liveScorersModel = intent?.getParcelableExtra<EventTwo>("liveScorersModel")
        liveScorersModel?.let {
            leagueViewModel.insertLiveScores(mutableListOf(it))
        }

    }
}