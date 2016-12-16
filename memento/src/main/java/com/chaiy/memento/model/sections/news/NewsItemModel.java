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

    private NewsItemModel(Builder builder) {
        setHeadLine(builder.headLine);
        setPublishedDate(builder.publishedDate);
        setSnippet(builder.snippet);
        setImagePath(builder.imagePath);
        setSource(builder.source);
        setUrl(builder.url);
    }

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

    public static final Creator<NewsItemModel> CREATOR = new Creator<NewsItemModel>() {
        @Override
        public NewsItemModel createFromParcel(Parcel source) {
            return new NewsItemModel(source);
        }

        @Override
        public NewsItemModel[] newArray(int size) {
            return new NewsItemModel[size];
        }
    };

    public static final class Builder {
        private String headLine;
        private String publishedDate;
        private String snippet;
        private String imagePath;
        private String source;
        private String url;

        public Builder() {
        }

        public Builder headLine(String val) {
            headLine = val;
            return this;
        }

        public Builder publishedDate(String val) {
            publishedDate = val;
            return this;
        }

        public Builder snippet(String val) {
            snippet = val;
            return this;
        }

        public Builder imagePath(String val) {
            imagePath = val;
            return this;
        }

        public Builder source(String val) {
            source = val;
            return this;
        }

        public Builder url(String val) {
            url = val;
            return this;
        }

        public NewsItemModel build() {
            return new NewsItemModel(this);
        }
    }
}
