package com.yipl.nrna.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by julian on 12/9/15.
 */
public class PostDataEntity {
    @SerializedName("media_url")
    String mediaUrl;
    String content;
    String thumbnail;
    String duration;
    List<FileDataEntity> file;

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String pMediaUrl) {
        mediaUrl = pMediaUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String pContent) {
        content = pContent;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String pThumbnail) {
        thumbnail = pThumbnail;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String pDuration) {
        duration = pDuration;
    }


    public List<FileDataEntity> getFile() {
        return file;
    }

    public void setFile(List<FileDataEntity> pFile) {
        file = pFile;
    }

}
