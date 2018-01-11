package com.devphill.testmova.model_data;


import io.realm.RealmObject;

public class HistoryItem extends RealmObject {

    private String phrase;
    private String image_url;
    private String caption;
    private long curent_ms;

    /**
     * Выводит на экран елемент истории
     * @param phrase слово запроса
     * @param image_url ссылка на картинку
     * @param caption детально о картинке
     * @param curent_ms время , когда был запрос*/
    public HistoryItem(String phrase, String image_url, String caption,long curent_ms) {
        this.phrase = phrase;
        this.image_url = image_url;
        this.curent_ms = curent_ms;
        this.caption = caption;
    }

    public HistoryItem() {
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }


    public long getCurent_ms() {
        return curent_ms;
    }

    public void setCurent_ms(long curent_ms) {
        this.curent_ms = curent_ms;
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
