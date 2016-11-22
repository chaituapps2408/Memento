package com.chaiy.memento.backend.workers;

import com.chaiy.memento.backend.bean.MementoBean;
import com.chaiy.memento.backend.bean.sections.weather.LatLong;
import com.chaiy.memento.backend.utility.MementoBeanGeneratorUtility;
import com.github.dvdme.ForecastIOLib.FIODaily;
import com.github.dvdme.ForecastIOLib.ForecastIO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Chaiy on 11/13/2016.
 */

public class WeatherWorkerThread implements Runnable {

    private static final String API_KEY = "f54e7f0348bebe1c0aad92414fe37a0f";
    private final CountDownLatch latch;
    private final MementoBean mementoBean;
    private final String date;
    private final LatLong latLong;

    public WeatherWorkerThread(CountDownLatch latch, MementoBean mementoBean, String date, LatLong latLong) {
        this.latch = latch;
        this.mementoBean = mementoBean;
        this.date = date;
        this.latLong = latLong;
    }

    @Override
    public void run() {

        ForecastIO fio = new ForecastIO(API_KEY);
        fio.setUnits(ForecastIO.UNITS_SI);
        fio.setLang(ForecastIO.LANG_ENGLISH);
        fio.setExcludeURL("currently,hourly,minutely");
        System.out.println("Date : Weather : "+ getUnixTime(date));
        fio.setTime(getUnixTime(date));
        fio.getForecast(latLong.getLatitude(), latLong.getLongitude());
        System.out.println("Latitude: " + fio.getLatitude());
        System.out.println("Longitude: " + fio.getLongitude());
        System.out.println("Timezone: " + fio.getTimezone());
        //System.out.println("Offset: "+fio.getOffset());

        FIODaily daily = new FIODaily(fio);
        //In case there is no daily data available
        if (daily.days() > 0) {
            mementoBean.addSection(MementoBeanGeneratorUtility.generateWeatherSectionBean(daily.getDay(0)));
        } else
            System.out.println("No daily data.");

        latch.countDown();
    }

    private String getUnixTime(String date) {
        DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
        long unixtime = 0;
        {
            //dfm.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));//Specify your timezone
            try {
                unixtime = dfm.parse(date).getTime();
                unixtime = unixtime / 1000;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Long.toString(unixtime);
        }
    }
}
