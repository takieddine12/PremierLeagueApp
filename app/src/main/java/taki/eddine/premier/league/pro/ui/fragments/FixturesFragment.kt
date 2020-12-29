package taki.eddine.premier.league.pro.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import maes.tech.intentanim.CustomIntent
import taki.eddine.premier.league.pro.adapters.FixturesAdapter
import taki.eddine.premier.league.pro.ui.appui.ActivitySettings
import taki.eddine.premier.league.pro.Constants.checkConnectivity
import taki.eddine.premier.league.pro.mvvm.LeagueViewModel
import taki.eddine.premier.league.pro.R
import taki.eddine.premier.league.pro.webauthentification.NetworkStatesHandler
import taki.eddine.premier.league.pro.showToast
import taki.eddine.premier.league.pro.databinding.FixtureslayoutBinding
import taki.eddine.premier.league.pro.models.Event
import taki.eddine.premier.league.pro.models.FixturesModel
import taki.eddine.premier.league.pro.objects.UtilsClass
import taki.eddine.premier.league.pro.objects.UtilsClass.ROUND_PREFERENCES
import taki.eddine.premier.league.pro.ui.viewtypes.DateItem
import taki.eddine.premier.league.pro.ui.viewtypes.GeneralItem
import taki.eddine.premier.league.pro.ui.viewtypes.ListItem
import kotlin.collections.LinkedHashMap


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class FixturesFragment : Fragment() {
    private var dataStore: DataStore<Preferences>? = null
    private lateinit var fixturesLayoutBinding: FixtureslayoutBinding
    private lateinit var fixturesMutableList: MutableList<Event>
    private var listItem: MutableList<ListItem>? = null
    private var groupedHashMap: LinkedHashMap<String, MutableList<Event>>? = null


    private val leagueViewModel: LeagueViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fixturesLayoutBinding = FixtureslayoutBinding.inflate(layoutInflater, container, false)
        return fixturesLayoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fixturesLayoutBinding.fixturesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        fixturesLayoutBinding.fixturesRecyclerView.setHasFixedSize(true)

        groupedHashMap = linkedMapOf()


        fixturesMutableList = mutableListOf()
        listItem = mutableListOf()

        dataStore = requireContext().createDataStore(name = "fixturesPrefs")

        if (checkConnectivity(requireContext())) {
            CoroutineScope(Dispatchers.Main).launch {
                dataStore!!.data.asLiveData().observe(viewLifecycleOwner, Observer { preferences ->
                    var counter = preferences[ROUND_PREFERENCES] ?: 1
                    scrollToRound(counter)
                    fixturesLayoutBinding.nextBtn.setOnClickListener {
                        if (counter >= 38) {
                            scrollToRound(counter)
                        }
                        scrollToRound(counter++.plus(1))
                    }
                    fixturesLayoutBinding.previousBtn.setOnClickListener {
                        if (counter <= 1) {
                            scrollToRound(counter)
                        }
                        scrollToRound(counter--.minus(1))
                    }
                })
            }
        } else {
            leagueViewModel.deleteDuplicateMatches()
            leagueViewModel.observeFixtures().observe(viewLifecycleOwner, Observer {
                it?.let {
                    fixturesMutableList = it
                    fixturesMutableList.sortBy { event -> event.strTime }
                    fixturesLayoutBinding.fixturesRecyclerView.adapter =
                        FixturesAdapter(requireActivity(), handleGrouping(fixturesMutableList))
                    fixturesLayoutBinding.fixturesProgressBar.visibility = View.INVISIBLE
                    it.map {
                        fixturesLayoutBinding.round.text =
                            getString(R.string.round).plus(" - ").plus(it.matchRound)
                    }
                }
            })
        }

    }

    private fun scrollToRound(round: Int) {
        groupedHashMap?.clear()
        lifecycleScope.launch {
            leagueViewModel.getNextLeagueFixtures(4328, round, "2020-2021")
                .observe(viewLifecycleOwner, Observer {
                    if (!it.data?.events.isNullOrEmpty()) {
                        when (it.status) {
                            NetworkStatesHandler.Status.LOADING -> {
                                fixturesLayoutBinding.fixturesProgressBar.visibility = View.VISIBLE
                            }
                            NetworkStatesHandler.Status.SUCCESS -> {
                                fixturesLayoutBinding.round.text =
                                    getString(R.string.round).plus(-round)
                                it.data!!.events!!.map { event ->
                                    leagueViewModel.getLiveScoreHomeLogo(event.idHomeTeam!!.toInt())
                                        .observe(viewLifecycleOwner,
                                            Observer {
                                                it.teams!!.map { HomeLogoUrl ->
                                                    leagueViewModel.getLiveScoreHomeLogoTest(event.idAwayTeam!!.toInt())
                                                        .observe(viewLifecycleOwner,
                                                            Observer { AwayLogoUrl ->
                                                                AwayLogoUrl.teams!!.map {
                                                                    val fixture = Event(
                                                                        HomeLogoUrl,
                                                                        it,
                                                                        event.idAwayTeam,
                                                                        event.idEvent,
                                                                        event.idHomeTeam,
                                                                        event.intAwayScore,
                                                                        event.intHomeScore,
                                                                        event.intRound,
                                                                        event.strAwayGoalDetails,
                                                                        event.strAwayRedCards,
                                                                        event.strAwayTeam,
                                                                        event.strAwayYellowCards,
                                                                        event.strHomeGoalDetails,
                                                                        event.strHomeRedCards,
                                                                        event.strHomeTeam,
                                                                        event.strHomeYellowCards,
                                                                        UtilsClass.convertDate(event.dateEvent!!),
                                                                        UtilsClass.convertHour(event.strTime!!),
                                                                        event.strPostponed
                                                                    )
                                                                    fixture.matchRound = round

                                                                    fixturesMutableList.clear()
                                                                    fixturesMutableList.add(fixture)


                                                                    fixturesMutableList.sortedWith(
                                                                        compareBy { it.strTime })

                                                                    handleGrouping(
                                                                        fixturesMutableList
                                                                    )
                                                                    fixturesLayoutBinding.fixturesRecyclerView.adapter =
                                                                        FixturesAdapter(
                                                                            requireActivity(),
                                                                            listItem!!
                                                                        )
                                                                    fixturesLayoutBinding.fixturesProgressBar.visibility =
                                                                        View.INVISIBLE
                                                                   // leagueViewModel.insertFixtures(fixture)
                                                                }

                                                            })
                                                }
                                            })
                                }


                            }
                            NetworkStatesHandler.Status.ERROR -> {
                                fixturesLayoutBinding.fixturesProgressBar.visibility =
                                    View.INVISIBLE
                            }
                        }
                    }
                })
        }
    }

    private fun handleGrouping(fixturesMutableList: MutableList<Event>): MutableList<ListItem> {
        groupedHashMap = hashMapGroup(fixturesMutableList)
        listItem?.clear()
        for (date in groupedHashMap!!.keys) {
            val dateItem = DateItem()
            dateItem.setDate(date)
            listItem?.add(dateItem)
            for (po_jo in groupedHashMap!![date]!!) {
                val generalItem = GeneralItem()
                generalItem.setPojoOfJsonArray(po_jo)
                listItem?.add(generalItem)
            }

        }
        return listItem!!
    }

    private fun hashMapGroup(list: MutableList<Event>): LinkedHashMap<String, MutableList<Event>> {
        for (poJo in list) {
            val hashKey = poJo.dateEvent
            if (groupedHashMap!!.containsKey(hashKey)) {
                groupedHashMap!![hashKey]!!.add(poJo)
            } else {
                val emptyList = mutableListOf<Event>()
                emptyList.add(poJo)
                groupedHashMap!![hashKey!!] = emptyList
            }
        }
        return groupedHashMap!!
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
                if (checkConnectivity(requireContext())) {
                    requireActivity().supportFragmentManager.beginTransaction().detach(this)
                        .attach(this).commit()
                } else {
                    requireContext().showToast(requireContext(), getString(R.string.nointernet))
                }
            }
            R.id.settings -> {
                Intent(requireContext(), ActivitySettings::class.java).apply {
                    startActivity(this)
                    CustomIntent.customType(requireActivity(), "fadein-to-fadeout")
                }
            }
        }
        return true
    }
}