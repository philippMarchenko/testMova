package com.devphill.testmova.dagger;

import android.content.Context;


import com.devphill.testmova.dagger.module.model.ServerAPIModule;
import com.devphill.testmova.dagger.module.presenter.AppImagesPresenterModule;
import com.devphill.testmova.internet.ServerAPI;
import com.devphill.testmova.mvp.model.AppImagesModel;
import com.devphill.testmova.mvp.presenter.AppImagesPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ServerAPIModule.class,
                      AppImagesPresenterModule.class,
                      ContextModule.class})

public interface AppComponent {

    Context getContext();

    ServerAPI getServerAPI();

    AppImagesModel getAppImagesModel();

    AppImagesPresenter getAppImagesPresenter();


}