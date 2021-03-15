package taki.eddine.premier.league.pro.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import maes.tech.intentanim.CustomIntent
import taki.eddine.premier.league.pro.adapters.LiveScoresAdapter
import taki.eddine.premier.league.pro.ui.appui.ActivitySettings
import taki.eddine.premier.league.pro.Constants
import taki.eddine.premier.league.pro.mvvm.LeagueViewModel
import taki.eddine.premier.league.pro.R
import taki.eddine.premier.league.pro.webauthentification.NetworkStatesHandler
import taki.eddine.premier.league.pro.showToast
import taki.eddine.premier.league.pro.databinding.LivescoreslayoutBinding
import taki.eddine.premier.league.pro.livescoresdata.EventTwo
import taki.eddine.premier.league.pro.objects.UtilsClass
import timber.log.Timber


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class LiveScoresFragment : Fragment() {

    private lateinit var binding: LivescoreslayoutBinding
    private val leagueViewModel: LeagueViewModel by viewModels()
    private var mutableList: MutableList<EventTwo>? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LivescoreslayoutBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.livescoresrecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.livescoresrecycler.setHasFixedSize(true)

        mutableList = mutableListOf()

        getData()
    }
    private fun getSavedLiveScores() {
        leagueViewModel.deleteDuplicateLiveScores()
        leagueViewModel.observeLiveScores().observe(viewLifecycleOwner, Observer { list ->
            if(list != null && list.isNotEmpty()){
                binding.noData.visibility = View.GONE
                binding.noDataText.visibility = View.GONE
                list.map {
                    binding.fixturedate.text = it.dateEvent
                    binding.update.text = getString(R.string.updatedOn).plus(it.updated)
                }
                binding.livescoresrecycler.adapter = LiveScoresAdapter(requireContext(),list)
            } else {
                binding.noData.visibility = View.VISIBLE
                binding.noDataText.visibility = View.VISIBLE
                binding.livescoresrecycler.adapter = LiveScoresAdapter(requireContext(),list)
            }

        })
    }
    private fun getData() {
        mutableList?.clear()
        lifecycleScope.launch {
            leagueViewModel.getLiveScores().observe(viewLifecycleOwner, Observer {
                when(it.status){
                    NetworkStatesHandler.Status.LOADING ->{
                        binding.livescoreProgressbar.visibility = View.VISIBLE
                    }
                    NetworkStatesHandler.Status.SUCCESS -> {
                        val list = it.data?.events
                        binding.livescoreProgressbar.visibility = View.INVISIBLE
                        if (list != null && list.isNotEmpty() && Constants.checkConnectivity(requireContext())) {
                           list.map { match ->
                                if (match.strLeague.equals("English Premier League")) {
                                    binding.update.text = getString(R.string.updatedOn).plus(match.updated)
                                    binding.fixturedate.text = UtilsClass.convertDate(match.dateEvent!!)
                                    val liveScores = EventTwo(
                                        match.strHomeTeam,
                                        match.strAwayTeam,
                                        match.strHomeTeamBadge,
                                        match.strAwayTeamBadge,
                                        match.intHomeScore,
                                        match.intAwayScore,
                                        match.strProgress,
                                        UtilsClass.convertHour(match.strEventTime!!),
                                        UtilsClass.convertDate(match.dateEvent!!),
                                        UtilsClass.convertUpdatedDate(match.updated!!),
                                        match.strLeague
                                    )

                                    mutableList?.add(liveScores)
                                    binding.livescoresrecycler.adapter = LiveScoresAdapter(requireActivity(), mutableList!!)

                                }
                                else {
                                    Timber.d("Code Executed Here..")
                                    binding.noData.visibility = View.VISIBLE
                                    binding.livescoreProgressbar.visibility = View.INVISIBLE
                                    getSavedLiveScores()
                                }
                            } }
                             else {
                                binding.livescoreProgressbar.visibility = View.INVISIBLE
                                getSavedLiveScores()

                        }
                    }
                    NetworkStatesHandler.Status.ERROR ->{
                        getSavedLiveScores()
                        binding.livescoreProgressbar.visibility = View.INVISIBLE
                    }
                }
            })
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
        when(item.itemId){
            R.id.refresh -> {
               if (Constants.checkConnectivity(requireContext())){
                   requireActivity().supportFragmentManager.beginTransaction().detach(this).attach(this).commit()
               } else {
                   requireContext().showToast(requireContext(),getString(R.string.nointernet))
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