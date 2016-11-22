package com.chaiy.memento.backend.bean.sections.weather;

/**
 * Created by Chaiy on 11/13/2016.
 */

public class DailyWeather {

    String summary;
    String visibility;
    String apparentTemperatureMinTime;
    String temperatureMax;
    String icon;
    String cloudCover;
    String temperatureMin;
    String windBearing;
    String pressure;
    String dewPoint;
    String temperatureMinTime;
    String apparentTemperatureMax;
    String temperatureMaxTime;
    String apparentTemperatureMin;
    String sunsetTime;
    String precipType;
    String humidity;
    String time;
    String moonPhase;
    String windSpeed;
    String apparentTemperatureMaxTime;
    String sunriseTime;

    public String getSummary() {
        return summary;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getApparentTemperatureMinTime() {
        return apparentTemperatureMinTime;
    }

    public String getTemperatureMax() {
        return temperatureMax;
    }

    public String getIcon() {
        return icon;
    }

    public String getCloudCover() {
        return cloudCover;
    }

    public String getTemperatureMin() {
        return temperatureMin;
    }

    public String getWindBearing() {
        return windBearing;
    }

    public String getPressure() {
        return pressure;
    }

    public String getDewPoint() {
        return dewPoint;
    }

    public String getTemperatureMinTime() {
        return temperatureMinTime;
    }

    public String getApparentTemperatureMax() {
        return apparentTemperatureMax;
    }

    public String getTemperatureMaxTime() {
        return temperatureMaxTime;
    }

    public String getApparentTemperatureMin() {
        return apparentTemperatureMin;
    }

    public String getSunsetTime() {
        return sunsetTime;
    }

    public String getPrecipType() {
        return precipType;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getTime() {
        return time;
    }

    public String getMoonPhase() {
        return moonPhase;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getApparentTemperatureMaxTime() {
        return apparentTemperatureMaxTime;
    }

    public String getSunriseTime() {
        return sunriseTime;
    }

    private DailyWeather(Builder builder) {
        summary = builder.summary;
        visibility = builder.visibility;
        apparentTemperatureMinTime = builder.apparentTemperatureMinTime;
        temperatureMax = builder.temperatureMax;
        icon = builder.icon;
        cloudCover = builder.cloudCover;
        temperatureMin = builder.temperatureMin;
        windBearing = builder.windBearing;
        pressure = builder.pressure;
        dewPoint = builder.dewPoint;
        temperatureMinTime = builder.temperatureMinTime;
        apparentTemperatureMax = builder.apparentTemperatureMax;
        temperatureMaxTime = builder.temperatureMaxTime;
        apparentTemperatureMin = builder.apparentTemperatureMin;
        sunsetTime = builder.sunsetTime;
        precipType = builder.precipType;
        humidity = builder.humidity;
        time = builder.time;
        moonPhase = builder.moonPhase;
        windSpeed = builder.windSpeed;
        apparentTemperatureMaxTime = builder.apparentTemperatureMaxTime;
        sunriseTime = builder.sunriseTime;
    }


    public static final class Builder {
        private String summary;
        private String visibility;
        private String apparentTemperatureMinTime;
        private String temperatureMax;
        private String icon;
        private String cloudCover;
        private String temperatureMin;
        private String windBearing;
        private String pressure;
        private String dewPoint;
        private String temperatureMinTime;
        private String apparentTemperatureMax;
        private String temperatureMaxTime;
        private String apparentTemperatureMin;
        private String sunsetTime;
        private String precipType;
        private String humidity;
        private String time;
        private String moonPhase;
        private String windSpeed;
        private String apparentTemperatureMaxTime;
        private String sunriseTime;

        public Builder() {
        }

        public Builder summary(String val) {
            summary = val;
            return this;
        }

        public Builder visibility(String val) {
            visibility = val;
            return this;
        }

        public Builder apparentTemperatureMinTime(String val) {
            apparentTemperatureMinTime = val;
            return this;
        }

        public Builder temperatureMax(String val) {
            temperatureMax = val;
            return this;
        }

        public Builder icon(String val) {
            icon = val;
            return this;
        }

        public Builder cloudCover(String val) {
            cloudCover = val;
            return this;
        }

        public Builder temperatureMin(String val) {
            temperatureMin = val;
            return this;
        }

        public Builder windBearing(String val) {
            windBearing = val;
            return this;
        }

        public Builder pressure(String val) {
            pressure = val;
            return this;
        }

        public Builder dewPoint(String val) {
            dewPoint = val;
            return this;
        }

        public Builder temperatureMinTime(String val) {
            temperatureMinTime = val;
            return this;
        }

        public Builder apparentTemperatureMax(String val) {
            apparentTemperatureMax = val;
            return this;
        }

        public Builder temperatureMaxTime(String val) {
            temperatureMaxTime = val;
            return this;
        }

        public Builder apparentTemperatureMin(String val) {
            apparentTemperatureMin = val;
            return this;
        }

        public Builder sunsetTime(String val) {
            sunsetTime = val;
            return this;
        }

        public Builder precipType(String val) {
            precipType = val;
            return this;
        }

        public Builder humidity(String val) {
            humidity = val;
            return this;
        }

        public Builder time(String val) {
            time = val;
            return this;
        }

        public Builder moonPhase(String val) {
            moonPhase = val;
            return this;
        }

        public Builder windSpeed(String val) {
            windSpeed = val;
            return this;
        }

        public Builder apparentTemperatureMaxTime(String val) {
            apparentTemperatureMaxTime = val;
            return this;
        }

        public Builder sunriseTime(String val) {
            sunriseTime = val;
            return this;
        }

        public DailyWeather build() {
            return new DailyWeather(this);
        }
    }
}
