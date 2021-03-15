@file:Suppress("DEPRECATION")

package taki.eddine.premier.league.pro.ui.fragments

import android.annotation.SuppressLint
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
import maes.tech.intentanim.CustomIntent.customType
import taki.eddine.premier.league.pro.BuildConfig
import taki.eddine.premier.league.pro.Constants
import taki.eddine.premier.league.pro.R
import taki.eddine.premier.league.pro.adapters.TopScorersAdapter
import taki.eddine.premier.league.pro.databinding.TopscorerslayoutBinding
import taki.eddine.premier.league.pro.mvvm.LeagueViewModel
import taki.eddine.premier.league.pro.showToast
import taki.eddine.premier.league.pro.topscorersui.ResultMainModel
import taki.eddine.premier.league.pro.ui.appui.ActivitySettings
import taki.eddine.premier.league.pro.ui.bottomsheetfragments.TopScorersDetailsBottomSheet
import taki.eddine.premier.league.pro.uilisteners.TopScorersListener
import taki.eddine.premier.league.pro.webauthentification.NetworkStatesHandler

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class TopScorersFragment : Fragment() {
  
    private val leagueViewModel: LeagueViewModel by viewModels()
    private lateinit var binding : TopscorerslayoutBinding
    private lateinit var list : MutableList<ResultMainModel>
    private lateinit var adapter : TopScorersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = TopscorerslayoutBinding.inflate(inflater, container, false)
        return binding.root
    }
    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topscorersrecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.topscorersrecycler.setHasFixedSize(true)


        list = mutableListOf()
     
        getTopScorersData()
    }

    private fun getOfflineData(){

        leagueViewModel.observeTopScorers().observe(viewLifecycleOwner, Observer { mainResult ->
            if(mainResult != null){
                binding.topscorersprogress.visibility = View.INVISIBLE
                binding.noData.visibility = View.INVISIBLE
                mainResult.sortBy { it.playerPlace?.toInt() }
                adapter = TopScorersAdapter(mainResult, object : TopScorersListener {
                    override fun topScorers(resultX: ResultMainModel) {
                        binding.noData.visibility = View.INVISIBLE
                        Bundle().apply {
                            putString("playerName", resultX.playerName)
                            putString("icon", resultX.result?.teamLogo)
                            TopScorersDetailsBottomSheet().arguments = this
                            if (Constants.dialogCounter == 0) {
                                TopScorersDetailsBottomSheet().show(
                                    requireActivity().supportFragmentManager,
                                    "tag"
                                )
                                Constants.dialogCounter = 1
                            }
                        }
                    }
                })
                binding.topscorersrecycler.adapter = adapter
            } else {
                binding.topscorersprogress.visibility = View.INVISIBLE
                binding.noData.visibility = View.VISIBLE

            }


        })
    }
    private fun getTopScorersData(){
        lifecycleScope.launch {
            leagueViewModel.getTopScorers(148, BuildConfig.TopScorersApi)
                .observe(viewLifecycleOwner, Observer {
                    if (!it.data?.result.isNullOrEmpty()) {
                        when (it.status) {
                            NetworkStatesHandler.Status.LOADING -> {
                                binding.topscorersprogress.visibility = View.VISIBLE
                            }
                            NetworkStatesHandler.Status.SUCCESS -> {
                                if(it.data!!.result != null && Constants.checkConnectivity(requireContext())){
                                    binding.noData.visibility = View.INVISIBLE
                                    it.data.result?.map { result ->
                                        result.teamKey?.let { teamKey ->
                                            leagueViewModel.getSportApiTeamLogo(teamKey.toInt(), BuildConfig.TopScorersApi).observe(
                                                viewLifecycleOwner, Observer { netWorkResponse ->
                                                    if (!netWorkResponse.result?.isNullOrEmpty()!!) {
                                                        netWorkResponse.result.map { resultX ->
                                                            val resultModel = ResultMainModel(
                                                                result.goals,
                                                                result.playerKey, result.playerName,
                                                                result.playerPlace, result.teamKey,
                                                                result.teamName, resultX
                                                            )

                                                            list.add(resultModel)
                                                            list.sortWith(compareBy { it.playerPlace?.toInt() })
                                                            binding.topscorersrecycler.adapter = TopScorersAdapter(list, object : TopScorersListener {
                                                                override fun topScorers(resultX: ResultMainModel) {
                                                                    val bundle = Bundle()
                                                                    bundle.putString("playerName", resultX.playerName)
                                                                    bundle.putString("icon", resultX.result?.teamLogo)
                                                                    val topScorersDetailsBottomSheet = TopScorersDetailsBottomSheet()
                                                                    topScorersDetailsBottomSheet.arguments = bundle
                                                                    topScorersDetailsBottomSheet.show(
                                                                        parentFragmentManager,
                                                                        "Best Scorers Dialog"
                                                                    )
                                                                }
                                                            })
                                                            leagueViewModel.insertTopScorers(result)
                                                        }
                                                    }
                                                }
                                            )
                                        }
                                    }
                                    binding.topscorersprogress.visibility = View.INVISIBLE
                                }
                                else {
                                    getOfflineData()
                                }

                            }
                            NetworkStatesHandler.Status.ERROR -> {
                                binding.topscorersprogress.visibility = View.INVISIBLE
                                getOfflineData()
                            }
                        }
                    } else {
                        getOfflineData()
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
                if (Constants.checkConnectivity(requireContext())) {
                    requireActivity().supportFragmentManager.beginTransaction().detach(this).attach(
                        this
                    ).commit()
                } else {
                    requireContext().showToast(requireContext(), getString(R.string.nointernet))
                }
            }
            R.id.settings -> {
                Intent(requireContext(), ActivitySettings::class.java).apply {
                    startActivity(this)
                    customType(requireActivity(), "fadein-to-fadeout")
                }
            }
        }
        return true
    }
}