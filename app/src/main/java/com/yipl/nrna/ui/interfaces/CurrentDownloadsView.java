package com.yipl.nrna.ui.interfaces;

import com.yipl.nrna.domain.model.DownloadItem;

import java.util.List;

/**
 * Created by julian on 2/23/16.
 */
public interface CurrentDownloadsView extends LoadDataView {
    void showCurrentDownloads(List<DownloadItem> items);
}
