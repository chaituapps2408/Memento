package com.chaiy.nytimes.bean.articles;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Chaiy on 11/5/2016.
 */

public class Article {

    @SerializedName("web_url")
    String webUrl;

    @SerializedName("snippet")
    String snippet;

    @SerializedName("lead_paragraph")
    String leadParagraph;

    @SerializedName("abstract")
    String abstractField;

    @SerializedName("print_page")
    String printPage;

    @SerializedName("blog")
    String[] blog;

    @SerializedName("source")
    String source;

    @SerializedName("multimedia")
    Multimedia[] multimedia;

    @SerializedName("headline1")
    List<HeadLine> headline;

    @SerializedName("keywords")
    Keywords[] keywords;

    @SerializedName("pub_date")
    String pubDate;

    @SerializedName("document_type")
    String documentType;

    @SerializedName("news_desk")
    String newsDesk;

    @SerializedName("section_name")
    String sectionName;

    @SerializedName("subsection_name")
    String subsectionName;

    @SerializedName("byline1")
    ByLine[] byLine;

    @SerializedName("type_of_material")
    String typeOfMaterial;

    @SerializedName("_id")
    String _id;

    @SerializedName("word_count")
    String wordCount;

    @SerializedName("slideshow_credits")
    String slideshowCredits;

    public void setHeadline(List<HeadLine> headline) {
        this.headline = headline;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getSnippet() {
        return snippet;
    }

    public String getLeadParagraph() {
        return leadParagraph;
    }

    public String getAbstractField() {
        return abstractField;
    }

    public String getPrintPage() {
        return printPage;
    }

    public String[] getBlog() {
        return blog;
    }

    public String getSource() {
        return source;
    }

    public Multimedia[] getMultimedia() {
        return multimedia;
    }

    public List<HeadLine> getHeadline() {
        return headline;
    }

    public Keywords[] getKeywords() {
        return keywords;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getNewsDesk() {
        return newsDesk;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getSubsectionName() {
        return subsectionName;
    }

    public ByLine[] getByLine() {
        return byLine;
    }

    public String getTypeOfMaterial() {
        return typeOfMaterial;
    }

    public String get_id() {
        return _id;
    }

    public String getWordCount() {
        return wordCount;
    }

    public String getSlideshowCredits() {
        return slideshowCredits;
    }
}
