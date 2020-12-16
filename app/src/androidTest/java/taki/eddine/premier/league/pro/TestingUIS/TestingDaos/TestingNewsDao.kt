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
import taki.eddine.premier.league.pro.room.NewsDao
import taki.eddine.premier.league.pro.getOrAwaitValue
import taki.eddine.premier.league.pro.models.NewsModel

@RunWith(AndroidJUnit4::class)
@LargeTest
@ExperimentalCoroutinesApi
class TestingNewsDao {

    private lateinit var globalDatabase: GlobalDatabase
    private lateinit var  newsDao: NewsDao
    private lateinit var context: Context
    private lateinit var list : MutableList<NewsModel>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Before
    fun InitialSetUp(){
        context = ApplicationProvider.getApplicationContext()
        globalDatabase = Room.inMemoryDatabaseBuilder(context,GlobalDatabase::class.java).allowMainThreadQueries().build()
        newsDao = globalDatabase.newsDao()
        list = mutableListOf()
    }

    @After
    fun teardown(){
        globalDatabase.close()
    }

    @Test
    fun TestNewsDaoQuery() = runBlockingTest{
        var newsModel = NewsModel("newsBanner","newsTitle","newsDeescription",
        "newsDate","link")
        newsDao.insertNews(newsModel)
        list.add(newsModel)

        var testData = newsDao.getNews().getOrAwaitValue()
        assertThat(testData).contains(newsModel)
    }

    @Test
    fun TestInsertNews() = runBlockingTest{
        var newsModel = NewsModel("newsBanner","newsTitle","newsDeescription",
            "newsDate","link")
        newsDao.insertNews(newsModel)
        list.add(newsModel)

        var testData = newsDao.getNews().getOrAwaitValue()
        assertThat(testData).contains(newsModel)
    }
}