package taki.eddine.premier.league.pro.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import taki.eddine.premier.league.pro.topscorersui.ResultMainModel
@Dao
interface TopScorersDao {

    @Query("SELECT * FROM resultTable")
    fun getTopScorersFromDao() : LiveData<MutableList<ResultMainModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopScorers(result: ResultMainModel)

    @Query("DELETE FROM resultTable WHERE resultID NOT IN (SELECT MIN(resultID) FROM resultTable GROUP BY playerName, playerPlace)")
    suspend fun deleteDuplicateBestScorers()
}