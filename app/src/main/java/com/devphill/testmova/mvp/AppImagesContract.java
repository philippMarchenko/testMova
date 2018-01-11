package com.devphill.testmova.mvp;


import com.devphill.testmova.base.MvpModel;
import com.devphill.testmova.base.MvpPresenter;
import com.devphill.testmova.base.MvpView;
import com.devphill.testmova.model_data.HistoryItem;
import com.devphill.testmova.model_data.image_model.ImagesModel;

import java.util.List;

import io.reactivex.Observable;

public interface AppImagesContract {

    interface View extends MvpView {

        /**
         * Выводит на экран елемент истории
        * @param imagesModel обьект, принятый с сервера с масивом картинок и информацией
        * @param phrase слово,которое вводил пользователь*/
        void showListImages(ImagesModel imagesModel,String phrase);
        /**
         * Прячем прогресбар
         */
        void hideDownloadMode();
        /**
         * Покажем сообщение пользователю
         */
        void showMessage(int messageResId);

    }

    interface Presenter extends MvpPresenter<View> {
        /**
         * Выводит на экран елемент истории
         * @param text запрос на получение списка деклараций у презентера
        */
        void getImages(String text);
        /**
         * Запрос на получение истории у презентера
         *
         * @return список элементов истории
         */
        List<HistoryItem> getDataFromDB();
        /**
         * Удаляет элемент из БД
         * @param ms значение милисекунд для идентификации элемента
         */
        void deleteItemFromRealm(long ms);


    }

    interface Model extends MvpModel {
        /**
         * Запрос на получение истории у презентера
         * @return обьект, принятый с сервера с масивом картинок и информацией
         * @param phrase слово,которое вводил пользователь
         */
        Observable<ImagesModel> getImages(String phrase);        //запрос на получение списка деклараций у модели
        /**
         * Сохраняет элемент исории в БД
         * @param historyItem элемент исории
         */
        void saveDataIndDB(HistoryItem historyItem);
        /**
         * Запрос на получение истории с БД
         * @return список элементов истории
         */
        List<HistoryItem> getDataFromDB();
        /**
         * Удаляет элемент из БД
         * @param ms значение милисекунд для идентификации элемента
         */
        void deleteItemFromRealm(long ms);

    }

}
