package com.chaiy.memento.model.sections.news;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class NewsItemModel implements Parcelable {

    String headLine;
    String publishedDate;
    String snippet;
    String imagePath;
    String source;
    String url;

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeadLine() {
        return headLine;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getSource() {
        return source;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.headLine);
        dest.writeString(this.publishedDate);
        dest.writeString(this.snippet);
        dest.writeString(this.imagePath);
        dest.writeString(this.source);
        dest.writeString(this.url);
    }

    public NewsItemModel() {
    }

    protected NewsItemModel(Parcel in) {
        this.headLine = in.readString();
        this.publishedDate = in.readString();
        this.snippet = in.readString();
        this.imagePath = in.readString();
        this.source = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<NewsItemModel> CREATOR = new Parcelable.Creator<NewsItemModel>() {
        @Override
        public NewsItemModel createFromParcel(Parcel source) {
            return new NewsItemModel(source);
        }

        @Override
        public NewsItemModel[] newArray(int size) {
            return new NewsItemModel[size];
        }
    };
}
