package taki.eddine.premier.league.pro.webauthentification


import retrofit2.http.GET
import retrofit2.http.Query
import taki.eddine.premier.league.pro.CurrentRoundModel
import taki.eddine.premier.league.pro.livescoresdata.v2livescores
import taki.eddine.premier.league.pro.models.*
import taki.eddine.premier.league.pro.standingSavedDataUi.StandingClubModel
import taki.eddine.premier.league.pro.topscorersui.SportApiLogoModel
import taki.eddine.premier.league.pro.topscorersui.TopScorersModl
import taki.eddine.premier.league.pro.topscorersui.TopScorersPlayersDetails


interface ApiResponse {

    @GET("v1/json/4013070/search_all_teams.php?")
    suspend fun getClubDetails(@Query("l") league : String) : StandingClubModel
    //----
    //TODO: API REQUEST MADE FROM https://allsportsapi.com/
    @GET("football/?&met=Topscorers")
    suspend fun getTopScorers(@Query("leagueId") leagueid : Int, @Query("APIkey") apiKey : String) : TopScorersModl

    @GET("football/?&met=Teams")
    suspend fun getSportApiTeamLogo(@Query("teamId") teamId : Int,@Query("APIkey") APIkey : String) : SportApiLogoModel

    @GET("football/?&met=Players")
    suspend fun getPlayerDetails(@Query("playerName") playerName : String , @Query("APIkey") apiKey : String) : TopScorersPlayersDetails
    ///----------------------------------------------------------------------
    @GET("v1/json/4013070/eventsseason.php?")
    suspend fun getCurrentRound(@Query("id")id : Int,@Query("s") season : String) : CurrentRoundModel

    @GET("v2/json/8673533/livescore.php?s=Soccer")
    suspend fun getLiveScores() : v2livescores

    @GET("v1/json/4013070/eventsround.php?")
    suspend fun getNextFixtures(@Query("id") id : Int, @Query("r") round : Int , @Query("s") season : String) : FixturesModel


    @GET("v1/json/1/lookuptable.php")
    suspend fun getStandings(@Query("l") id : Int, @Query("s") seanson : String ) : StandingsModel


    @GET("v1/json/4013070/lookup_all_teams.php?")
    suspend fun getAllTeams(@Query("id") id : Int) : NotificationModel


    @GET("v1/json/4013070/lookupteam.php?")
    suspend fun getLiveScoreHomeTeamLogo(@Query("id") id: Int) : LiveScoreModel

    @GET("v1/json/4013070/lookupteam.php?")
    suspend fun getLiveScoreHomeTeamLogoTest(@Query("id") id: Int) : LiveScoreModelAway


    @GET("v1/json/4013070/lookupteam.php?")
    suspend fun getStandingsDetails(@Query("id") id: Int) : StandingsTeamsDetailsModel

    @GET("v1/json/4013070/eventsnext.php?")
    suspend fun getTeamGames(@Query("id") id: Int ) : TeamMatchesModel

}