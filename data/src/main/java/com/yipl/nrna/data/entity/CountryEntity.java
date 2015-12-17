package com.yipl.nrna.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by julian on 12/9/15.
 */
public class CountryEntity {
    long id;
    @SerializedName("created_at")
    long createdAt;
    @SerializedName("updated_at")
    long updatedAt;
    String description;
    String name;
    String code;
    String image;

    public long getId() {
        return id;
    }

    public void setId(long pId) {
        id = pId;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long pCreatedAt) {
        createdAt = pCreatedAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long pUpdatedAt) {
        updatedAt = pUpdatedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String pCode) {
        code = pCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String pImage) {
        image = pImage;
    }
}
