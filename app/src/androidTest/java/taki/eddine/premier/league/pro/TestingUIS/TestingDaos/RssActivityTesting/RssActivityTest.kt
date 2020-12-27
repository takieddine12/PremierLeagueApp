package taki.eddine.premier.league.pro.TestingUIS.TestingDaos.RssActivityTesting

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import taki.eddine.premier.league.pro.ui.appui.RssActivity
import taki.eddine.premier.league.pro.R

@RunWith(AndroidJUnit4ClassRunner::class)
@SmallTest
class RssActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(RssActivity::class.java)

    @Before
    fun InitialSetUp(){


    }

    @After
    fun teardown(){

    }

    @Test
    fun CheckLayoutComponenets(){
        //check layout
        onView(withId(R.id.rsslayout)).check(matches(withText("rsslayout")))
        onView(withId(R.id.rsslayout)).check(matches(isDisplayed()))

        // Check WebView
        onView(withId(R.id.webview)).check(matches(isDisplayed()))
    }
}