package com.yipl.nrna.media;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.PowerManager;

import com.yipl.nrna.R;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.util.MyConstants;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by julian on 12/14/15.
 */
public class MediaService extends Service implements
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnInfoListener {

    private final IBinder mBinder = new MusicBinder();
    private MediaPlayer mPlayer;
    private List<Post> mTracks;
    private int mCurrentPosition;
    private Post mCurrentTrack;
    private boolean mIsMediaValid = false;

    @Override
    public void onCreate() {
        super.onCreate();
        mCurrentPosition = 0;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mPlayer != null) {
            mPlayer.reset();
        } else {
            mPlayer = new MediaPlayer();
            initMediaPlayer();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
        return true;
    }

    private void initMediaPlayer() {
        mPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnErrorListener(this);
        mPlayer.setOnInfoListener(this);
    }

    public void setTracks(List<Post> pSongs, int pIndex) {
        mTracks = pSongs;
        mCurrentPosition = pIndex;
    }

    public void setTrack(int pIndex) {
        mCurrentPosition = pIndex;
    }

    @Override
    public void onPrepared(MediaPlayer pMediaPlayer) {
        mIsMediaValid = true;
        pMediaPlayer.start();
        sendBroadcast(new Intent(MyConstants.Media.ACTION_STATUS_PREPARED));
    }

    @Override
    public void onCompletion(MediaPlayer pMediaPlayer) {
        sendBroadcast(new Intent(MyConstants.Media.ACTION_MEDIA_BUFFER_STOP));
        mIsMediaValid = false;
        playNext();
    }

    @Override
    public boolean onError(MediaPlayer pMediaPlayer, int pWhat, int pExtra) {
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    private void playMedia(int index) {
        mCurrentTrack = mTracks.get(index);
        sendBroadcast(new Intent(MyConstants.Media.ACTION_MEDIA_BUFFER_START));

        Intent intent = new Intent(MyConstants.Media.ACTION_MEDIA_CHANGE);
        intent.putExtra(MyConstants.Extras.KEY_AUDIO, mCurrentTrack);
        sendBroadcast(intent);
        try {
            mPlayer.reset();
            setDataSource(mCurrentTrack);
            mPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
            playNext();
        }
    }

    private void setDataSource(Post pCurrentTrack) throws IOException {
        String mediaUrl = pCurrentTrack.getData().getMediaUrl();
        String fileName = mediaUrl.substring(mediaUrl.lastIndexOf("/") + 1);
        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        File dir = new File(root, getString(R.string.app_name));
        dir.mkdirs();
        File audioFile = new File(dir, fileName);

        if (audioFile.exists()) {
            String path = audioFile.getAbsolutePath();
            if (!(path.startsWith("file") || path.startsWith("content") || path.startsWith("FILE") || path
                    .startsWith("CONTENT"))) {
                path = "file://" + path;
            }
            mPlayer.setDataSource(this, Uri.parse(path));
        } else {
            mPlayer.setDataSource(mediaUrl);
        }
    }

    public void playPause() {
        Intent intent = new Intent(MyConstants.Media.ACTION_PLAY_STATUS_CHANGE);
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            intent.putExtra(MyConstants.Extras.KEY_PLAY_STATUS, false);
        } else {
            mIsMediaValid = true;
            mPlayer.start();
            intent.putExtra(MyConstants.Extras.KEY_PLAY_STATUS, true);
        }
        sendBroadcast(intent);
    }

    public void stopPlayback() {
        mPlayer.pause();
    }

    public void playNext() {
        mIsMediaValid = false;
        mPlayer.stop();
        if (mCurrentPosition == mTracks.size() - 1) {
            mCurrentPosition = 0;
        } else {
            mCurrentPosition += 1;
        }
        playMedia(mCurrentPosition);
    }

    public void playPrev() {
        mIsMediaValid = false;
        if (mCurrentPosition == 0) {
            mCurrentPosition = mTracks.size() - 1;
        } else {
            mCurrentPosition -= 1;
        }
        mPlayer.stop();
        playMedia(mCurrentPosition);
    }

    public void seekTo(int seekbarProgress) {
        int currentPosition = MediaHelper.progressToTimer(seekbarProgress, mPlayer.getDuration());
        mPlayer.seekTo(currentPosition);
    }

    public void startStreaming() {
        playMedia(mCurrentPosition);
    }

    public boolean isMediaValid() {
        return mIsMediaValid;
    }

    public long[] getSongLengths() {
        try {
            if (mIsMediaValid) {
                return new long[]{mPlayer.getCurrentPosition(), mPlayer.getDuration()};
            } else {
                return null;
            }
        } catch (IllegalStateException e) {
            return null;
        }
    }

    public int getPlayListSize() {
        return (mTracks == null) ? 0 : mTracks.size();
    }

    public Post getCurrentTrack() {
        return mCurrentTrack;
    }

    public boolean getPlayStatus() {
        return mPlayer.isPlaying();
    }

    public class MusicBinder extends Binder {
        public MediaService getService() {
            return MediaService.this;
        }
    }
}
