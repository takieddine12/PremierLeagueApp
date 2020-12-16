package taki.eddine.premier.league.pro.bottomsheetfragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import taki.eddine.premier.league.pro.BuildConfig
import taki.eddine.premier.league.pro.mvvm.LeagueViewModel
import taki.eddine.premier.league.pro.webauthentification.NetworkStatesHandler
import taki.eddine.premier.league.pro.Constants
import taki.eddine.premier.league.pro.databinding.TopscorersbottomsheetlayoutBinding

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class TopScorersDetailsBottomSheet : BottomSheetDialogFragment() {

    private val leagueViewModel: LeagueViewModel by viewModels()
    private lateinit var binding : TopscorersbottomsheetlayoutBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =  TopscorersbottomsheetlayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(Constants.checkConnectivity(requireContext())){
            val  playerName = arguments?.getString("playerName")?.replace(".","")
            val icon = arguments?.getString("icon")
            lifecycleScope.launch {
                leagueViewModel.getPlayerDetails(playerName!!,BuildConfig.TopScorersApi).observe(viewLifecycleOwner, Observer {
                    when(it.status){
                        NetworkStatesHandler.Status.LOADING ->{

                        }
                        NetworkStatesHandler.Status.SUCCESS ->{
                            it.data?.result?.map { result ->
                                //TODO : Set Up Views With Data
                                Picasso.get().load(icon).fit().into(binding.teamicon)
                                binding.playername.text  = result.playerName
                                binding.playernumber.text = result.playerNumber
                                binding.playercountry.text = result.playerCountry
                                binding.playertype.text = result.playerType
                                binding.playerage.text = result.playerAge
                                binding.playermatchplayed.text = result.playerMatchPlayed
                                binding.playergoals.text = result.playerGoals
                                binding.playeryellowcard.text = result.playerYellowCards
                                binding.playerredcard.text = result.playerRedCards

                            }
                        }
                        NetworkStatesHandler.Status.ERROR ->{
                        }
                    }
                })
            }
        } else {
            val arguments = arguments
            arguments?.let {

                val playerName = it.getString("playerName")
                val playerNumber = it.getString("playerNumber")
                val playerCountry = it.getString("playerCountry")
                val playerType = it.getString("playerType")
                val playerAge = it.getString("playerAge")
                val playerMatchPlayed = it.getString("playerMatchPlayed")
                val playerGoals = it.getString("playerGoals")
                val playerYellowCard = it.getString("playerYellowCard")
                val playerRedCard = it.getString("playerRedCard")

                binding.playername.text  = playerName
                binding.playernumber.text = playerNumber
                binding.playercountry.text = playerCountry
                binding.playertype.text = playerType
                binding.playerage.text = playerAge
                binding.playermatchplayed.text = playerMatchPlayed
                binding.playergoals.text = playerGoals
                binding.playeryellowcard.text = playerYellowCard
                binding.playerredcard.text = playerRedCard

            }
        }
    }
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Constants.dialogCounter = 0
    }
}

