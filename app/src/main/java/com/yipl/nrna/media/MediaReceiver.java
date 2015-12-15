package com.yipl.nrna.media;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.ui.interfaces.AudioDetailActivityView;

public class MediaReceiver extends BroadcastReceiver {

    AudioDetailActivityView mListener = null;


    public MediaReceiver(AudioDetailActivityView pListener) {
        mListener = pListener;
    }

    public MediaReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(MyConstants.Media.ACTION_MEDIA_BUFFER_START)) {
            mListener.onBufferStarted();
        } else if (intent.getAction().equals(MyConstants.Media.ACTION_MEDIA_BUFFER_STOP)) {
            mListener.onBufferStopped();
        } else if (intent.getAction().equals(MyConstants.Media.ACTION_MEDIA_CHANGE)) {
            mListener.onMediaChanged((Post) intent.getSerializableExtra(MyConstants
                    .Extras.KEY_AUDIO));
        } else if (intent.getAction().equals(MyConstants.Media.ACTION_STATUS_PREPARED)) {
            mListener.onMediaPrepared();
        } else if (intent.getAction().equals(MyConstants.Media.ACTION_PLAY_STATUS_CHANGE)) {
            mListener.playStatusChanged(intent.getBooleanExtra(MyConstants.Extras
                    .KEY_PLAY_STATUS, false));
        }
    }
}
