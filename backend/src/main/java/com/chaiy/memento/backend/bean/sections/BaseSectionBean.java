package com.chaiy.memento.backend.bean.sections;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class BaseSectionBean {

    private final Sections section;


    protected BaseSectionBean(Sections section) {
        this.section = section;
    }

    public Sections getSection() {
        return section;
    }
}
