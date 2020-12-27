package taki.eddine.premier.league.pro.ui.appui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import taki.eddine.premier.league.pro.mvvm.ShareViewModel
import taki.eddine.premier.league.pro.R
import taki.eddine.premier.league.pro.adapters.FixtureViewPagerAdapter
import taki.eddine.premier.league.pro.databinding.ActivityEventDetailsBinding
import taki.eddine.premier.league.pro.models.AwayLogoModel
import taki.eddine.premier.league.pro.models.Event
import taki.eddine.premier.league.pro.models.TeamXX

class EventDetailsActivity : AppCompatActivity() {

    lateinit var binding : ActivityEventDetailsBinding
    private val shareViewModel : ShareViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val intent = intent
        intent?.let {

            val event = it.getParcelableExtra<Event>("event")
            val teamXX = it.getParcelableExtra<TeamXX>("teamxx")
            val awayLogoModel = it.getParcelableExtra<AwayLogoModel>("awaylogomodel")


            // TODO : Passing HomeTeam Details
            val homeMap = hashMapOf<String,String>()
            homeMap["homegoalsdetails"] = event?.strHomeGoalDetails ?: "No Data"
            homeMap["homeyellowcards"] = event?.strHomeYellowCards ?: "No Data"
            homeMap["homeredcards"] = event?.strHomeRedCards ?: "No Data"
            shareViewModel.sendHomeDetails(homeMap)


            // TODO : Passing AwayTeam Details
            val awayMap = hashMapOf<String,String>()
            awayMap["awaygoalsdetails"] = event?.strAwayGoalDetails ?: "No Data"
            awayMap["awayyellowcards"] = event?.strAwayYellowCards ?: "No Data"
            awayMap["awayredcards"] = event?.strAwayRedCards ?: "No Data"
            shareViewModel.sendAwayDetails(awayMap)


            // TODO : Setting Extra Data To The Ui
            Picasso.get().load(teamXX?.HomestrTeamBadge).error(R.drawable.ic_baseline_sports).fit().into(binding.eventhomelogo)
            Picasso.get().load(awayLogoModel?.AwaystrTeamBadge).error(R.drawable.ic_baseline_sports).fit().into(binding.eventawaylogo)
            binding.eventdetailsround.text = event?.intRound ?: "0"

            setUpTabLayout(event?.strHomeTeam!!, event.strAwayTeam!!)

        }

    }

    private fun setUpTabLayout(homeTeam : String, awayTeam : String){
        val viewPagerAdapter = FixtureViewPagerAdapter(this)
        binding.fixturesviewpager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.fixturetablayout,binding.fixturesviewpager) { tab, position ->
            when (position) {
                0 -> tab.text = homeTeam
                1 -> tab.text = awayTeam
            }
        }.attach()
    }
}
