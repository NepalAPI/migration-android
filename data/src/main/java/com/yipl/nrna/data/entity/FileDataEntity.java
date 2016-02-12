package com.yipl.nrna.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nirazan-PC on 2/12/2016.
 */
public class FileDataEntity {

    @SerializedName("file_name")
    String fileUrl;
    String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String pFileUrl) {
        fileUrl = pFileUrl;
    }
}
