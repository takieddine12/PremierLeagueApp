package taki.eddine.premier.league.pro.ui.fragments

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
import taki.eddine.premier.league.pro.databinding.FixturehomelayoutBinding


class FixtureHomeTeamFragment : Fragment() {

    private lateinit var binding : FixturehomelayoutBinding
    private lateinit var shareViewModel: ShareViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FixturehomelayoutBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        shareViewModel = ViewModelProvider(requireActivity())[ShareViewModel::class.java]
        shareViewModel.homeDetailsLiveData.observe(viewLifecycleOwner, Observer {

            it["homegoalsdetails"]?.let { it1 -> setUpFirstStepper(it1) }
            it["homeyellowcards"]?.let { it1 -> setUpSecondStepper(it1) }
            it["homeredcards"]?.let { it1 -> setupThirdStepper(it1) }

        })
       }

    private fun setUpFirstStepper(goals : String){
        var gameGoals = goals
        if(gameGoals.isEmpty()){
            gameGoals = "N/A"
        }

        val  data1 = gameGoals.split(';')
        val list  = ArrayList<String>(data1)
        for(i in 0 until list.size){
            if(list[i].isEmpty()){
                list.removeAt(i)
            }
        }
        val  backToList = list.toList()
        binding.fixturesHomeStepper1.setStepViewTexts(backToList)
            .reverseDraw(false)
            .setStepViewComplectedTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
            .setStepViewUnComplectedTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
            .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_football))
            .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_football))
            .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_football))

    }
    private fun setUpSecondStepper(yellowCard: String){
        var gameYellowCard = yellowCard
        if(gameYellowCard.isEmpty()){
            gameYellowCard = "N/A"
        }

        val  data2 = gameYellowCard.split(";")

        binding.fixturesHomeStepper2.setStepViewTexts(data2)
            .reverseDraw(false)
            .setStepViewComplectedTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
            .setStepViewUnComplectedTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
            .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_yellowcard))
            .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_yellowcard))
            .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_yellowcard))
    }
    private fun setupThirdStepper(redCards: String){
        var gameRedCard = redCards
        if(gameRedCard.isEmpty()){
            gameRedCard = "N/A"
        }
        val  data3 = gameRedCard.split(";")
        binding.fixturesHomeStepper3.setStepViewTexts(data3)
            .reverseDraw(false)
            .setStepViewComplectedTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
            .setStepViewUnComplectedTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
            .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_redcircle))
            .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_redcircle))
            .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_redcircle))
    }
}