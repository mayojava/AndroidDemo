package com.mytaxi.android_demo.activities;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mytaxi.android_demo.R;
import com.mytaxi.android_demo.TestApplication;
import com.mytaxi.android_demo.TestUtils;
import com.mytaxi.android_demo.dependencies.component.TestAppComponent;
import com.mytaxi.android_demo.models.User;
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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class DriverProfileActivityTest {
    @Inject
    HttpClient httpClient;
    @Inject
    SharedPrefStorage sharedPrefStorage;

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule
            = new IntentsTestRule<>(MainActivity.class, true, false);

    private final String salt = "randomsalt";
    private final User user = new User(
            "whiteelephant261", salt, TestUtils.calculateSHA256("video1", salt));

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
    public void shouldLaunchPhoneDialer_whenPhoneIconIsClicked() {
        showDriversProfile();

        onView(withId(R.id.fab)).perform(click());
        intended(hasAction(Intent.ACTION_DIAL));
    }

    private void showDriversProfile() {
        final ArgumentCaptor<HttpClient.DriverCallback> driverCallbackArgumentCaptor =
                ArgumentCaptor.forClass(HttpClient.DriverCallback.class);

        intentsTestRule.launchActivity(null);
        verify(httpClient).fetchDrivers(driverCallbackArgumentCaptor.capture());

        final HttpClient.DriverCallback callback = driverCallbackArgumentCaptor.getValue();
        callback.setDrivers(TestUtils.getDversList());
        callback.run();

        onView(withId(R.id.textSearch)).perform(typeText("sa"));

        onView(withText("Sarah Friedrich"))
                .inRoot(
                        withDecorView(not(is(intentsTestRule.getActivity().getWindow().getDecorView()))))
                .perform(click());

    }
}