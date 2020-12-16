package taki.eddine.premier.league.pro.TestingUIS.TestingDaos.MainActivityTesting

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import taki.eddine.premier.league.pro.appui.MainActivity
import taki.eddine.premier.league.pro.fragments.FixturesFragment
import taki.eddine.premier.league.pro.R

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class MainActivityTest{

    private  lateinit  var toolbarTitle : String
    @get:Rule
    var activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun InitialSetup(){
         toolbarTitle = "Fixtures"
    }

    @After
    fun teardown(){

    }

    @Test
    fun testToolbar(){
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar)).check(matches(hasDescendant(withText(toolbarTitle))))
    }

    @Test
    fun testBuddleBar(){
        onView(withId(R.id.bubbleTabBar)).check(matches(isDisplayed()))
        onView(withText("Match"))
        onView(withText("Rank"))
        onView(withText("Live"))

    }

    @Test
    fun TestNavigationComponenet(){
        var navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.nav_graph)

        //Creating Our Fragment Sceneraio

        var fragmentScenario = launchFragmentInContainer<FixturesFragment>()
        fragmentScenario.onFragment {
            Navigation.setViewNavController(it.requireView(),navController)
        }

        onView(withId(R.id.fixtures)).perform(ViewActions.click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.fixtures)
    }




}