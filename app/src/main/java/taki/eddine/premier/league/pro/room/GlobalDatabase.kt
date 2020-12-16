package taki.eddine.premier.league.pro.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import taki.eddine.premier.league.pro.LeagueTypeConverter
import taki.eddine.premier.league.pro.models.*
import taki.eddine.premier.league.pro.livescoresdata.EventTwo
import taki.eddine.premier.league.pro.topscorersui.ResultMainModel

@Database(entities = [NewsModel::class,Table::class,EventTwo::class, Event::class,ResultMainModel::class,BottomStandingModel::class],version = 1,exportSchema = false)
@TypeConverters(LeagueTypeConverter::class)
abstract class GlobalDatabase : RoomDatabase(){

    abstract fun newsDao() : NewsDao
    abstract fun standingsDao() : StandingsDao
    abstract fun fixturesDao() : FixturesDao
    abstract fun livescoresDao() : LiveScoresDao
    abstract fun topscorersDao() : TopScorersDao
    abstract fun StandingsBottomDao() : StandingsBottomDao

}