package com.chaiy.memento.model.sections;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class BaseSectionModel implements Parcelable {

    private final Sections section;


    protected BaseSectionModel(Sections section) {
        this.section = section;
    }

    public String getSectionName() {
        switch (section) {
            case NEWS:
                return "News";
            case MOVIES:
                return "Movies";
            case WEATHER:
                return "Weather";
            default:
                return null;
        }
    }

    public Sections getSection() {
        return section;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.section == null ? -1 : this.section.ordinal());
    }

    protected BaseSectionModel(Parcel in) {
        int tmpSection = in.readInt();
        this.section = tmpSection == -1 ? null : Sections.values()[tmpSection];
    }

    public static final Creator<BaseSectionModel> CREATOR = new Creator<BaseSectionModel>() {
        @Override
        public BaseSectionModel createFromParcel(Parcel source) {
            return new BaseSectionModel(source);
        }

        @Override
        public BaseSectionModel[] newArray(int size) {
            return new BaseSectionModel[size];
        }
    };
}
