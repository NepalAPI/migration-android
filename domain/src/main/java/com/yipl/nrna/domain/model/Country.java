package com.yipl.nrna.domain.model;

import com.yipl.nrna.domain.util.MyConstants;

/**
 * Created by julian on 12/8/15.
 */
public class Country extends BaseModel {
    long mCreatedAt;
    long mUpdatedAt;
    String mDescription;
    String mName;
    String mCode;
    String mImage;

    public Country() {
        mDataType = MyConstants.Adapter.TYPE_COUNTRY;
    }

    public long getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(long pCreatedAt) {
        mCreatedAt = pCreatedAt;
    }

    public long getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(long pUpdatedAt) {
        mUpdatedAt = pUpdatedAt;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String pDescription) {
        mDescription = pDescription;
    }

    public String getName() {
        return mName;
    }

    public void setName(String pName) {
        mName = pName;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String pCode) {
        mCode = pCode;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String pImage) {
        mImage = pImage;
    }
}
