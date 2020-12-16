package taki.eddine.premier.league.pro.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import taki.eddine.premier.league.pro.models.NewsModel

@Dao
interface NewsDao {

    @Query("SELECT * FROM NewsTable")
    fun getNews() : LiveData<MutableList<NewsModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsModel: NewsModel)

    @Query("DELETE FROM NewsTable WHERE newsID NOT IN (SELECT MIN(newsID) FROM NewsTable GROUP BY newsTitle, newsDate)")
    suspend fun deleteDuplicateNews()



}