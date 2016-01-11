package com.yipl.nrna.domain.model;

import com.yipl.nrna.domain.util.MyConstants;

/**
 * Created by julian on 1/8/16.
 */
public class CountryUpdate extends BaseModel {
    Long mId;
    Long mCountryId;
    Long mUpdatedAt;
    Long mCreatedAt;
    String mTitle;
    String mDescription;

    public CountryUpdate() {
        setDataType(MyConstants.Adapter.TYPE_COUNTRY_UPDATE);
    }

    @Override
    public Long getId() {
        return mId;
    }

    @Override
    public void setId(Long pId) {
        mId = pId;
    }

    public Long getCountryId() {
        return mCountryId;
    }

    public void setCountryId(Long pCountryId) {
        mCountryId = pCountryId;
    }

    public Long getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Long pUpdatedAt) {
        mUpdatedAt = pUpdatedAt;
    }

    public Long getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Long pCreatedAt) {
        mCreatedAt = pCreatedAt;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String pTitle) {
        mTitle = pTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String pDescription) {
        mDescription = pDescription;
    }
}
