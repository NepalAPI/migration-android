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
    String about;
    String name;
    @SerializedName("image_url")
    String imageUrl;

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

    public String getAbout() {
        return about;
    }

    public void setAbout(String pAbout) {
        about = pAbout;
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String pImageUrl) {
        imageUrl = pImageUrl;
    }
}
