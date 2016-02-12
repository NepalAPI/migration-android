package com.yipl.nrna.ui.interfaces;

import android.content.Context;

import java.io.File;

/**
 * Created by Nirazan-PC on 2/8/2016.
 */
public interface PdfViewerDialogFragmentView extends LoadDataView {
    public void showPdf(File pFile);
    public void downloadSuccess(File pFile);
}
