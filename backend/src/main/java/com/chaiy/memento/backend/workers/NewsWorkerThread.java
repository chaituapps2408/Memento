package com.chaiy.memento.backend.workers;

import com.chaiy.memento.backend.bean.MementoBean;
import com.chaiy.memento.backend.bean.sections.news.NewsSectionBean;
import com.chaiy.memento.backend.utility.MementoBeanGeneratorUtility;
import com.chaiy.nytimes.NYTimesApi;
import com.chaiy.nytimes.requests.ArticleSearchRequest;
import com.chaiy.nytimes.response.ArticleSearchResponse;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Chaiy on 11/13/2016.
 */

public class NewsWorkerThread implements Runnable {

    private static final String API_KEY = "244944bfb2a34a8c80c44262134db1e9";
    private final CountDownLatch latch;
    private final MementoBean mementoBean;
    private final String date;

    public NewsWorkerThread(CountDownLatch latch, MementoBean mementoBean, String date) {
        this.latch = latch;
        this.mementoBean = mementoBean;
        this.date = date;
    }

    @Override
    public void run() {

        System.out.println("Date : NYTimes : "+ getFormatedDate(date));
        ArticleSearchResponse rep = NYTimesApi.init(API_KEY)
                .getArticles(new ArticleSearchRequest.Builder()
                        .beginDate(getFormatedDate(date))
                        .endDate(getFormatedDate(date))
                        .build());

        NewsSectionBean newsSectionBean = MementoBeanGeneratorUtility.generateNewsSectionBean(rep);
        if (newsSectionBean != null)
            mementoBean.addSection(MementoBeanGeneratorUtility.generateNewsSectionBean(rep));

        latch.countDown();
    }

    private String getFormatedDate(String date) {
        return date.replace("-", "");
    }
}
