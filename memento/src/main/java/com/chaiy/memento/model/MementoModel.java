package com.chaiy.memento.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.chaiy.memento.model.sections.BaseSectionModel;
import com.chaiy.memento.model.sections.Sections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class MementoModel implements Parcelable {


    public static final int MOVIES_SECTION = 1;
    public static final int NEWS_SECTION = 2;
    public static final int WEATHER_SECTION = 3;


    private final List<Integer> sectionOrder;


    HashMap<Integer, BaseSectionModel> sections = new HashMap<>();

    public MementoModel(List<Integer> sectionOrder) {
        this.sectionOrder = sectionOrder;
    }

    public List<Integer> getSectionOrder() {
        return sectionOrder;
    }

    public HashMap<Integer, BaseSectionModel> getSections() {
        return sections;
    }

    public void addSection(int sectionIndex ,BaseSectionModel section) {
        switch (sectionIndex) {
            case NEWS_SECTION:
                sections.put(NEWS_SECTION, section);
                break;
            case MOVIES_SECTION:
                sections.put(MOVIES_SECTION, section);
                break;
            case WEATHER_SECTION:
                sections.put(WEATHER_SECTION, section);
                break;
        }
    }

    public BaseSectionModel getSection(Sections section) {
        switch (section) {
            case NEWS:
                return sections.get(NEWS_SECTION);
            case MOVIES:
                return sections.get(MOVIES_SECTION);
            case WEATHER:
                return sections.get(WEATHER_SECTION);
            default:
                return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.sectionOrder);
        //dest.writeSerializable(this.sections);
    }

    protected MementoModel(Parcel in) {
        this.sectionOrder = new ArrayList<Integer>();
        in.readList(this.sectionOrder, Integer.class.getClassLoader());
        //this.sections = (HashMap<Integer, BaseSectionModel>) in.readSerializable();
    }

    public static final Parcelable.Creator<MementoModel> CREATOR = new Parcelable.Creator<MementoModel>() {
        @Override
        public MementoModel createFromParcel(Parcel source) {
            return new MementoModel(source);
        }

        @Override
        public MementoModel[] newArray(int size) {
            return new MementoModel[size];
        }
    };
}
