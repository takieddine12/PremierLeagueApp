package taki.eddine.premier.league.pro.services

import android.app.IntentService
import android.content.Intent
import android.graphics.BitmapFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import taki.eddine.premier.league.pro.Constants
import taki.eddine.premier.league.pro.models.NewsModel
import taki.eddine.premier.league.pro.mvvm.LeagueRepository
import taki.eddine.premier.league.pro.mvvm.LeagueViewModel
import java.io.IOException
import java.io.InputStream
import java.net.URL
import javax.inject.Inject


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class NewsService() : IntentService("Debugging") {

    @Inject
    lateinit var repository: LeagueRepository

    @ExperimentalCoroutinesApi
    lateinit var leagueViewModel : LeagueViewModel

    private var title: String? = null
    private var imageUrl: String? = null
    private var description: String? = null
    private var publishDate: String? = null
    private var link: String? = null


    override fun onCreate() {
        super.onCreate()
        leagueViewModel = LeagueViewModel(repository)
    }

    override fun onHandleIntent(intent: Intent?) {
        if(Constants.checkConnectivity(this)){

            imageUrl = intent?.getStringExtra("imageUrl")
            title = intent?.getStringExtra("title")
            description = intent?.getStringExtra("description")
            publishDate = intent?.getStringExtra("publishDate")
            link = intent?.getStringExtra("link")

            imageUrl?.let {
                val url = URL(it)
                val bitmap = BitmapFactory.decodeStream(getInputStream(url))
                val model = NewsModel(bitmap, title, description, publishDate!!, link!!)
                leagueViewModel.insertNews(model)
            }

        }
    }

    private fun getInputStream(url: URL): InputStream? {
        return try {
            url.openConnection().getInputStream()
        } catch (e: IOException) {
            null
        }
    }

}