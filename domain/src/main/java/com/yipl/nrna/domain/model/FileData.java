package com.yipl.nrna.domain.model;

import java.io.Serializable;

/**
 * Created by Nirazan-PC on 2/12/2016.
 */
public class FileData implements Serializable {
    String fileUrl;
    String description;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String pFileUrl) {
        fileUrl = pFileUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }
}
