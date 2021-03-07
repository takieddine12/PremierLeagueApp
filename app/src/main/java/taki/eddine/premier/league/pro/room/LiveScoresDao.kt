package taki.eddine.premier.league.pro.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import taki.eddine.premier.league.pro.livescoresdata.EventTwo

@Dao
interface LiveScoresDao {

    @Query("SELECT * FROM liveScoresTable")
    fun getLiveScores() : LiveData<MutableList<EventTwo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLiveScores(match: kotlin.collections.MutableList<taki.eddine.premier.league.pro.livescoresdata.EventTwo?>)

    @Query("DELETE FROM liveScoresTable  WHERE matchID NOT IN (SELECT MIN(matchID) FROM liveScoresTable GROUP BY strHomeTeam, strAwayTeam)")
    suspend fun deleteDuplicateLiveScores()

}