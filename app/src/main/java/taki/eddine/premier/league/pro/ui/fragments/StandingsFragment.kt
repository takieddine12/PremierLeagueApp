package taki.eddine.premier.league.pro.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.minibugdev.sheetselection.SheetSelection
import com.minibugdev.sheetselection.SheetSelectionItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import maes.tech.intentanim.CustomIntent
import taki.eddine.premier.league.pro.adapters.StandingsAdapter
import taki.eddine.premier.league.pro.ui.appui.ActivitySettings
import taki.eddine.premier.league.pro.Constants
import taki.eddine.premier.league.pro.mvvm.LeagueViewModel
import taki.eddine.premier.league.pro.R
import taki.eddine.premier.league.pro.webauthentification.NetworkStatesHandler
import taki.eddine.premier.league.pro.showToast
import taki.eddine.premier.league.pro.databinding.StandingslayoutBinding
import taki.eddine.premier.league.pro.models.BottomStandingModel
import taki.eddine.premier.league.pro.models.Table
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class StandingsFragment : Fragment() {

    private lateinit var prefs : SharedPreferences
    private val leagueViewModel: LeagueViewModel by viewModels()
    private var mutableList: MutableList<Table>? = null
    private lateinit var binding: StandingslayoutBinding
    private lateinit var standingsAdapter: StandingsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = StandingslayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.standingsrecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.standingsrecycler.setHasFixedSize(true)

        mutableList = mutableListOf()

        if(Constants.checkConnectivity(requireContext())){
            getData()
        } else {

        }


        getClubsDetails()
    }
    private fun getOfflineData(){
        leagueViewModel.deleteDuplicateStandings()
        leagueViewModel.observeStandings().observe(viewLifecycleOwner, Observer { list ->
          if(list  != null &&  list.isNotEmpty()){
              list.sortWith(compareByDescending<Table> { it.total }.thenByDescending { it.goalsDifference })
              standingsAdapter = StandingsAdapter(requireActivity(), list)
              binding.standingsrecycler.adapter = standingsAdapter
              binding.standingProgressbar.visibility = View.INVISIBLE
          } else {
              list.sortWith(compareByDescending<Table> { it.total }.thenByDescending { it.goalsDifference })
              standingsAdapter = StandingsAdapter(requireActivity(), list)
              binding.standingsrecycler.adapter = standingsAdapter
              binding.standingProgressbar.visibility = View.INVISIBLE
          }
        })
    }
    private fun getData(){
        lifecycleScope.launch {
            leagueViewModel.getStandings(4328, "2020-2021")
                .observe(viewLifecycleOwner, Observer {
                    when (it.status) {
                        NetworkStatesHandler.Status.LOADING -> {
                            binding.standingProgressbar.visibility = View.VISIBLE
                        }
                        NetworkStatesHandler.Status.SUCCESS -> {
                            val list = it.data?.table
                            if (list != null && list.isNotEmpty() && Constants.checkConnectivity(requireContext())) {
                                list.map { table ->
                                    val standingTable = Table(
                                        table.strForm,
                                        table.strTeamBadge,
                                        table.intRank,
                                        table.draw,
                                        table.goalsAgainst,
                                        table.goalsDifference,
                                        table.goalsFor,
                                        table.loss,
                                        table.name,
                                        table.played,
                                        table.teamid,
                                        table.total,
                                        table.win
                                    )

                                    mutableList?.add(standingTable)
                                    mutableList?.sortWith(compareByDescending<Table> { it.total }
                                        .thenByDescending { it.goalsDifference })

                                    standingsAdapter = StandingsAdapter(requireActivity(), mutableList!!)
                                    binding.standingsrecycler.adapter = standingsAdapter
                                    leagueViewModel.insertTableStandings(table)

                                }
                                binding.standingProgressbar.visibility = View.INVISIBLE

                            }
                            else {
                                getOfflineData()
                            }
                        }
                        NetworkStatesHandler.Status.ERROR -> {
                            binding.standingProgressbar.visibility = View.INVISIBLE
                            getOfflineData()
                        }
                    }
                })
        }

    }
    private fun getClubsDetails()  {
        lifecycleScope.launch {
            leagueViewModel.getClubsDetails("English Premier League").observe(
                viewLifecycleOwner,
                Observer {
                    when (it.status) {
                        NetworkStatesHandler.Status.LOADING -> {

                        }
                        NetworkStatesHandler.Status.SUCCESS -> {
                            if (!it.data?.teams.isNullOrEmpty()) {
                                it.data?.teams?.map { team ->
                                    val url = URL(team.strTeamBadge)
                                    val LogoUrl = URL(team.strStadiumThumb)
                                 //   getTeamLogo(team.strTeam!!)
                                    try {
                                        Thread{

                                            // TODO : Stadium Thumb
                                            val httpURLConnectionTeam = LogoUrl.openConnection() as HttpsURLConnection
                                            httpURLConnectionTeam.connect()
                                            httpURLConnectionTeam.readTimeout = 60
                                            val inputStreamTeam = httpURLConnectionTeam.inputStream
                                            val byteArrayStadium = ByteArrayOutputStream()
                                            val teamLogoBitmap = BitmapFactory.decodeStream(inputStreamTeam)
                                            teamLogoBitmap.compress(Bitmap.CompressFormat.PNG, 100,byteArrayStadium)

                                            // TODO StrTeam Badge
                                            val httpsURLConnectionTeam = url.openConnection() as HttpsURLConnection
                                            httpsURLConnectionTeam.connect()
                                            httpsURLConnectionTeam.readTimeout  = 60
                                            val inputStreamStadium = httpsURLConnectionTeam.inputStream
                                            val byteArrayTeamBadge = ByteArrayOutputStream()
                                            val stadiumBitmap = BitmapFactory.decodeStream(inputStreamStadium)
                                            stadiumBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayTeamBadge)

                                            requireActivity().runOnUiThread {

                                                val bottomStandingModel = BottomStandingModel(
                                                    team.strDescriptionEN!!, team.strTeam,
                                                    byteArrayTeamBadge.toByteArray(),
                                                    team.strTeamBanner!!, byteArrayStadium.toByteArray(),
                                                    team.strStadiumLocation!!, team.intStadiumCapacity!!
                                                )

//                                                Intent(requireContext(),BottomSheetStandingsService::class.java).apply {
//                                                    putExtra("bottomStandingModel",bottomStandingModel)
//                                                    requireContext().startService(this)
//                                                }
                                            }
                                        }.start()
                                    }catch (e : Exception){
                                        Timber.d("HttpUrlConnection Exception ${e.message}")
                                    }
                                }
                            }
                        }
                        NetworkStatesHandler.Status.ERROR -> {
                            //Something wrong happened
                        }
                    }

                })
        }
    }
