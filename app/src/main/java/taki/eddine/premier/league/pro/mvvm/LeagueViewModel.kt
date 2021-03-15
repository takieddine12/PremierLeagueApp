package taki.eddine.premier.league.pro.mvvm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import taki.eddine.premier.league.pro.CurrentRoundModel
import taki.eddine.premier.league.pro.livescoresdata.EventTwo
import taki.eddine.premier.league.pro.models.*
import taki.eddine.premier.league.pro.topscorersui.ResultMainModel


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class LeagueViewModel
@ViewModelInject constructor(
    private var repository: LeagueRepository
) : ViewModel() {


    //-----------------------
    //TODO: AllApiFootball
    suspend fun getTopScorers(leagueId: Int, ApiKey: String) =
        repository.getTopScorers(leagueId, ApiKey)
            .buffer()
            .asLiveData()

    fun getSportApiTeamLogo(teamId: Int, ApiKey: String) =
        repository.getSportApiLogoTeam(teamId, ApiKey)
            .buffer()
            .asLiveData()


    //------------------------
    suspend fun getPlayerDetails(playerName: String, ApiKey: String) = repository.getPlayerDetails(
        playerName,
        ApiKey
    )
        .buffer()
        .asLiveData()


    //---------------------------------------
    suspend fun getCurrentRound(id: Int, season: String): LiveData<CurrentRoundModel> {
        return repository.getCurrentRound(id, season).asLiveData()
    }

    suspend fun getNextLeagueFixtures(id: Int, round: Int, season: String) =
        repository.getLeagueFixtures(id, round, season).asLiveData()

    suspend fun getStandings(id: Int, season: String) = repository.getStandings(id, season).asLiveData()

    suspend fun getAllTeams(id: Int) = repository.getAllTeams(id).asLiveData()

    fun getLiveScoreHomeLogoTest(id: Int) = liveData(Dispatchers.IO) {
        val response = repository.getLiveScoreHomeLogoTest(id)
        emit(response)

    }

    suspend fun getLiveScores() = repository.getLiveScores().asLiveData()

    fun getLiveScoreHomeLogo(id: Int) = liveData(Dispatchers.IO) {
        val response = repository.getLiveScoreHomeLogo(id)
        emit(response)
    }

    suspend fun getStandingsTeamsDetails(id: Int) = repository.getStandingsTeamsDetails(id)
        .asLiveData()

    suspend fun getTeamGames(id: Int) = repository.getTeamGames(id)
        .asLiveData()

    // Observing News DB
    fun observeNews() = repository.getSavedNews()
    fun insertNews(newsModel: NewsModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNews(newsModel)
        }
    }

    //Observing Standings DB
    fun observeStandings() = repository.getStandingsFromDB()
    fun insertTableStandings(table: Table) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertStandingsIntoDB(table)
        }
    }

    //Observing Fixtures DB
    fun observeFixtures() = repository.getSavedFixtures()
    fun insertFixtures(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFixtures(event)
        }
    }

    ///Observing LiveScore DB
    fun observeLiveScores() = repository.getSavedLiveScores()
    fun insertLiveScores(match: MutableList<EventTwo>?) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertLiveScores(match!!)
        }
    }


    fun observeTopScorers() = repository.getSavedTopScorers()

    fun insertTopScorers(result: ResultMainModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTopScorers(result)
        }
    }


    fun getStandingsBottom() = repository.getSavedStandingsBottom()

    fun insertBottomStandings(model: BottomStandingModel) {
        viewModelScope.launch {
            repository.insertStandingsBottom(model)
        }
    }

//-----

    suspend fun getClubsDetails(league: String) = repository.getClubsDetails(league)
        .buffer()
        .asLiveData()

    fun deleteDuplicateMatches() {
        viewModelScope.launch {
            repository.deleteDuplicateMatches()
        }
    }

    fun deleteDuplicateLiveScores() {
        viewModelScope.launch {
            repository.deleteDuplicateLiveScores()
        }
    }

    fun deleteDuplicateNews() {
        viewModelScope.launch {
            repository.deleteDuplicateNews()
        }
    }

    fun deleteDuplicateBottomSheetStandings() {
        viewModelScope.launch {
            repository.deleteDuplicateBottomSheetStandings()
        }
    }

    fun deleteDuplicateBestScorers() {
        viewModelScope.launch {
            repository.deleteDuplicateBestScorers()
        }
    }

    fun deleteDuplicateStandings() {
        viewModelScope.launch {
            repository.deleteDuplicateStandings()
        }
    }
}