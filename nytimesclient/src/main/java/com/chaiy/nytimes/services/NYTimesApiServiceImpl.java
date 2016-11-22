package com.chaiy.nytimes.services;


import com.chaiy.nytimes.services.clients.httpclient.SimpleHttpServiceImpl;

/**
 * Created by Chaiy on 11/5/2016.
 */

public class NYTimesApiServiceImpl {


    public static NYTimesAipServices fetchServiceClient(String apiKey) {

        return new SimpleHttpServiceImpl(apiKey);

    }

}
