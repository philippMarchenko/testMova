package com.devphill.testmova.mvp.model;

import android.content.Context;

import com.devphill.testmova.dagger.App;
import com.devphill.testmova.internet.ServerAPI;
import com.devphill.testmova.model_data.HistoryItem;
import com.devphill.testmova.model_data.image_model.ImagesModel;
import com.devphill.testmova.mvp.AppImagesContract;


import java.util.List;

import javax.inject.Inject;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class AppImagesModel implements AppImagesContract.Model {

    private String LOG_TAG = "AppImagesModel";

    private Context mContext;

    private Realm mRealm;


    @Inject
    ServerAPI serverAPI;

    public AppImagesModel(){

        mContext = App.getComponent().getContext();
        serverAPI = App.getComponent().getServerAPI();

    }

    @Override
    public Observable<ImagesModel> getImages(String phrase) {
        return serverAPI.getImages("id,title,thumb","best",phrase);
    }

    @Override
    public void saveDataIndDB(HistoryItem historyItem) {

        try {
            mRealm = Realm.getInstance(mContext);
            mRealm.beginTransaction();                         //начало транзакции Realm
            mRealm.copyToRealm(historyItem);                      //положим наш обьект в БД
            mRealm.commitTransaction();                         //подтвердим транзакцию
        }
        catch (Exception e){
        }
    }

    @Override
    public List<HistoryItem> getDataFromDB() {

        RealmResults<HistoryItem> realmResults = null;
        mRealm = Realm.getInstance(mContext);

        try {
            realmResults = mRealm.where(HistoryItem.class).findAllSorted("curent_ms", Sort.DESCENDING);
            realmResults.load();
        }
        catch (Exception e){
        }

        return realmResults;
    }

    @Override
    public void deleteItemFromRealm(long ms) {
        mRealm.beginTransaction();

        RealmResults<HistoryItem> rows= mRealm.where(HistoryItem.class).equalTo("curent_ms", ms).findAll();

        rows.clear();
        mRealm.commitTransaction();
    }


}