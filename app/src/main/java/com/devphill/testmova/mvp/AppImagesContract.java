package com.devphill.testmova.mvp;


import com.devphill.testmova.base.MvpModel;
import com.devphill.testmova.base.MvpPresenter;
import com.devphill.testmova.base.MvpView;
import com.devphill.testmova.model_data.HistoryItem;
import com.devphill.testmova.model_data.ImagesModel;

import java.util.List;

import io.reactivex.Observable;

public interface AppImagesContract {

    interface View extends MvpView {

        void showListImages(ImagesModel imagesModel,String phrase);              //покажем список деклараций

        void hideDownloadMode();                                   //спрячем прогресбар

        void showMessage(int messageResId);                        //покажем тоаст

    }

    interface Presenter extends MvpPresenter<View> {

        void getImages(String text);                             //запрос на получение списка деклараций у презентера

        List<HistoryItem> getData();                             //запрос на получение истории у презентера

    }

    interface Model extends MvpModel {

        Observable<ImagesModel> getImages(String phrase);        //запрос на получение списка деклараций у модели

        void saveDataIndDB(HistoryItem historyItem);

        List<HistoryItem> getDataFromDB();

    }

}
