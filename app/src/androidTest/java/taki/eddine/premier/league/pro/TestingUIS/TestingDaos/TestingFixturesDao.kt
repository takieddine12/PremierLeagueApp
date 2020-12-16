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
import taki.eddine.premier.league.pro.room.FixturesDao
import taki.eddine.premier.league.pro.room.GlobalDatabase
import taki.eddine.premier.league.pro.getOrAwaitValue
import taki.eddine.premier.league.pro.models.AwayLogoModel
import taki.eddine.premier.league.pro.models.Event
import taki.eddine.premier.league.pro.models.TeamXX

@RunWith(AndroidJUnit4::class)
@LargeTest
@ExperimentalCoroutinesApi
class TestingFixturesDao {

    private lateinit var database: GlobalDatabase
    private lateinit var fixturesDao: FixturesDao
    private lateinit var context : Context
    private lateinit var list : MutableList<Event>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun InitialSetUp(){
        context = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(context,GlobalDatabase::class.java).allowMainThreadQueries().build()
        fixturesDao = database.fixturesDao()
        list = mutableListOf()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun testFixtureDaoQuery() = runBlockingTest{
        var teamXX = TeamXX("homeurl")
        var awayLogoModel = AwayLogoModel("awayurl")
        var event = Event(teamXX,awayLogoModel,"dateevent","idAwayTeam","idEvent","idHomeTeam",
            "idAwayScore","intHomeScore","intRound","text","text","text",
            "text","text","text","text","text","text")
        list.add(event)
        fixturesDao.insertFixtures(list)

        val observeChanges  =  fixturesDao.getFixtures().getOrAwaitValue()
        assertThat(observeChanges).contains(event)
    }

    @Test
    fun testInsertFixtures() = runBlockingTest{

        var teamXX = TeamXX("homeurl")
        var awayLogoModel = AwayLogoModel("awayurl")
        var event = Event(teamXX,awayLogoModel,"dateevent","idAwayTeam","idEvent","idHomeTeam",
            "idAwayScore","intHomeScore","intRound","text","text","text",
            "text","text","text","text","text","text")
        list.add(event)
        fixturesDao.insertFixtures(list)

        var observeChanges  =  fixturesDao.getFixtures().getOrAwaitValue()
        assertThat(observeChanges).contains(event)


    }
}