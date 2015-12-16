package com.yipl.nrna.domain.model;

import com.yipl.nrna.domain.util.MyConstants;

/**
 * Created by julian on 12/8/15.
 */
public class Country extends BaseModel {
    Long mCreatedAt;
    Long mUpdatedAt;
    String mImageUrl;
    String mName;
    String mAbout;

    public Country() {
        mDataType = MyConstants.Adapter.TYPE_COUNTRY;
    }

    public Long getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Long pCreatedAt) {
        mCreatedAt = pCreatedAt;
    }

    public Long getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Long pUpdatedAt) {
        mUpdatedAt = pUpdatedAt;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String pImageUrl) {
        mImageUrl = pImageUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String pName) {
        mName = pName;
    }

    public String getAbout() {
        return mAbout;
    }

    public void setAbout(String pAbout) {
        mAbout = pAbout;
    }
}
