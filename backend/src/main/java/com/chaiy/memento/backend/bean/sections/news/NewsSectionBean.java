package com.chaiy.memento.backend.bean.sections.news;

import com.chaiy.memento.backend.bean.sections.BaseSectionBean;
import com.chaiy.memento.backend.bean.sections.Sections;

import java.util.List;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class NewsSectionBean extends BaseSectionBean {

    final List<NewsItemBean> newsItemBeanList;

    public NewsSectionBean(List<NewsItemBean> newsItemBeanList) {
        super(Sections.NEWS);
        this.newsItemBeanList = newsItemBeanList;
    }

    public List<NewsItemBean> getNewsItemBeanList() {
        return newsItemBeanList;
    }

}
