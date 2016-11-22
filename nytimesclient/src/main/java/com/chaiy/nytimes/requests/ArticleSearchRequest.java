package com.chaiy.nytimes.requests;

/**
 * Created by Chaiy on 11/5/2016.
 */

public class ArticleSearchRequest {


    String beginDate;
    String endDate;
    String[] searchQueryList;
    String[] filteredQueryList;
    String sortType;
    String[] filterFields;
    int page;

    private ArticleSearchRequest(Builder builder) {
        beginDate = builder.beginDate;
        endDate = builder.endDate;
        searchQueryList = builder.searchQueryList;
        filteredQueryList = builder.filteredQueryList;
        sortType = builder.sortType;
        filterFields = builder.filterFields;
        page = builder.page;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String[] getSearchQueryList() {
        return searchQueryList;
    }

    public String[] getFilteredQueryList() {
        return filteredQueryList;
    }

    public String getSortType() {
        return sortType;
    }

    public int getPage() {
        return page;
    }

    public String[] getFilterFields() {
        return filterFields;
    }

    public static final class Builder {
        private String beginDate;
        private String endDate;
        private String[] searchQueryList;
        private String[] filteredQueryList;
        private String sortType;
        private String[] filterFields;
        private int page;

        public Builder() {
        }

        public Builder beginDate(String val) {
            beginDate = val;
            return this;
        }

        public Builder endDate(String val) {
            endDate = val;
            return this;
        }

        public Builder searchQueryList(String... val) {
            searchQueryList = val;
            return this;
        }

        public Builder filteredQueryList(String... val) {
            filteredQueryList = val;
            return this;
        }

        public Builder sortType(String val) {
            sortType = val;
            return this;
        }

        public Builder filterFields(String... val) {
            filterFields = val;
            return this;
        }


        public Builder page(int val) {
            page = val;
            return this;
        }

        public ArticleSearchRequest build() {
            return new ArticleSearchRequest(this);
        }
    }
}
