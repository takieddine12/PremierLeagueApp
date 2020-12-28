package taki.eddine.premier.league.pro.services

import android.app.IntentService
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import taki.eddine.premier.league.pro.models.BottomStandingModel
import taki.eddine.premier.league.pro.mvvm.LeagueRepository
import taki.eddine.premier.league.pro.mvvm.LeagueViewModel
import taki.eddine.premier.league.pro.mvvm.LeagueViewModel_AssistedFactory
import javax.inject.Inject
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class BottomSheetStandingsService  : IntentService("Debugging"){

    lateinit var leagueViewModel: LeagueViewModel
    @Inject
    lateinit var repository: LeagueRepository

    override fun onCreate() {
        super.onCreate()
        leagueViewModel  = LeagueViewModel(repository)
    }

    override fun onHandleIntent(intent: Intent?) {
        val bottomStandingModel = intent?.getParcelableExtra<BottomStandingModel>("bottomStandingModel")
        leagueViewModel.insertBottomStandings(bottomStandingModel!!)
    }
}