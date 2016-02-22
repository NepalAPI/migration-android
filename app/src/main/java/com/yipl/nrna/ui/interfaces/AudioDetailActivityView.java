package com.yipl.nrna.ui.interfaces;

import android.content.Context;

import com.yipl.nrna.domain.model.Post;

/**
 * Created by julian on 12/14/15.
 */
public interface AudioDetailActivityView extends MvpView {
    void onMediaPlayPause();

    void onMediaPlayPrevious();

    void onMediaPlayNext();

    void onSeekTo(int progress);

    void onBufferStarted();

    void onBufferStopped();

    void onMediaPrepared();

    void showHidePrevNext(boolean showPrev, boolean showNext);

    void onMediaChanged(Post pPost);

    void playStatusChanged(boolean isPlaying);

    void onAudioDownloadStarted(String pMessage);

    void onAudioFileNotFoundToShare();

    Context getContext();
}
