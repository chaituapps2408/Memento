package com.chaiy.memento.model;


import com.chaiy.memento.model.sections.BaseSectionModel;
import com.chaiy.memento.model.sections.Sections;

import java.util.HashMap;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class MementoModel {


    private static final int MOVIES_SECTION = 1;
    private static final int NEWS_SECTION = 2;
    private static final int WEATHER_SECTION = 3;


    private final int[] sectionOrder;


    HashMap<Integer, BaseSectionModel> sections = new HashMap<>();

    public MementoModel() {
        this.sectionOrder = new int[]
                {MOVIES_SECTION,
                        NEWS_SECTION,
                        WEATHER_SECTION};
    }

    public int[] getSectionOrder() {
        return sectionOrder;
    }

    public HashMap<Integer, BaseSectionModel> getSections() {
        return sections;
    }

    public void addSection(BaseSectionModel section) {
        switch (section.getSection()) {
            case NEWS:
                sections.put(NEWS_SECTION, section);
                break;
            case MOVIES:
                sections.put(MOVIES_SECTION, section);
                break;
            case WEATHER:
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
}
