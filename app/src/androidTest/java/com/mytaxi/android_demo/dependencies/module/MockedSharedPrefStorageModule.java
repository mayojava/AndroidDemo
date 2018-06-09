package com.mytaxi.android_demo.dependencies.module;

import com.mytaxi.android_demo.utils.storage.SharedPrefStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class MockedSharedPrefStorageModule {

    @Provides
    @Singleton
    SharedPrefStorage provideSharedPrefStorage() {
        return mock(SharedPrefStorage.class);
    }
}
