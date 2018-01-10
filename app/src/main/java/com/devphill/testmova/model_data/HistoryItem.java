package com.devphill.testmova.model_data;


import android.graphics.Bitmap;

import io.realm.RealmObject;

public class HistoryItem extends RealmObject {

   // private Bitmap bitmap;
    private String phrase;
    private String image_url;

    public HistoryItem(String phrase, String image_url) {
      //  this.bitmap = bitmap;
        this.phrase = phrase;
        this.image_url = image_url;

    }

    public HistoryItem() {
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
}
