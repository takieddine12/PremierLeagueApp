package taki.eddine.premier.league.pro.mvvm

import kotlinx.coroutines.flow.*
import taki.eddine.premier.league.pro.CurrentRoundModel
import taki.eddine.premier.league.pro.di.*
import taki.eddine.premier.league.pro.webauthentification.ApiResponse
import taki.eddine.premier.league.pro.webauthentification.NetworkStatesHandler
import taki.eddine.premier.league.pro.webauthentification.ResponseHandler.Companion.handleException
import taki.eddine.premier.league.pro.webauthentification.ResponseHandler.Companion.handleSuccess
import taki.eddine.premier.league.pro.room.*
import taki.eddine.premier.league.pro.livescoresdata.EventTwo
import taki.eddine.premier.league.pro.livescoresdata.v2livescores
import taki.eddine.premier.league.pro.models.*
import taki.eddine.premier.league.pro.standingSavedDataUi.StandingClubModel
import taki.eddine.premier.league.pro.topscorersui.ResultMainModel
import taki.eddine.premier.league.pro.topscorersui.SportApiLogoModel
import taki.eddine.premier.league.pro.topscorersui.TopScorersModl
import taki.eddine.premier.league.pro.topscorersui.TopScorersPlayersDetails
import java.lang.Exception
import javax.inject.Inject

class LeagueRepository @Inject constructor(private var apiResponse: ApiResponse, @NewsDaoImpl var newsDao: NewsDao,
                                           @StandingsDaoImpl var standingsDao: StandingsDao, @FixturesDaoImpl var fixturesDao: FixturesDao, @TopScorersDaoImpl var topScorersDao: TopScorersDao,
                                           @LiveScoresImpl var liveScoresDao: LiveScoresDao, @BottomStandingsDaoImpl var  standingsBottomDao: StandingsBottomDao,
                                           @TopScorersRetrofitCall var apiResponse2 : ApiResponse){



    suspend fun getClubsDetails(league : String ) : Flow<NetworkStatesHandler<StandingClubModel>> {
        return flow {
            try {
                emit(handleSuccess(apiResponse.getClubDetails(league)))
            }catch (e  : Exception) {
                emit(handleException(e))
            }
        }
    }
    //TODO : AllApiSPORT Api Call
    suspend fun getTopScorers(leagueId : Int,Apikey : String) : Flow<NetworkStatesHandler<TopScorersModl>> {
        return flow {
            try {
                emit(handleSuccess(apiResponse2.getTopScorers(leagueId,Apikey)))
            }catch (ex : Exception){
                emit(handleException(ex))
            }
        }
    }

     fun getSportApiLogoTeam(teamid : Int, Apikey  : String ) : Flow<NetworkStatesHandler<SportApiLogoModel>>{
        return flow {
            try {
                emit(handleSuccess(apiResponse2.getSportApiTeamLogo(teamid,Apikey)))
            }catch (e : Exception){
                emit(handleException(e))
            }
        }
    }

    suspend fun getPlayerDetails(playerName:  String , Apikey: String) : Flow<NetworkStatesHandler<TopScorersPlayersDetails>>{
        return flow {
            try {
                emit(handleSuccess(apiResponse2.getPlayerDetails(playerName,Apikey)))
            }catch (e : Exception){
                handleException<TopScorersPlayersDetails>(e)
            }
        }
    }


    //-----------------------------------------------
    suspend fun getCurrentRound(id : Int,season : String) : Flow<CurrentRoundModel> {
        return flow {
            emit(apiResponse.getCurrentRound(id,season))
        }
    }
    suspend fun getLeagueFixtures(id : Int,round : Int,season : String) : Flow<NetworkStatesHandler<FixturesModel>> {
        return flow {
            try {
                emit(handleSuccess(apiResponse.getNextFixtures(id,round,season)))
                emit(NetworkStatesHandler.loading(null))
            }catch (e : Exception){
                emit(handleException(e))
            }
        }
    }



    suspend fun getStandings(id : Int, seanson : String) : Flow<NetworkStatesHandler<StandingsModel>> {
        return flow {
            try {
                emit(handleSuccess(apiResponse.getStandings(id,seanson)))
            }catch (e : Exception){
                emit(handleException(e))
            }
        }
    }

    suspend fun getAllTeams(id : Int)  : Flow<NetworkStatesHandler<NotificationModel>>{ // this one
        return flow {
            try {
               emit(handleSuccess(apiResponse.getAllTeams(id)))
            }catch (e : Exception){
                emit(handleException(e))
            }
        }
    }


    //----------

    suspend fun getLiveScores()  : Flow<NetworkStatesHandler<v2livescores>> {
        return flow {
            try {
                emit(handleSuccess(apiResponse.getLiveScores()))
            }catch (e : Exception){
                emit(handleException(e))
            }
        }
    }



    suspend fun getLiveScoreHomeLogo(id : Int) = apiResponse.getLiveScoreHomeTeamLogo(id)
    suspend fun getLiveScoreHomeLogoTest(id : Int) = apiResponse.getLiveScoreHomeTeamLogoTest(id)
    suspend fun getStandignsTeamDetails(id: Int)  : Flow<NetworkStatesHandler<StandingsTeamsDetailsModel>>{ // this one
        return flow {
             try {
                emit(handleSuccess(apiResponse.getStandingsDetails(id)))
            }catch (e : Exception){
                emit( handleException(e))
            }
        }

    }
    suspend fun getTeamGames(id : Int) : Flow<TeamMatchesModel>{
        return flow {
            try {
                emit(apiResponse.getTeamGames(id))
            }catch (e : Exception){

            }
        }
    }
    fun getSavedNews() = newsDao.getNews()
    suspend fun insertNews(newsModel: NewsModel) = newsDao.insertNews(newsModel)
    ///------------ Standings Room DB
    fun getStandingsFromDB()  = standingsDao.getStandings()
    suspend fun insertStandingsIntoDB(table:  Table) = standingsDao.insertTable(table)
    ///------------- Fixtures Room
    fun getSavedFixtures() = fixturesDao.getFixtures()
    suspend fun insertFixtures(event: Event) = fixturesDao.insertFixtures(event)
    ///---------------LiveScores Room
    fun getSavedLiveScores() = liveScoresDao.getLiveScores()
    suspend fun insertLiveScores(match : EventTwo) = liveScoresDao.insertLiveScores(match)
    ///----------------TopScorers Room
    fun getSavedTopScorers() = topScorersDao.getTopScorersFromDao()
    suspend fun insertTopScorers(result: ResultMainModel) = topScorersDao.insertTopScorers(result)

    ///---------------StandingsBottomSheet Data
    fun getSavedStandingsBottom() = standingsBottomDao.getBottomStandings()
    suspend fun insertStandingsBottom(model : BottomStandingModel) = standingsBottomDao.insertBottomStandings(model)

    suspend fun deleteDuplicateMatches() = fixturesDao.deleteDuplicateMatches()
    suspend fun deleteDuplicateLiveScores() = liveScoresDao.deleteDuplicateLiveScores()
    suspend fun deleteDuplicateNews()  = newsDao.deleteDuplicateNews()
    suspend fun deleteDuplicateBottomSheetStandings() = standingsBottomDao.deleteDuplicateBottomSheetStandings()
    suspend fun deleteDuplicateBestScorers() = topScorersDao.deleteDuplicateBestScorers()
    suspend fun deleteDuplicateStandings() = standingsDao.deleteDuplicateStandings()

}