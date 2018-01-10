package com.devphill.testmova.dagger.module.view;


import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;


import com.devphill.testmova.model_data.Image;
import com.devphill.testmova.mvp.view.helper.SavedFragment;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module

public class ListImagesActivityModule {

    SavedFragment savedFragment;

    FragmentActivity fragmentActivity;

    public ListImagesActivityModule(Activity activity) {
        fragmentActivity = (FragmentActivity) activity;
    }


    @Provides
    @Singleton
    SavedFragment providesavedFragment() {
      //  Log.d("ListImagesActivityModule","DeclarationsListProvider");

         savedFragment = (SavedFragment) fragmentActivity.getSupportFragmentManager().findFragmentByTag("SAVE_FRAGMENT");


        if (savedFragment != null){
           //  Log.d("ListImagesActivityModule","savedFragment not null");

             return savedFragment;
         }

            else {
          //   Log.d("ListImagesActivityModule","savedFragment null");
             savedFragment = new SavedFragment();
             fragmentActivity.getSupportFragmentManager().beginTransaction()
                     .add(savedFragment, "SAVE_FRAGMENT")
                     .commit();

             return savedFragment;
         }
    }

    @Provides
    @Singleton
    ImagesListProvider provideImagesList(SavedFragment savedFragment) {

        return new ImagesListProvider(savedFragment);
    }

    public class ImagesListProvider {

        SavedFragment mSavedFragment;

        public ImagesListProvider(SavedFragment savedFragment){

            mSavedFragment = savedFragment;
        }

        public List<Image> getImageList() {

        //    Log.d("ListImagesActivityModule","DeclarationsListProvider declarationsList");
            return mSavedFragment.getImageList();
        }

        public void setImagesList(List<Image> declarationsList) {
           /* savedFragment.setDeclarations(declarationsList);*/
            mSavedFragment.setDeclarations(declarationsList);
        }
    }
}
