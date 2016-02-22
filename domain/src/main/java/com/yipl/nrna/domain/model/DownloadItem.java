package com.yipl.nrna.domain.model;

import com.yipl.nrna.domain.util.MyConstants;

/**
 * Created by julian on 2/23/16.
 */
public class DownloadItem extends BaseModel {
    String title;
    long downloadReference;

    public DownloadItem(){
        setDataType(MyConstants.Adapter.TYPE_DOWNLOAD_ITEM);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String pTitle) {
        title = pTitle;
    }

    public long getDownloadReference() {
        return downloadReference;
    }

    public void setDownloadReference(long pDownloadReference) {
        downloadReference = pDownloadReference;
    }
}
