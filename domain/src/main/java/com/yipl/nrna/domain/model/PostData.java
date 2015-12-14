package com.yipl.nrna.domain.model;

import java.io.Serializable;

/**
 * Created by julian on 12/9/15.
 */
public class PostData implements Serializable {
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
