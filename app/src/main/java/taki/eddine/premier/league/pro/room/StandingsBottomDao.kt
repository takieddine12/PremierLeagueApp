package taki.eddine.premier.league.pro.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import taki.eddine.premier.league.pro.models.BottomStandingModel

@Dao
interface StandingsBottomDao {

    @Query("SELECT * FROM StandingBottomTable")
    fun getBottomStandings() : LiveData<MutableList<BottomStandingModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBottomStandings(model : BottomStandingModel)

    @Query("DELETE FROM StandingBottomTable WHERE bottomStandingsId NOT IN (SELECT MIN(bottomStandingsId) FROM StandingBottomTable GROUP BY strTeam, strTeamBadge)")
    suspend fun deleteDuplicateBottomSheetStandings()


}