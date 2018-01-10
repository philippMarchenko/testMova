package com.devphill.testmova.internet;



import com.devphill.testmova.model_data.ImagesModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServerAPI {

    String BASE_URL = "https://api.gettyimages.com";

    @GET("/v3/search/images/")
    Observable<ImagesModel> getImages(@Query(value = "ﬁelds") String ﬁelds,
                                      @Query(value = "sort_order") String sort_order,
                                      @Query(value = "phrase") String phrase);




}