package com.devphill.testmova.dagger;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ContextModule {

   private final Context context;

   public ContextModule(Context context){

       this.context = context;
   }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }
}
