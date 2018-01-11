package com.devphill.testmova.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.devphill.testmova.R;
import com.devphill.testmova.dagger.App;
import com.devphill.testmova.model_data.HistoryItem;
import com.devphill.testmova.model_data.image_model.Image;
import com.devphill.testmova.model_data.image_model.ImagesModel;
import com.devphill.testmova.mvp.AppImagesContract;
import com.devphill.testmova.base.PresenterBase;
import com.devphill.testmova.util.Util;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class AppImagesPresenter extends PresenterBase<AppImagesContract.View> implements AppImagesContract.Presenter {

    private String LOG_TAG = "AppImagesPresenter";

    private AppImagesContract.Model model;
    private Context mContext;

    public AppImagesPresenter() {

        mContext =  App.getComponent().getContext();
        model = App.getComponent().getAppImagesModel();
    }

    @Override
    public void viewIsReady() {
        Log.d(LOG_TAG,"viewIsReady ");

    }

    @Override
    public void getImages(String phrase) {
        Log.d(LOG_TAG,"onSearchConfirmed ");

        String netType = Util.getNetworkType(mContext);
        if(netType == null){
            getView().hideDownloadMode();
            getView().showMessage(R.string.no_internet);
        }
        else {

            model.getImages(phrase).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<ImagesModel>() {
                        @Override
                        public void onNext(ImagesModel imagesModel) {

                            if(imagesModel.getImages().size() > 0){
                                Log.d(LOG_TAG,"Найдено " + imagesModel.getResultCount() + " картинок по запросу " + phrase);
                                getView().showListImages(imagesModel,phrase);

                                HistoryItem historyItem = new HistoryItem(phrase,
                                        imagesModel.getImages().get(0).getDisplaySizes().get(0).getUri(),
                                        imagesModel.getImages().get(0).getCaption(),
                                        System.currentTimeMillis());

                                model.saveDataIndDB(historyItem);
                            }
                            else{
                                getView().showMessage(R.string.no_result);
                                getView().hideDownloadMode();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(LOG_TAG,"onError getDeclarations " + e.getMessage());
                            getView().hideDownloadMode();


                        }

                        @Override
                        public void onComplete() {
                            Log.d(LOG_TAG,"onComplete getDeclarations ");
                        }
                    });
        }
    }

    @Override
    public void deleteItemFromRealm(long ms) {
        model.deleteItemFromRealm(ms);
    }

    @Override
    public List<HistoryItem> getDataFromDB() {

        return model.getDataFromDB();
    }

}
