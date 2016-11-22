/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.chaiy.memento.backend;


import com.chaiy.memento.backend.bean.MementoBean;
import com.chaiy.memento.backend.bean.sections.weather.LatLong;
import com.chaiy.memento.backend.workers.MoviesWorkerThread;
import com.chaiy.memento.backend.workers.NewsWorkerThread;
import com.chaiy.memento.backend.workers.WeatherWorkerThread;
import com.chaiy.nytimes.NYTimesApi;
import com.chaiy.nytimes.requests.ArticleSearchRequest;
import com.chaiy.nytimes.response.ArticleSearchResponse;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.ThreadManager;

import java.util.concurrent.CountDownLatch;

import javax.inject.Named;


/**
 * An endpoint class we are exposing
 */
@Api(
        name = "mementoApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.memento.chaiy.com",
                ownerName = "backend.memento.chaiy.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        ArticleSearchResponse rep = NYTimesApi.init("244944bfb2a34a8c80c44262134db1e9")
                .getArticles(new ArticleSearchRequest.Builder().beginDate("20051105").endDate("20051105").build());

        return response;
    }


    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "getEvents")
    public MementoBean getEvents(@Named("date") String date, @Named("latitude") String latitude, @Named("longitude") String longitude) {

        MementoBean mementoBean = new MementoBean();
        final CountDownLatch latch = new CountDownLatch(3);

        Thread newsService = ThreadManager.createThreadForCurrentRequest(new NewsWorkerThread(latch, mementoBean, date));
        Thread weatherService = ThreadManager.createThreadForCurrentRequest(new WeatherWorkerThread(latch, mementoBean, date, new LatLong(latitude, longitude)));
        Thread moviesService = ThreadManager.createThreadForCurrentRequest(new MoviesWorkerThread(latch, mementoBean, date));

        newsService.start(); //separate thread will initialize CacheServiceweatherService.start(); //another thread for AlertService initialization
        weatherService.start();
        moviesService.start();

        try {
            latch.await();  //main thread is waiting on CountDownLatch to finish
            System.out.println("All services are up, Application is starting now");
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        return mementoBean;
    }

}
