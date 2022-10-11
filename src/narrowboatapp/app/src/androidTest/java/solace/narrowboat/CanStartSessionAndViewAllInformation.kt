package solace.narrowboat


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CanStartSessionAndViewAllInformation {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_BACKGROUND_LOCATION"
        )

    @Test
    fun canStartSessionAndViewAllInformation() {
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_settings), withContentDescription("Settings"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val bottomNavigationItemView2 = onView(
            allOf(
                withId(R.id.navigation_journey), withContentDescription("Journeys"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(click())

        val materialButton = onView(
            allOf(
                withId(R.id.btnCreateJourney), withText("Create Journey"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.etJourneyName),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("Test"), closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(
                withId(R.id.btnCreateNewJourney), withText("Create Journey"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val bottomNavigationItemView3 = onView(
            allOf(
                withId(R.id.navigation_settings), withContentDescription("Settings"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView3.perform(click())

        val materialButton3 = onView(
            allOf(
                withId(R.id.btnViewBoats), withText("View boats"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val materialButton4 = onView(
            allOf(
                withId(R.id.btnCreateBoat), withText("Create new boat"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.etBoatName),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("Test"), closeSoftKeyboard())

        val materialButton5 = onView(
            allOf(
                withId(R.id.btnCreateNewBoat), withText("Create Boat"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    19
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())

        val appCompatImageButton = onView(
            allOf(
                withId(R.id.ibCloseBoatList),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayout4),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val bottomNavigationItemView4 = onView(
            allOf(
                withId(R.id.navigation_voyage), withContentDescription("Voyage"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView4.perform(click())

        val imageView = onView(
            allOf(
                withContentDescription("My Location"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        imageView.perform(click())

        val materialButton6 = onView(
            allOf(
                withId(R.id.btnStartVoyage), withText("Start Voyage"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton6.perform(click())

        val materialButton7 = onView(
            allOf(
                withId(R.id.btnSelectJourney), withText("Select Journey"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton7.perform(click())

        val materialButton8 = onView(
            allOf(
                withId(R.id.btnPauseVoyage), withText("Pause Voyage"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton8.perform(click())

        val materialButton9 = onView(
            allOf(
                withId(R.id.btnResumeVoyage), withText("Resume Voyage"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton9.perform(click())

        val materialButton10 = onView(
            allOf(
                withId(R.id.btnLogbookVoyage), withText("Open Logbook"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialButton10.perform(click())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.etCrew),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    1
                )
            )
        )
        appCompatEditText3.perform(scrollTo(), replaceText("Test"), closeSoftKeyboard())

        val materialButton11 = onView(
            allOf(
                withId(R.id.btnUpdateLogbook), withText("Update Logbook"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton11.perform(click())

        val materialButton12 = onView(
            allOf(
                withId(R.id.btnStopVoyage), withText("Stop Voyage"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton12.perform(click())

        val bottomNavigationItemView5 = onView(
            allOf(
                withId(R.id.navigation_journey), withContentDescription("Journeys"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView5.perform(click())

        val recyclerView = onView(
            allOf(
                withId(R.id.rv_journeys),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val materialButton13 = onView(
            allOf(
                withId(R.id.btnOpenLogbook), withText("Open Logbook"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cvEntry),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialButton13.perform(click())

        val appCompatImageButton2 = onView(
            allOf(
                withId(R.id.ibCloseLogbook),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayout2),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageButton2.perform(click())

        val materialButton14 = onView(
            allOf(
                withId(R.id.btnOpenMap), withText("Open Map"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cvEntry),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton14.perform(click())

        val appCompatImageButton3 = onView(
            allOf(
                withId(R.id.ibBackLoadedSession),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayout3),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageButton3.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
