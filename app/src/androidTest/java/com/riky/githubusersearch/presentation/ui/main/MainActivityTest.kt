package com.riky.githubusersearch.presentation.ui.main

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.riky.githubusersearch.R
import com.riky.githubusersearch.fake.FakeUserRepository
import com.riky.githubusersearch.fake.user
import com.riky.githubusersearch.fake.userDetail
import com.riky.githubusersearch.presentation.ui.detail.DetailUserActivity
import com.riky.githubusersearch.presentation.ui.main.adapter.UserAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.core.IsNot.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MainActivityTest {

    @get:Rule val hiltRule = HiltAndroidRule(this)

    @Inject lateinit var fakeRepo: FakeUserRepository

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun showsWelcomeMessageOnLaunch() {
        fakeRepo.searchResult = emptyList()

        launch(MainActivity::class.java)

        // Root include visible
        onView(withId(R.id.item_message)).check(matches(isDisplayed()))
        // Title/desc inside include (avoid clashing with top @id/title)
        onView(allOf(withId(R.id.title), isDescendantOfA(withId(R.id.item_message))))
            .check(matches(withText(R.string.welcome_title)))
        onView(allOf(withId(R.id.desc), isDescendantOfA(withId(R.id.item_message))))
            .check(matches(withText(R.string.welcome_desc)))

        // Loading hidden by default
        onView(withId(R.id.loading)).check(matches(not(isDisplayed())))
    }

    @Test
    fun searchShowsResultsInRecyclerView() {
        fakeRepo.searchResult = listOf(
            user(1, "rikyaf"),
            user(2, "joni")
        )

        launch(MainActivity::class.java)

        onView(withId(R.id.et_search)).perform(typeText("riky"))
        closeSoftKeyboard()
        onView(withId(R.id.btn_search)).perform(click())

        // Message page should be hidden by submit list (overlapped in FrameLayout)
        onView(withId(R.id.item_message)).check(matches(not(isDisplayed())))

        // RecyclerView shows results
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))
        onView(withText("rikyaf")).check(matches(isDisplayed()))
        onView(withText("joni")).check(matches(isDisplayed()))
    }

    @Test
    fun searchEmptyShowsNoUsersMessage() {
        fakeRepo.searchResult = emptyList()

        launch(MainActivity::class.java)

        onView(withId(R.id.et_search)).perform(typeText("noresult"))
        closeSoftKeyboard()
        onView(withId(R.id.btn_search)).perform(click())

        onView(withId(R.id.item_message)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.title), isDescendantOfA(withId(R.id.item_message))))
            .check(matches(withText(R.string.no_users_found)))
        onView(allOf(withId(R.id.desc), isDescendantOfA(withId(R.id.item_message))))
            .check(matches(withText(R.string.no_users_found_desc)))
    }

    @Test
    fun clickListItemNavigatesToDetail() {
        // Given
        fakeRepo.searchResult = listOf(user(1, "rikyaf"))
        fakeRepo.detail = userDetail("rikyaf")

        launch(MainActivity::class.java)

        // Perform search
        onView(withId(R.id.et_search)).perform(typeText("octo"))
        closeSoftKeyboard()
        onView(withId(R.id.btn_search)).perform(click())

        // Start capturing intents
        Intents.init()

        // Click first item
        onView(withId(R.id.rv_users)).perform(
            RecyclerViewActions.actionOnItemAtPosition<UserAdapter.UserViewHolder>(0, click())
        )

        // Verify target Activity & EXTRA
        Intents.intended(
            allOf(
                hasComponent(DetailUserActivity::class.java.name),
                hasExtra(DetailUserActivity.EXTRA_USERNAME, "rikyaf")
            )
        )

        Intents.release()
    }
}