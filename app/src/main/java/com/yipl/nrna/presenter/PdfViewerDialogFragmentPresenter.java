package com.yipl.nrna.presenter;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.yipl.nrna.R;
import com.yipl.nrna.data.di.PerActivity;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.ui.interfaces.PdfViewerDialogFragmentView;
import com.yipl.nrna.util.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Nirazan-PC on 2/8/2016.
 */

public class PdfViewerDialogFragmentPresenter implements Presenter {

    PdfViewerDialogFragmentView mView;
    Subscription mSubscription;

    @Inject
    public PdfViewerDialogFragmentPresenter(){

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mSubscription.unsubscribe();
    }

    @Override
    public void initialize() {

    }

    public void loadPdf(String url){
        mView.showLoadingView();
        File file = getFile(url);
        getDocument(file.getName(), url);
    }

    @Override
    public void attachView(MvpView view) {
        mView = (PdfViewerDialogFragmentView) view;
    }

    private Observable<File> downloadFileObservable(final String fileName, final String fileUrl) {
        return Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> pSubscriber) {

                File doc = getFile(fileName);
                if (!doc.exists()){
                    try {
                        URL url = new URL(fileUrl);
                        URLConnection urlConnection = url.openConnection();
                        urlConnection.connect();
                        InputStream input = new BufferedInputStream(url.openStream());
                        FileOutputStream output = mView.getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
                        int lengthOfFile = urlConnection.getContentLength();

                        byte data[] = new byte[1024];
                        long total = 0;
                        int count = 0;
                        while ((count = input.read(data)) != -1) {
                            total += count;
                            Logger.e("downloaded: " + (int) ((total * 100) / lengthOfFile));
                            output.write(data, 0, count);
                        }
                        output.flush();
                        output.close();
                        input.close();
                        pSubscriber.onNext(getFile(fileName));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        pSubscriber.onError(e);
                    } catch (IOException e) {
                        e.printStackTrace();
                        pSubscriber.onError(e);
                    }

                }
                else {
                    try {
                        Logger.e("doc", "from storage: "+getFile(fileName).getCanonicalPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    pSubscriber.onNext(getFile(fileName));
                }

            }
        });
    }

    public File getFile(String fileName) {
        File saveDir = mView.getContext().getFilesDir();
        File document = new File(saveDir, fileName);
        return document;

    }

    public void getDocument(String fileName,String url){
        mSubscription = downloadFileObservable(fileName, url).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DownloadObserver());
    }

    public void copyFile(String fileName){
        File internalFile = new File(mView.getContext().getFilesDir(), fileName);
        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+mView.getContext().getString(R.string.app_name));
        if(!directory.exists()){
            directory.mkdir();
        }
        File externalFile = new File(directory, Uri.parse(fileName).getLastPathSegment());
        if(!externalFile.exists()) {

            FileChannel inChannel = null;
            FileChannel outChannel = null;

            try {
                inChannel = new FileInputStream(internalFile).getChannel();
                outChannel = new FileOutputStream(externalFile).getChannel();
                inChannel.transferTo(0, inChannel.size(), outChannel);
                Logger.e("copy", " successful");
                mView.downloadSuccess(externalFile);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inChannel != null)
                        inChannel.close();
                    if (outChannel != null)
                        outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else
            mView.downloadSuccess(externalFile);
    }


    private class DownloadObserver extends Subscriber<File>{

        @Override
        public void onCompleted() {
            mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            mView.hideLoadingView();
            mView.showErrorView(e.getLocalizedMessage());
            Logger.e(e.getLocalizedMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(File pFile) {
            mView.hideLoadingView();
            mView.showPdf(pFile);
            mSubscription.unsubscribe();
        }



    }
}
