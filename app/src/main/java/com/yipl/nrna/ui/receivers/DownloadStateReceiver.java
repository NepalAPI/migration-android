package com.yipl.nrna.ui.receivers;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

import com.yipl.nrna.MyApplication;
import com.yipl.nrna.R;
import com.yipl.nrna.di.component.DaggerApplicationComponent;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.di.module.ActivityModule;
import com.yipl.nrna.di.module.ApplicationModule;
import com.yipl.nrna.di.module.DataModule;
import com.yipl.nrna.presenter.DownloadCompletePresenter;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.util.AppPreferences;
import com.yipl.nrna.util.Logger;

import javax.inject.Inject;

/**
 * Created by julian on 1/26/16.
 */
public class DownloadStateReceiver extends BroadcastReceiver implements MvpView{
    DownloadManager mDownloadManager;
    AppPreferences pref;
    Context mContext;

    @Inject
    DownloadCompletePresenter mPresenter;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;

        mDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        pref = new AppPreferences(context);

        long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, Long.MIN_VALUE);
        if (reference != Long.MIN_VALUE && pref.hasDownloadReference(reference)) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(reference);
            Cursor cursor = mDownloadManager.query(query);
            extractDataFromCursor(context, reference, cursor);
        }else{
            Logger.d("DownloadStateReceiver_onReceive", "reference set: " + pref.getDownloadReferences());
            Logger.d("DownloadStateReceiver_onReceive", "test: not apps download");
        }
    }

    private void extractDataFromCursor(Context pContext, Long pReference, Cursor pCursor) {
        pCursor.moveToFirst();
        int status = pCursor.getInt(pCursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
        int reason = pCursor.getInt(pCursor.getColumnIndex(DownloadManager.COLUMN_REASON));
        String filePath = pCursor.getString(pCursor.getColumnIndex(DownloadManager
                .COLUMN_LOCAL_FILENAME));

        switch (status) {
            case DownloadManager.STATUS_SUCCESSFUL:
                Toast.makeText(pContext, pContext.getString(R.string.app_name) + ": File " +
                        "Downloaded to " + filePath, Toast.LENGTH_SHORT).show();

                DaggerDataComponent.builder()
                        .activityModule(new ActivityModule())
                        .applicationComponent(DaggerApplicationComponent.builder()
                                .applicationModule(new ApplicationModule((MyApplication) mContext
                                        .getApplicationContext()))
                                .build())
                        .dataModule(new DataModule(pReference))
                        .build()
                        .inject(this);
                mPresenter.attachView(this);
                mPresenter.initialize();
                break;
            case DownloadManager.STATUS_FAILED:
                Toast.makeText(pContext, pContext.getString(R.string.app_name) + "\\ Failed:" +
                        reason, Toast.LENGTH_SHORT).show();
                break;
            case DownloadManager.STATUS_PAUSED:
                Toast.makeText(pContext, pContext.getString(R.string.app_name) + "\\ Paused:" +
                        reason, Toast.LENGTH_SHORT).show();
                break;
            case DownloadManager.STATUS_PENDING:
                break;
            case DownloadManager.STATUS_RUNNING:
                break;
        }
    }
}
