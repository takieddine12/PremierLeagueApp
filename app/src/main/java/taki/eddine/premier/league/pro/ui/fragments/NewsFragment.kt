package taki.eddine.premier.league.pro.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fixturesawaylayout.*
import kotlinx.coroutines.*
import maes.tech.intentanim.CustomIntent.customType
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import taki.eddine.premier.league.pro.BuildConfig
import taki.eddine.premier.league.pro.adapters.LeagueNewsAdapter
import taki.eddine.premier.league.pro.ui.appui.ActivityOfflineNews
import taki.eddine.premier.league.pro.ui.appui.ActivitySettings
import taki.eddine.premier.league.pro.ui.appui.RssActivity
import taki.eddine.premier.league.pro.Constants
import taki.eddine.premier.league.pro.mvvm.LeagueViewModel
import taki.eddine.premier.league.pro.R
import taki.eddine.premier.league.pro.showToast
import taki.eddine.premier.league.pro.databinding.NewslayoutBinding
import taki.eddine.premier.league.pro.models.NewsModel
import taki.eddine.premier.league.pro.services.NewsService
import taki.eddine.premier.league.pro.uilisteners.RssListener
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.channels.InterruptedByTimeoutException


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class NewsFragment : Fragment() {


    private val leagueViewModel: LeagueViewModel by viewModels()
    private var title: String? = null
    private var imageUrl: String? = null
    private var description: String? = null
    private var publishDate: String? = null
    private var link: String? = null
    private var xmlList: MutableList<NewsModel>? = null
    private lateinit var newsAdapter: LeagueNewsAdapter
    private lateinit var binding: NewslayoutBinding
    private lateinit var adapter: LeagueNewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = NewslayoutBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newsrcycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.newsrcycler.setHasFixedSize(true)
        xmlList = mutableListOf()
    }
    @SuppressLint("CommitPrefEdits")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val prefs  = requireContext().getSharedPreferences("news", Context.MODE_PRIVATE)

        val isFirstTime = prefs.getBoolean("firstTime",true)
        if(isFirstTime and Constants.checkConnectivity(requireContext())){
            CoroutineScope(Dispatchers.IO).launch {
                getData()
            }
        } else if(!isFirstTime ) {
            leagueViewModel.deleteDuplicateNews()
            leagueViewModel.observeNews().observe(viewLifecycleOwner, Observer {
                newsAdapter = LeagueNewsAdapter(requireContext(), it, object : RssListener {
                    override fun rssArticles(model: NewsModel) {
                        //TODO : Converting Bitmap to ByeArray
                        Intent(requireContext(), ActivityOfflineNews::class.java).apply {
                            putExtra("offlineTitle", model.newsTitle)
                            putExtra("offlineDescription", model.newsDescription)
                            putExtra("offlineImage", model.newsBanner)
                            startActivity(this)
                            customType(requireActivity(),"fadein-to-fadeout")
                        }

                    }
                })
                binding.newsrcycler.adapter = newsAdapter
                binding.newsprogress.visibility = View.INVISIBLE
            })
        }

        val editor = prefs.edit()
        editor.apply {
            putBoolean("firstTime",false)
            apply()
        }


    }

    private fun getInputStream(url: URL): InputStream? {
        return try {
            url.openConnection().getInputStream()
        } catch (e: IOException) {
            null
        }
    }
    private fun getData(){
            val url = URL(BuildConfig.Eye_Football_Key)
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = false
            val xpp = factory.newPullParser()
            xpp.setInput(getInputStream(url), "utf-8")
            var insideItem = false
            var eventType = xpp.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.name.equals("item", ignoreCase = true)) {
                        insideItem = true
                    } else if (xpp.name.equals("title", ignoreCase = true)) {
                        if (insideItem) {
                            title = xpp.nextText()
                        }
                    } else if (xpp.name.equals("pubDate", ignoreCase = true)) {
                        if (insideItem) {
                            val subbedDate = xpp.nextText()
                            publishDate = subbedDate.substring(0, 25)
                        }
                    } else if (xpp.name.equals("guid", ignoreCase = true)) {
                        if (insideItem) {
                            link = xpp.nextText()
                        }
                    } else if (xpp.name.equals("description", ignoreCase = true)) {
                        if (insideItem) {
                            val newsDescription = xpp.nextText()
                            if (newsDescription.contains("src") && newsDescription.contains("jpg")) {
                                imageUrl = newsDescription.substring(
                                    newsDescription.indexOf("src=") + 5,
                                    newsDescription.indexOf("jpg") + 3
                                )
                            }
                            if (newsDescription.contains("<BR>") && newsDescription.contains("</p>")) {
                                description =
                                    newsDescription.substring(
                                        newsDescription.indexOf("<BR>") + 4,
                                        newsDescription.indexOf("</p>")
                                    )
                            }
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG && xpp.name.equals("item", ignoreCase = true)) {
                    insideItem = false
                    imageUrl?.let {
                        //TODO : Getting Image Url And Convert it to Bitmap
                        val urlImg = URL(it)
                        val httpURLConnection = urlImg.openConnection() as HttpURLConnection
                        httpURLConnection.connect()
                        val inputStream = httpURLConnection.inputStream
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        CoroutineScope(Dispatchers.Main).launch{
                            val model = NewsModel(bitmap, title, description, publishDate!!, link!!)
                            xmlList?.add(model)
                            adapter = LeagueNewsAdapter(requireContext(), xmlList!!, object : RssListener {
                                override fun rssArticles(model: NewsModel) {
                                    Intent(requireContext(), RssActivity::class.java).apply {
                                        putExtra("url", model.link)
                                        startActivity(this)
                                        customType(requireActivity(),"fadein-to-fadeout")
                                    }
                                }
                            })
                            Intent(requireContext(),NewsService::class.java).apply {
                                putExtra("newsModel",model)
                                requireContext().startService(this)
                            }
                        }
                    }
                }
                eventType = xpp.next()
            }
        CoroutineScope(Dispatchers.Main).launch {
            binding.newsrcycler.adapter = adapter
            binding.newsprogress.visibility = View.INVISIBLE

        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> {
                if(Constants.checkConnectivity(requireContext())){
                    requireActivity().supportFragmentManager.beginTransaction().detach(this).attach(this).commit()
                } else {
                    requireContext().showToast(requireContext(),getString(R.string.nointernet))
                }
            }
            R.id.settings -> {
                Intent(requireContext(), ActivitySettings::class.java).apply {
                    startActivity(this)
                    customType(requireContext(),"fadein-to-fadeout")
                }
            }
        }
        return true
    }

}