package com.yipl.nrna.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nirazan-PC on 1/8/2016.
 */
public class DeletedContentEntity {

    @SerializedName("id")
    Long mId;
    @SerializedName("deleted_At")
    Long mDeletedAt;

    public Long getDeletedAt() {
        return mDeletedAt;
    }

    public void setDeletedAt(Long pDeletedAt) {
        mDeletedAt = pDeletedAt;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long pId) {
        mId = pId;
    }
}
