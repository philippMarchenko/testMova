package com.devphill.testmova.mvp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.devphill.testmova.R;
import com.devphill.testmova.dagger.App;
import com.devphill.testmova.model_data.HistoryItem;
import com.devphill.testmova.model_data.image_model.ImagesModel;
import com.devphill.testmova.mvp.AppImagesContract;
import com.devphill.testmova.mvp.presenter.AppImagesPresenter;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListImagesActivity extends AppCompatActivity implements AppImagesContract.View,
                                                                     MaterialSearchBar.OnSearchActionListener{

    @BindView(R.id.searchBar)
    MaterialSearchBar materialSearchBar;

    @BindView(R.id.recycler_view_declarations)
    RecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @Inject
    AppImagesPresenter appImagesPresenter;

    private ImagesAdapter imagesAdapter;                            //адаптер списка
    private List<HistoryItem> historyItems = new ArrayList<>();     //локальный список элементов истории

    private String LOG_TAG = "ListDeclarationsActivity";

    private ImagesAdapter.ImagesListener imagesListener;            //слушатель событий из адаптера

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_TAG,"onCreate ");

        ButterKnife.bind(this);

        App.createComponent(this,getApplicationContext());

        appImagesPresenter = App.getComponent().getAppImagesPresenter();            //подгружаем презентер

        progressBar.setVisibility(View.INVISIBLE);

        materialSearchBar.setOnSearchActionListener(this);                          //этот клас реализует интерфейс поиска

        initImageListener();                                                        //инициализация слушателя клика по корзинке

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getBaseContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        imagesAdapter = new ImagesAdapter(getBaseContext(),historyItems,imagesListener);
        recyclerView.setAdapter(imagesAdapter);

        // attach view to presenter
        appImagesPresenter.attachView(this);

        // view is ready to work
        appImagesPresenter.viewIsReady();

        //достаем всю историю с БД и добавляем на экран
        historyItems.addAll(appImagesPresenter.getDataFromDB());
        imagesAdapter.notifyDataSetChanged();

    }

    private void initImageListener(){

        imagesListener = position -> {
            appImagesPresenter.deleteItemFromRealm(historyItems.get(position).getCurent_ms());  //удаляем из БД элемент
            historyItems.remove(position);                                                      //удаляем из списка на экране элемент
            imagesAdapter.notifyDataSetChanged();
        };
    }//инициализация слушателя клика по корзинке

    @Override
    public void showListImages(ImagesModel imagesModel,String phrase) {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

        //создаем новый элемент истории
        HistoryItem historyItem = new HistoryItem(phrase,
                                                  imagesModel.getImages().get(0).getDisplaySizes().get(0).getUri(),
                                                  imagesModel.getImages().get(0).getCaption(),
                                                  System.currentTimeMillis());

        historyItems.add(0,historyItem);            //обновим список на экране, добавив в начало элемент
        imagesAdapter.notifyItemInserted(0);
        recyclerView.smoothScrollToPosition(0);
        materialSearchBar.disableSearch();

    }

    @Override
    public void hideDownloadMode() {

        progressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void showMessage(int messageResId) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
        materialSearchBar.hideSuggestionsList();

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        Log.d(LOG_TAG,"onSearchConfirmed " + text.toString());

        progressBar.setVisibility(View.VISIBLE);                        //покажем прогресбар
        materialSearchBar.disableSearch();
        appImagesPresenter.getImages(text.toString());
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }

}

