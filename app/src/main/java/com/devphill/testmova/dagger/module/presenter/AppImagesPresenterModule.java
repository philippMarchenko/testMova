package com.devphill.testmova.dagger.module.presenter;

import com.devphill.testmova.mvp.model.AppImagesModel;
import com.devphill.testmova.mvp.presenter.AppImagesPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppImagesPresenterModule {

    @Provides
    @Singleton
    AppImagesPresenter provideAppImagesPresenter() {
        return new AppImagesPresenter();

    }


    @Provides
    @Singleton
    AppImagesModel provideAppDeclarationsModel() {
        return new AppImagesModel();

    }
}
