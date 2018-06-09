package com.mytaxi.android_demo.dependencies.module;

import com.mytaxi.android_demo.utils.PermissionHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class MockedPermissionModule  {

    @Provides
    @Singleton
    PermissionHelper providePermissionHelper() {
        return mock(PermissionHelper.class);
    }
}
