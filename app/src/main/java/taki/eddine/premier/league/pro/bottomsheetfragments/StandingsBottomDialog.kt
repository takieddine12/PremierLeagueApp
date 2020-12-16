package taki.eddine.premier.league.pro.bottomsheetfragments

import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import taki.eddine.premier.league.pro.Constants
import taki.eddine.premier.league.pro.mvvm.LeagueViewModel
import taki.eddine.premier.league.pro.R
import taki.eddine.premier.league.pro.webauthentification.NetworkStatesHandler
import taki.eddine.premier.league.pro.databinding.ActivityStandingsTeamsDetailsBinding
import taki.eddine.premier.league.pro.models.TeamXXX
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class StandingsBottomDialog : BottomSheetDialogFragment() {

    private lateinit var binding : ActivityStandingsTeamsDetailsBinding
    private val leagueViewModel  : LeagueViewModel by viewModels()
    private  var mutableList: MutableList<TeamXXX>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ActivityStandingsTeamsDetailsBinding.inflate(layoutInflater,container,false)

        mutableList = mutableListOf()

        leagueViewModel.deleteDuplicateBottomSheetStandings()
        val teamId = requireArguments().getString("teamId")
        teamId.let {
            if(Constants.checkConnectivity(requireContext())){
                lifecycleScope.launch {
                    leagueViewModel.getStandingsTeamsDetails(it!!.toInt())
                        .observe(this@StandingsBottomDialog, Observer {
                            when(it.status){
                                NetworkStatesHandler.Status.LOADING ->{

                                }
                                NetworkStatesHandler.Status.SUCCESS ->{
                                    it.data!!.teams!!.map {
                                        Picasso.get().load(it.strTeamBadge).fit().into(binding.teamlogo)
                                        Picasso.get().load(it.strStadiumThumb).fit().into(binding.staduimlogo)
                                        binding.teamname.text =it.strTeam
                                        binding.staduimname.text =it.strStadium
                                        binding.teamdescription.text = it.strDescriptionEN
                                        binding.staduimCapacity.text = it.intStadiumCapacity
                                        binding.staduimLocation.text = it.strStadiumLocation
                                    }
                                }
                                NetworkStatesHandler.Status.ERROR ->{

                                }
                            }
                        })

                }
            } else {
                val  arguments = requireArguments()
                val teamBadge = arguments.getByteArray("teamBadge")
                var stadiumThumb = arguments.getByteArray("stadiumthumb")

                val teamBitmap = BitmapFactory.decodeByteArray(teamBadge,0, teamBadge!!.size)
                //----
                binding.teamlogo.setImageBitmap(teamBitmap)
                binding.staduimlogo.setImageResource(R.drawable.ic_noimg)
                binding.teamname.text = arguments.getString("team")
                binding.staduimname.text = arguments.getString("stadium")
                binding.teamdescription.text = arguments.getString("description")
                binding.staduimCapacity.text = arguments.getString("stadiumCapacity")
                binding.staduimLocation.text = arguments.getString("stadiumLocation")
            }
        }

        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL,R.style.CustonBottomSheet)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireDialog() as BottomSheetDialog).dismissWithAnimation = true
        }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Constants.dialogCounter = 0
    }
}
