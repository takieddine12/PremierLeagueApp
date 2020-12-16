package taki.eddine.premier.league.pro.TestingUIS.TestingDaos

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import taki.eddine.premier.league.pro.room.GlobalDatabase
import taki.eddine.premier.league.pro.room.LiveScoresDao
import taki.eddine.premier.league.pro.getOrAwaitValue
import taki.eddine.premier.league.pro.models.AwayLogoModel
import taki.eddine.premier.league.pro.models.TeamXX

@RunWith(AndroidJUnit4::class)
@LargeTest
@ExperimentalCoroutinesApi
class TestingLiveScoresDao {

    private lateinit var globalDatabase: GlobalDatabase
    private lateinit var liveScoresDao: LiveScoresDao
    private lateinit var context : Context
    private lateinit var list : MutableList<Match>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun InitialSetUp(){
       context = ApplicationProvider.getApplicationContext()
        globalDatabase = Room.inMemoryDatabaseBuilder(context,GlobalDatabase::class.java).allowMainThreadQueries().build()
        liveScoresDao = globalDatabase.livescoresDao()
        list = mutableListOf()
    }

    @After
    fun teardown(){
        globalDatabase.close()
    }

    @Test
    fun TestingLiveScoresQuery() = runBlockingTest{
        var hometeam  = TeamXX("hometeamUrl")
        var awayLogoModel = AwayLogoModel("awayteamUrl")
        var match = Match("awaygoals","awayteam","awayteamid","date","hasbeenrescheduled","homegoals","hometeam",
            "hometeamid","round","stadium","time",hometeam,awayLogoModel)

        list.add(match)
        liveScoresDao.insertLiveScores(list)

        var TestingData = liveScoresDao.getLiveScores().getOrAwaitValue()
        assertThat(TestingData).contains(match)
    }

    @Test
    fun TestingInsertLiveScores() = runBlockingTest{

        var hometeam  = TeamXX("hometeamUrl")
        var awayLogoModel = AwayLogoModel("awayteamUrl")
        var match = Match("awaygoals","awayteam","awayteamid","date","hasbeenrescheduled","homegoals","hometeam",
        "hometeamid","round","stadium","time",hometeam,awayLogoModel)

        list.add(match)
        liveScoresDao.insertLiveScores(list)

        var TestingData = liveScoresDao.getLiveScores().getOrAwaitValue()
        assertThat(TestingData).contains(match)
    }




}