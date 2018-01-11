package com.devphill.testmova.dagger;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static AppComponent getComponent() {
        return component;
    }


    public static void createComponent(Activity activity, Context context){

        component = DaggerAppComponent.builder()
                .contextModule(new ContextModule(context))
                .build();
    }
}