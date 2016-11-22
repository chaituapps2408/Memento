package com.chaiy.memento.model.sections.news;


import android.os.Parcel;

import com.chaiy.memento.model.sections.BaseSectionModel;
import com.chaiy.memento.model.sections.Sections;

import java.util.List;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class NewsSectionModel extends BaseSectionModel {

    final List<NewsItemModel> newsItemModelList;

    public NewsSectionModel(List<NewsItemModel> newsItemModelList) {
        super(Sections.NEWS);
        this.newsItemModelList = newsItemModelList;
    }

    public List<NewsItemModel> getNewsItemModelList() {
        return newsItemModelList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.newsItemModelList);
    }

    protected NewsSectionModel(Parcel in) {
        super(in);
        this.newsItemModelList = in.createTypedArrayList(NewsItemModel.CREATOR);
    }

    public static final Creator<NewsSectionModel> CREATOR = new Creator<NewsSectionModel>() {
        @Override
        public NewsSectionModel createFromParcel(Parcel source) {
            return new NewsSectionModel(source);
        }

        @Override
        public NewsSectionModel[] newArray(int size) {
            return new NewsSectionModel[size];
        }
    };
}
