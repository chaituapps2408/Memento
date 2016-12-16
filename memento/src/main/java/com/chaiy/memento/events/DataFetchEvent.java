package com.chaiy.memento.events;

import com.chaiy.memento.model.MementoModel;

/**
 * Created by Chaiy on 12/8/2016.
 */

public class DataFetchEvent {

    private final boolean isSuccess;
    private final MementoModel mementoModel;


    public DataFetchEvent(boolean isSuccess, MementoModel mementoModel) {
        this.isSuccess = isSuccess;
        this.mementoModel = mementoModel;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public MementoModel getMementoModel() {
        return mementoModel;
    }
}
