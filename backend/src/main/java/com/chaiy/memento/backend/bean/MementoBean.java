package com.chaiy.memento.backend.bean;

import com.chaiy.memento.backend.bean.sections.BaseSectionBean;

import java.util.HashMap;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class MementoBean {


    private static final int MOVIES_SECTION = 1;
    private static final int NEWS_SECTION = 2;
    private static final int WEATHER_SECTION = 3;


    private final int[] sectionOrder;


    HashMap<Integer, BaseSectionBean> sections = new HashMap<>();

    public MementoBean() {
        this.sectionOrder = new int[]
                {MOVIES_SECTION,
                        NEWS_SECTION,
                        WEATHER_SECTION};
    }

    public int[] getSectionOrder() {
        return sectionOrder;
    }

    public HashMap<Integer, BaseSectionBean> getSections() {
        return sections;
    }

    public void addSection(BaseSectionBean section) {
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
}
