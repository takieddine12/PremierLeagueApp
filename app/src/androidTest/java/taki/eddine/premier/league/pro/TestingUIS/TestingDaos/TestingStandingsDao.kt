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
import taki.eddine.premier.league.pro.room.StandingsDao
import taki.eddine.premier.league.pro.getOrAwaitValue
import taki.eddine.premier.league.pro.models.TeamXX

@RunWith(AndroidJUnit4::class)
@LargeTest
@ExperimentalCoroutinesApi
class TestingStandingsDao  {


    private lateinit var standingsDao: StandingsDao
    private lateinit var globalDatabase: GlobalDatabase
    private lateinit var context: Context
    private lateinit var list : MutableList<taki.eddine.premier.league.pro.models.Table>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before()
    fun InitialSetUp(){
        context = ApplicationProvider.getApplicationContext()
        globalDatabase = Room.inMemoryDatabaseBuilder(context,GlobalDatabase::class.java).allowMainThreadQueries().build()
        standingsDao = globalDatabase.standingsDao()
        list = mutableListOf()
    }

    @After
    fun teardown(){
        globalDatabase.close()
    }

    @Test
    fun testStandingsDaoQuery() = runBlockingTest {
        var teams = TeamXX("logourl")
        var table = taki.eddine.premier.league.pro.models.Table(1,1,1,1,1,"man city",
            1,"1",1,5,teams)

        list.add(table)
        standingsDao.insertTable(list)

        var observeData = standingsDao.getStandings().getOrAwaitValue()
        assertThat(observeData).contains(table)
    }

    @Test
    fun testInsertStandings() = runBlockingTest {
        var teams = TeamXX("logourl")
        var table = taki.eddine.premier.league.pro.models.Table(1,1,1,1,1,"man city",
        1,"1",1,5,teams)
        list.add(table)
        standingsDao.insertTable(list)

        var observeData = standingsDao.getStandings().getOrAwaitValue()
        assertThat(observeData).contains(table)
    }
}