//    private fun getTeamLogo(team : String) : ByteArrayOutputStream {
//        val url = URL(team)
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        try {
//            Thread{
//
//            }.start()
//        }catch (e : Exception){
//            Timber.d("HttpUrlConnection Exception ${e.message}")
//        }
//        return byteArrayOutputStream
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
        menu.getItem(0).isVisible = true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.refresh -> {
                if (Constants.checkConnectivity(requireContext())) {
                    mutableList?.clear()
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
                    CustomIntent.customType(requireActivity(), "fadein-to-fadeout")
                }
            }
            R.id.instructions -> {
                val itemList = listOf<SheetSelectionItem>(
                    SheetSelectionItem("", "P : ${requireContext().getString(R.string.standingsPlayed)}", R.drawable.dot),
                    SheetSelectionItem("", "GD: ${requireContext().getString(R.string.standingsGoalDifference)}", R.drawable.dot),
                    SheetSelectionItem("", "W : ${requireContext().getString(R.string.standingsWonMatches)}", R.drawable.dot),
                    SheetSelectionItem("", "D : ${requireContext().getString(R.string.standingsDraw)}", R.drawable.dot),
                    SheetSelectionItem("", "L : ${requireContext().getString(R.string.standingsLoss)}", R.drawable.dot),
                    SheetSelectionItem("", "T : ${requireContext().getString(R.string.standingsTotal)}", R.drawable.dot),
                    SheetSelectionItem("", "PS :${requireContext().getString(R.string.standingsStandingsInfo)}")
                )

                SheetSelection.Builder(requireContext())
                    .showDraggedIndicator(true)
                    .title(requireContext().getString(R.string.standingsInstructions))
                    .items(itemList)
                    .showDraggedIndicator(true)
                    .build()
                    .show(requireActivity().supportFragmentManager, "")

            }
        }
        return true
    }


}