package com.yipl.nrna.presenter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.yipl.nrna.R;
import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.ui.interfaces.AudioDetailActivityView;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.util.AppPreferences;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public class AudioDetailPresenter implements Presenter {

    UseCase mUseCase;
    AudioDetailActivityView mView;
    AppPreferences mPreferences;
    String folderName;

    DownloadManager mDownloadManager;

    @Inject
    public AudioDetailPresenter(@Named("download_start") UseCase pUseCase, AppPreferences
            pPreferences) {
        mUseCase = pUseCase;
        mPreferences = pPreferences;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
    }

    @Override
    public void initialize() {
        mDownloadManager = (DownloadManager) mView.getContext().getSystemService(Context
                .DOWNLOAD_SERVICE);
        folderName = mView.getContext().getString(R.string.app_name);
    }

    @Override
    public void attachView(MvpView view) {
        mView = (AudioDetailActivityView) view;
    }

    public void downloadAudioPost(Post pAudio) {
        String mediaUrl = pAudio.getData().getMediaUrl();
        String fileName = mediaUrl.substring(mediaUrl.lastIndexOf("/") + 1);
        File audioFile = getAudioFile(fileName);

        if (!audioFile.exists()) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pAudio.getData()
                    .getMediaUrl()));
            request.setTitle(pAudio.getTitle());
            request.setDescription(mView.getContext().getResources().getString(R.string
                    .download_description));

            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, folderName +
                    "/" + fileName);
            request.setVisibleInDownloadsUi(true);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager
                    .Request.NETWORK_MOBILE);
            long reference = mDownloadManager.enqueue(request);
            mPreferences.addDownloadReference(reference);
            mUseCase.execute(new DefaultSubscriber(), reference);
        } else {
            mView.onAudioDownloadStarted(mView.getContext().getString(R.string.download_already,
                    pAudio.getTitle()));
        }
    }

    public void shareViaBluetooth(Post pAudio){
        String mediaUrl = pAudio.getData().getMediaUrl();
        String fileName = mediaUrl.substring(mediaUrl.lastIndexOf("/") + 1);
        File audioFile = getAudioFile(fileName);

        if(audioFile.exists()){
            String path =audioFile.getAbsolutePath();
            if(!(path.startsWith("file")||path.startsWith("content")||path.startsWith("FILE")||path
                    .startsWith("CONTENT"))) {
                path="file://"+path;
            }
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            Uri audioUri = Uri.parse(path);

            sharingIntent.setType("audio/mpeg");
            sharingIntent.setPackage("com.android.bluetooth");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, audioUri);
            mView.getContext().startActivity(Intent.createChooser(sharingIntent, mView.getContext
                    ().getString(R.string.share_audio)));
        }else{
            mView.onAudioFileNotFoundToShare();
        }
    }

    private File getAudioFile(String fileName){
        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        File dir = new File(root, folderName);
        dir.mkdirs();
        return new File(dir, fileName);
    }
}
