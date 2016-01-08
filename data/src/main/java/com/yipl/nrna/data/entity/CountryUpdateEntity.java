package com.yipl.nrna.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by julian on 12/7/15.
 */
public class CountryUpdateEntity {
    Long id;
    @SerializedName("country_id")
    Long countryId;
    @SerializedName("updated_at")
    Long updatedAt;
    @SerializedName("created_at")
    Long createdAt;
    String title;
    String description;

    public Long getId() {
        return id;
    }

    public void setId(Long pId) {
        id = pId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long pCountryId) {
        countryId = pCountryId;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long pUpdatedAt) {
        updatedAt = pUpdatedAt;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long pCreatedAt) {
        createdAt = pCreatedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String pTitle) {
        title = pTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }
}
