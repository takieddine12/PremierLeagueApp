package taki.eddine.premier.league.pro.mvvm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import taki.eddine.premier.league.pro.CurrentRoundModel
import taki.eddine.premier.league.pro.models.LiveScoreModelAway
import taki.eddine.premier.league.pro.webauthentification.NetworkStatesHandler
import taki.eddine.premier.league.pro.livescoresdata.EventTwo
import taki.eddine.premier.league.pro.livescoresdata.v2livescores
import taki.eddine.premier.league.pro.models.*
import taki.eddine.premier.league.pro.standingSavedDataUi.StandingClubModel
import taki.eddine.premier.league.pro.topscorersui.ResultMainModel
import taki.eddine.premier.league.pro.topscorersui.SportApiLogoModel
import taki.eddine.premier.league.pro.topscorersui.TopScorersModl
import taki.eddine.premier.league.pro.topscorersui.TopScorersPlayersDetails

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class LeagueViewModel @ViewModelInject constructor(private var repository: LeagueRepository): ViewModel() {


    suspend fun getPlayerDetails(playerName : String , ApiKey : String) : LiveData<NetworkStatesHandler<TopScorersPlayersDetails>> {
        return repository.getPlayerDetails(playerName,ApiKey)
            .buffer()
            .asLiveData()
    }
    //TODO: AllApiFootball
    suspend fun getTopScorers(leagueId: Int,ApiKey : String ) : LiveData<NetworkStatesHandler<TopScorersModl>>{
        return repository.getTopScorers(leagueId,ApiKey)
            .buffer()
            .asLiveData()
    }
    fun getSportApiTeamLogo(teamId : Int, ApiKey : String) : LiveData<NetworkStatesHandler<SportApiLogoModel>>{
        return repository.getSportApiLogoTeam(teamId,ApiKey)
            .buffer()
            .asLiveData()
    }

    //---------------------------------------
    suspend fun getCurrentRound(id : Int,season : String) : LiveData<CurrentRoundModel> {
        return repository.getCurrentRound(id,season).buffer().asLiveData()
    }
    suspend fun getNextLeagueFixtures(id: Int, round: Int, season: String): LiveData<NetworkStatesHandler<FixturesModel>> {
        return repository.getLeagueFixtures(id,round,season)
            .buffer()
            .asLiveData()
    }
    suspend fun getStandings(id : Int, season : String) : LiveData<NetworkStatesHandler<StandingsModel>> {
        return repository.getStandings(id, season)
            .buffer()
            .asLiveData()

    }
    suspend fun getAllTeams(id : Int) : LiveData<NetworkStatesHandler<NotificationModel>> {
        return repository.getAllTeams(id)
            .buffer()
            .asLiveData()

    }
    fun getLiveScoreHomeLogoTest(id : Int) : LiveData<LiveScoreModelAway>{
        return liveData(Dispatchers.IO){
            val response  = repository.getLiveScoreHomeLogoTest(id)
            emit(response)

        }

    }
    suspend fun getLiveScores() : LiveData<NetworkStatesHandler<v2livescores>> {
        return repository.getLiveScores()
            .buffer()
            .asLiveData()
    }
    fun getLiveScoreHomeLogo(id : Int) : LiveData<LiveScoreModel>{
        return liveData(Dispatchers.IO){
            val response  = repository.getLiveScoreHomeLogo(id)
            emit(response)
        }
    }
    suspend fun getStandingsTeamsDetails(id : Int) : LiveData<NetworkStatesHandler<StandingsTeamsDetailsModel>>{
        return repository.getStandingsTeamsDetails(id)
            .buffer()
            .asLiveData()

    }
    suspend fun getTeamGames(id : Int) : LiveData<TeamMatchesModel>{
        return repository.getTeamGames(id)
            .buffer()
            .asLiveData()
    }

    // Observing News DB
    fun observeNews() : LiveData<MutableList<NewsModel>>{
        return repository.getSavedNews()
    }
    fun insertNews(newsModel: NewsModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNews(newsModel)
        }
    }

    //Observing Standings DB
    fun observeStandings()  : LiveData<MutableList<Table>> {
        return repository.getStandingsFromDB()
    }
    fun insertTableStandings(table: Table) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertStandingsIntoDB(table)
        }
    }

    //Observing Fixtures DB
    fun observeFixtures() : LiveData<MutableList<Event>> {
        return repository.getSavedFixtures()
    }
    fun insertFixtures(event: Event){
        viewModelScope.launch(Dispatchers.IO)  {
            repository.insertFixtures(event)
        }
    }

    ///Observing LiveScors DB
    fun observeLiveScores() : LiveData<MutableList<EventTwo>> {
        return repository.getSavedLiveScores()
    }
    fun insertLiveScores(match : MutableList<EventTwo>) {
        viewModelScope.launch(Dispatchers.IO)  {
            repository.insertLiveScores(match)
        }
    }


    fun observeTopScorers() : LiveData<MutableList<ResultMainModel>>{
        return repository.getSavedTopScorers()
    }

    fun insertTopScorers(result: ResultMainModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTopScorers(result)
        }
    }



    fun getStandingsBottom() : LiveData<MutableList<BottomStandingModel>> {
        return repository.getSavedStandingsBottom()

    }

    fun insertBottomStandings(model : BottomStandingModel)  {
        viewModelScope.launch {
            repository.insertStandingsBottom(model)
        }
    }

    //-----

    suspend fun getClubsDetails(league : String ) : LiveData<NetworkStatesHandler<StandingClubModel>> {
        return repository.getClubsDetails(league)
            .buffer()
            .asLiveData()
    }

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