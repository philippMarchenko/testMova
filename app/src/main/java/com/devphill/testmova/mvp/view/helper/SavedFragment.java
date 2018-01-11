package com.devphill.testmova.mvp.view.helper;

import android.os.Bundle;
import android.support.v4.app.Fragment;


import com.devphill.testmova.model_data.image_model.Image;

import java.util.ArrayList;
import java.util.List;

public class SavedFragment extends Fragment {

    private List<Image> imageList = new ArrayList<>();


    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setRetainInstance(true);
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setDeclarations(List<Image> declarationsList) {
        this.imageList = declarationsList;
    }
}