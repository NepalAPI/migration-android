package com.yipl.nrna.domain.model;

import java.io.Serializable;

/**
 * Created by julian on 12/9/15.
 */
public class PostData implements Serializable {
    String mediaUrl;
    String content;
    String thumbnail;
    String duration;


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
}
