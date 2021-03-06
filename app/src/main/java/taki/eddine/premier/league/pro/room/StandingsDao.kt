package taki.eddine.premier.league.pro.room

import androidx.lifecycle.LiveData
import androidx.room.*
import taki.eddine.premier.league.pro.models.Table

@Dao
interface StandingsDao {

    @Transaction
    @Query("SELECT * FROM standingTable ORDER BY total , goalsDifference")
    fun getStandings() : LiveData<MutableList<Table>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTable(table: Table)

    @Query("DELETE FROM standingTable WHERE tableID NOT IN (SELECT MIN(tableID) FROM standingTable GROUP BY name, total)")
    suspend fun deleteDuplicateStandings()


}