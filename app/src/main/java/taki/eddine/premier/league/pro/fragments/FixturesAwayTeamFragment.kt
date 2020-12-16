package taki.eddine.premier.league.pro.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import taki.eddine.premier.league.pro.mvvm.ShareViewModel
import taki.eddine.premier.league.pro.R
import taki.eddine.premier.league.pro.databinding.FixturesawaylayoutBinding

class FixturesAwayTeamFragment : Fragment() {

    private lateinit var binding: FixturesawaylayoutBinding
    private lateinit var shareViewModel: ShareViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FixturesawaylayoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shareViewModel = ViewModelProvider(requireActivity())[ShareViewModel::class.java]
        shareViewModel.awayDetailsLiveData.observe(viewLifecycleOwner, Observer {

            it["awaygoalsdetails"]?.let { it1 -> setUpFirstStepper(it1) }
            it["awayyellowcards"]?.let { it1 -> setUpSecondStepper(it1) }
            it["awayredcards"]?.let { it1 -> setUpThirdStepper(it1) }

        })

    }

    private fun setUpFirstStepper(goals : String){
        var gameGoals  = goals
        if(gameGoals.isEmpty()){
            gameGoals = "N/A"
        }
        val  data   = gameGoals.split(";")
        val list  = ArrayList<String>(data)
        for(i in list.indices){
            if(data[i].isEmpty()){
                list.removeAt(i)
            }
        }
        val backToList = list.toList()
        binding.fixturesAwayStepper1.setStepViewTexts(backToList)
                .reverseDraw(false)
                .setStepViewComplectedTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_football))
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_football))
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_football))


    }
    private fun setUpSecondStepper(yellowCard : String ){
        var gameYellowCard = yellowCard
        if(gameYellowCard.isEmpty()){
            gameYellowCard = "N/A"
        }
        val  data = gameYellowCard.split(";")
        binding.fixturesAwayStepper2.setStepViewTexts(data)
                .reverseDraw(false)
                .setStepViewComplectedTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_yellowcard))
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_yellowcard))
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_yellowcard))

    }
    private fun setUpThirdStepper(redCards : String ){
        var gameRedCards = redCards
        if(gameRedCards.isEmpty()){
            gameRedCards = "N/A"
        }
        val  data = gameRedCards.split(";")
        binding.fixturesAwayStepper3.setStepViewTexts(data)
                .reverseDraw(false)
                .setStepViewComplectedTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_redcircle))
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_redcircle))
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_redcircle))

    }


}