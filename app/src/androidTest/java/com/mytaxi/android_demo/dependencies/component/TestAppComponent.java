package com.mytaxi.android_demo.dependencies.component;

import com.mytaxi.android_demo.activities.AuthenticationActivityTest;
import com.mytaxi.android_demo.activities.DriverProfileActivityTest;
import com.mytaxi.android_demo.activities.MainActivityTest;
import com.mytaxi.android_demo.dependencies.module.MockNetworkModule;
import com.mytaxi.android_demo.dependencies.module.MockedPermissionModule;
import com.mytaxi.android_demo.dependencies.module.MockedSharedPrefStorageModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        MockNetworkModule.class,
        MockedSharedPrefStorageModule.class,
        MockedPermissionModule.class
})
public interface TestAppComponent extends AppComponent {
    void inject(AuthenticationActivityTest activity);
    void inject(MainActivityTest mainActivityTest);
    void inject(DriverProfileActivityTest driverProfileActivityTest);
}
