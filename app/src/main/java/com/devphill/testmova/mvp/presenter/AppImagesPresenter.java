package com.devphill.testmova.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.devphill.testmova.R;
import com.devphill.testmova.dagger.App;
import com.devphill.testmova.model_data.HistoryItem;
import com.devphill.testmova.model_data.Image;
import com.devphill.testmova.model_data.ImagesModel;
import com.devphill.testmova.mvp.AppImagesContract;
import com.devphill.testmova.base.PresenterBase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class AppImagesPresenter extends PresenterBase<AppImagesContract.View> implements AppImagesContract.Presenter {

    private String LOG_TAG = "AppImagesPresenter";

    private AppImagesContract.Model model;
    private List<Image> imagesList = new ArrayList<>();
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

        String netType = getNetworkType(mContext);
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
                                        imagesModel.getImages().get(0).getDisplaySizes().get(0).getUri());

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

                        }

                        @Override
                        public void onComplete() {
                            Log.d(LOG_TAG,"onComplete getDeclarations ");
                        }
                    });
        }
    }

    @Override
    public List<HistoryItem> getData() {

        return model.getDataFromDB();
    }

    private String getNetworkType(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            return activeNetwork.getTypeName();
        }
        return null;
    }

}
