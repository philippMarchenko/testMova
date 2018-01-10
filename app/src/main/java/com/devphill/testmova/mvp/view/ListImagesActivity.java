package com.devphill.testmova.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.devphill.testmova.model_data.Image;
import com.devphill.testmova.model_data.ImagesModel;
import com.devphill.testmova.mvp.AppImagesContract;
import com.devphill.testmova.mvp.presenter.AppImagesPresenter;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.io.IOException;
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

    private ImagesAdapter imagesAdapter;
    private List<HistoryItem> historyItems = new ArrayList<>();
    private ArrayList<String> suggestionList = new ArrayList<String>();

    private String LOG_TAG = "ListDeclarationsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_TAG,"onCreate ");


        ButterKnife.bind(this);

        App.createComponent(this,getApplicationContext());

        appImagesPresenter = App.getComponent().getAppImagesPresenter();            //подгружаем презентер
       // imageList = App.getComponent().getImagesListProvider().getImageList();      //загружаем данные из фрагмента для сохранения

        progressBar.setVisibility(View.INVISIBLE);

        materialSearchBar.setOnSearchActionListener(this);
     //   recyclerView = (RecyclerView) findViewById(R.id.recycler_view_declarations);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getBaseContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        imagesAdapter = new ImagesAdapter(getBaseContext(),this,historyItems);
        recyclerView.setAdapter(imagesAdapter);

        // attach view to presenter
        appImagesPresenter.attachView(this);

        // view is ready to work
        appImagesPresenter.viewIsReady();


        historyItems.addAll(appImagesPresenter.getData());
        imagesAdapter.notifyDataSetChanged();

    }


    @Override
    public void showListImages(ImagesModel imagesModel,String phrase) {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

        HistoryItem historyItem = new HistoryItem(phrase,
                                                  imagesModel.getImages().get(0).getDisplaySizes().get(0).getUri());

        historyItems.add(0,historyItem);
        imagesAdapter.notifyItemInserted(0);
        recyclerView.smoothScrollToPosition(0);
        materialSearchBar.disableSearch();

        App.getComponent().getImagesListProvider().setImagesList(imagesModel.getImages());
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


    @Override
    public void onDestroy() {
        super.onDestroy();

  /*      SharedPreferences prefs = getSharedPreferences("suggestionList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            editor.putString("LIST", ObjectSerializer.serialize(suggestionList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();

        Log.i(LOG_TAG, "MainFragment onDestroy");*/



    }
}

