package com.chaiy.memento.backend.bean.sections.news;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class NewsItemBean {

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
}
