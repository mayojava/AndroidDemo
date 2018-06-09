package com.mytaxi.android_demo.activities;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mytaxi.android_demo.R;
import com.mytaxi.android_demo.TestApplication;
import com.mytaxi.android_demo.TestUtils;
import com.mytaxi.android_demo.dependencies.component.TestAppComponent;
import com.mytaxi.android_demo.models.User;
import com.mytaxi.android_demo.utils.PermissionHelper;
import com.mytaxi.android_demo.utils.network.HttpClient;
import com.mytaxi.android_demo.utils.storage.SharedPrefStorage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Inject
    HttpClient httpClient;
    @Inject
    PermissionHelper permissionHelper;
    @Inject
    SharedPrefStorage sharedPrefStorage;

    @Rule
    public final ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class, true, false);

    private final String salt = "randomsalt";
    private final User user = new User(
            "whiteelephant261", salt, TestUtils.calculateSHA256("video1", salt));

    private HttpClient.DriverCallback driverCallback = null;

    @Before
    public void setup() {
        final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        final TestApplication app =
                (TestApplication) instrumentation.getTargetContext().getApplicationContext();
        final TestAppComponent appComponent = (TestAppComponent)app.getAppComponent();
        appComponent.inject(this);

        when(sharedPrefStorage.loadUser()).thenReturn(user);
    }

    @Test
    public void shouldSeeExpectedNameInSearchResult_whenSearchKeywordIsRight() {
        launchActivity();

        onView(withId(R.id.textSearch)).perform(typeText("sa"), closeSoftKeyboard());

        onView(withText("Sarah Friedrich"))
                .inRoot(
                        withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotSeeSearchResult_whenSearchTermIsWrong() {
        launchActivity();

        onView(withId(R.id.textSearch)).perform(typeText("aa"));

        onView(withText("Sarah Friedrich")).check(doesNotExist());

    }

    @Test
    public void clickingOnSearchResult_shouldShowProfilePage() {
        launchActivity();

        onView(withId(R.id.textSearch)).perform(typeText("sa"), closeSoftKeyboard());

        onView(withText("Sarah Friedrich"))
                .inRoot(
                        withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .perform(click());

        onView(withId(R.id.card_view_profile)).check(matches(isDisplayed()));
    }

    private void launchActivity() {
        reset(httpClient);
        final ArgumentCaptor<HttpClient.DriverCallback> driverCallbackArgumentCaptor =
                ArgumentCaptor.forClass(HttpClient.DriverCallback.class);

        activityTestRule.launchActivity(null);
        verify(httpClient).fetchDrivers(driverCallbackArgumentCaptor.capture());
        driverCallback = driverCallbackArgumentCaptor.getValue();
        driverCallback.setDrivers(TestUtils.getDversList());
        driverCallback.run();
        reset(httpClient);
    }
}