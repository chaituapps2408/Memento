package com.chaiy.memento.model.sections.movies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Chaiy on 11/13/2016.
 */

public class TmdbConfig implements Parcelable {

    private String baseUrl;
    private String secureBaseUrl;
    private List<String> posterSizes;
    private List<String> backdropSizes;
    private List<String> profileSizes;
    private List<String> logoSizes;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getSecureBaseUrl() {
        return secureBaseUrl;
    }

    public List<String> getPosterSizes() {
        return posterSizes;
    }

    public List<String> getBackdropSizes() {
        return backdropSizes;
    }

    public List<String> getProfileSizes() {
        return profileSizes;
    }

    public List<String> getLogoSizes() {
        return logoSizes;
    }

    private TmdbConfig(Builder builder) {
        baseUrl = builder.baseUrl;
        secureBaseUrl = builder.secureBaseUrl;
        posterSizes = builder.posterSizes;
        backdropSizes = builder.backdropSizes;
        profileSizes = builder.profileSizes;
        logoSizes = builder.logoSizes;
    }


    public static final class Builder {
        private String baseUrl;
        private String secureBaseUrl;
        private List<String> posterSizes;
        private List<String> backdropSizes;
        private List<String> profileSizes;
        private List<String> logoSizes;

        public Builder() {
        }

        public Builder baseUrl(String val) {
            baseUrl = val;
            return this;
        }

        public Builder secureBaseUrl(String val) {
            secureBaseUrl = val;
            return this;
        }

        public Builder posterSizes(List<String> val) {
            posterSizes = val;
            return this;
        }

        public Builder backdropSizes(List<String> val) {
            backdropSizes = val;
            return this;
        }

        public Builder profileSizes(List<String> val) {
            profileSizes = val;
            return this;
        }

        public Builder logoSizes(List<String> val) {
            logoSizes = val;
            return this;
        }

        public TmdbConfig build() {
            return new TmdbConfig(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.baseUrl);
        dest.writeString(this.secureBaseUrl);
        dest.writeStringList(this.posterSizes);
        dest.writeStringList(this.backdropSizes);
        dest.writeStringList(this.profileSizes);
        dest.writeStringList(this.logoSizes);
    }

    protected TmdbConfig(Parcel in) {
        this.baseUrl = in.readString();
        this.secureBaseUrl = in.readString();
        this.posterSizes = in.createStringArrayList();
        this.backdropSizes = in.createStringArrayList();
        this.profileSizes = in.createStringArrayList();
        this.logoSizes = in.createStringArrayList();
    }

    public static final Parcelable.Creator<TmdbConfig> CREATOR = new Parcelable.Creator<TmdbConfig>() {
        @Override
        public TmdbConfig createFromParcel(Parcel source) {
            return new TmdbConfig(source);
        }

        @Override
        public TmdbConfig[] newArray(int size) {
            return new TmdbConfig[size];
        }
    };
}
