package com.yipl.nrna.ui.interfaces;

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

    void onMediaChanged(Post pPost);

    void playStatusChanged(boolean isPlaying);
}
