package com.yipl.nrna.domain.model;

import java.io.Serializable;

/**
 * Created by julian on 12/7/15.
 */
public abstract class BaseModel implements Serializable{
    String mId;
    int mDataType;

    public String getId() {
        return mId;
    }

    public void setId(String pId) {
        mId = pId;
    }

    public int getDataType() {
        return mDataType;
    }

    public void setDataType(int pDataType) {
        mDataType = pDataType;
    }
}
