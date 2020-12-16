package taki.eddine.premier.league.pro.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import taki.eddine.premier.league.pro.models.Event


@Dao
interface FixturesDao {


    @Query("SELECT * FROM eventTable")
    fun getFixtures() : LiveData<MutableList<Event>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFixtures(event: Event)

    @Query("DELETE FROM eventTable WHERE fixturesID NOT IN (SELECT MIN(fixturesID) FROM eventTable GROUP BY strHomeTeam, strAwayTeam)")
    suspend fun deleteDuplicateMatches()

}