package com.mytaxi.android_demo;

import com.mytaxi.android_demo.dependencies.component.AppComponent;
import com.mytaxi.android_demo.dependencies.component.DaggerTestAppComponent;
import com.mytaxi.android_demo.dependencies.component.TestAppComponent;

public class TestApplication extends App {
    private TestAppComponent testAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        testAppComponent = DaggerTestAppComponent.builder()
                .build();
    }

    @Override
    public AppComponent getAppComponent() {
        return testAppComponent;
    }
}
