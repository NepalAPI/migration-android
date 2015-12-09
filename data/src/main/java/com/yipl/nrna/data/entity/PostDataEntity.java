package com.yipl.nrna.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by julian on 12/9/15.
 */
public class PostDataEntity {
    @SerializedName("media_url")
    String mediaUrl;
    String content;

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
}
