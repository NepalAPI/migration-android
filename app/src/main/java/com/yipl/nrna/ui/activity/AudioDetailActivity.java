package com.yipl.nrna.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yipl.nrna.R;
import com.yipl.nrna.base.FacebookActivity;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.media.MediaHelper;
import com.yipl.nrna.media.MediaReceiver;
import com.yipl.nrna.media.MediaService;
import com.yipl.nrna.media.MediaService.MusicBinder;
import com.yipl.nrna.ui.interfaces.AudioDetailActivityView;
import com.yipl.nrna.util.Logger;

import java.util.List;

import butterknife.Bind;

public class AudioDetailActivity extends FacebookActivity implements
        AudioDetailActivityView,
        View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {

    @Bind(R.id.bufferingText)
    TextView bufferingText;
    @Bind(R.id.playerControls)
    LinearLayout playerControls;
    @Bind(R.id.currentTime)
    TextView currentTime;
    @Bind(R.id.totalTime)
    TextView totalTime;
    @Bind(R.id.prev)
    ImageView prev;
    @Bind(R.id.play)
    ImageView play;
    @Bind(R.id.next)
    ImageView next;
    @Bind(R.id.seekbar)
    SeekBar seekbar;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.description)
    TextView description;

    private MediaService mService;
    private Intent mPlayIntent;
    private boolean mMusicBound = false;
    private List<Post> mTracks;
    private int mSelectedTrack = 0;
    private Handler seekbarHandler = new Handler();
    private MediaReceiver mediaReceiver;
    private IntentFilter receiverFilter;
    //connect to the service
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicBinder binder = (MusicBinder) service;
            mService = binder.getService();
            mService.setTracks(mTracks, mSelectedTrack);
            mService.startStreaming();
            mMusicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMusicBound = false;
        }
    };

    private Runnable updateSeekTime = new Runnable() {
        @Override
        public void run() {
            if (mService != null) {
                long[] lengths = mService.getSongLengths();
                if (lengths != null) {
                    Logger.e("AudioDetailActivity_updateSeekTime", "lengths: " + lengths[0] + "/" +
                            lengths[1] );
                    currentTime.setText(MediaHelper.getFormattedTime(lengths[0]));
                    totalTime.setText(MediaHelper.getFormattedTime(lengths[1]));
                    int progress = MediaHelper.getProgressPercentage(lengths[0], lengths[1]);
                    seekbar.setProgress(progress);
                    seekbarHandler.postDelayed(this, 100);
                } else {
                    Logger.e("AudioDetailActivity_updateSeekTime", "lengths: null");
                    seekbar.removeCallbacks(updateSeekTime);
                }
            } else {
                seekbar.setProgress(0);
            }
        }
    };

    @Override
    public int getLayout() {
        return R.layout.activity_audio_detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle data = getIntent().getExtras();
        if (data != null) {
            mTracks = (List<Post>) data.getSerializable(MyConstants.Extras.KEY_AUDIO_LIST);
            mSelectedTrack = data.getInt(MyConstants.Extras.KEY_AUDIO_SELECTION);
        }

        getToolbar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mediaReceiver = new MediaReceiver(this);
        receiverFilter = new IntentFilter();

        prev.setOnClickListener(this);
        play.setOnClickListener(this);
        next.setOnClickListener(this);
        seekbar.setOnSeekBarChangeListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            Logger.d("AudioDetailActivity_onOptionsItemSelected", "audioTitle: " + mService
                    .getCurrentTrack().getTitle());
            showShareDialog(mService.getCurrentTrack());
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mMusicBound) {
            unbindService(mConnection);
            mMusicBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        stopService(mPlayIntent);
        mService = null;
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        seekbar.removeCallbacks(updateSeekTime);
        if (mediaReceiver != null)
            unregisterReceiver(mediaReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateSeekBar();
        receiverFilter.addAction(MyConstants.Media.ACTION_MEDIA_BUFFER_START);
        receiverFilter.addAction(MyConstants.Media.ACTION_MEDIA_BUFFER_STOP);
        receiverFilter.addAction(MyConstants.Media.ACTION_STATUS_PREPARED);
        receiverFilter.addAction(MyConstants.Media.ACTION_MEDIA_CHANGE);
        receiverFilter.addAction(MyConstants.Media.ACTION_PLAY_STATUS_CHANGE);
        registerReceiver(mediaReceiver, receiverFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPlayIntent == null) {
            mPlayIntent = new Intent(this, MediaService.class);
            bindService(mPlayIntent, mConnection, Context.BIND_AUTO_CREATE);
            startService(mPlayIntent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                updateSeekBar();
                onMediaPlayPause();
                break;
            case R.id.next:
                seekbarHandler.removeCallbacks(updateSeekTime);
                onMediaPlayNext();
                break;
            case R.id.prev:
                seekbarHandler.removeCallbacks(updateSeekTime);
                onMediaPlayPrevious();
                break;
            default:
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        seekbarHandler.removeCallbacks(updateSeekTime);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mService.seekTo(seekBar.getProgress());
        updateSeekBar();
    }

    @Override
    public void onMediaPlayPause() {
        mService.playPause();
    }

    @Override
    public void onMediaPlayPrevious() {
        mService.playPrev();
    }

    @Override
    public void onMediaPlayNext() {
        mService.playNext();
    }

    @Override
    public void onSeekTo(int progress) {
        mService.seekTo(progress);
    }

    @Override
    public void onBufferStarted() {
        disablePlayerControls();
    }

    @Override
    public void onBufferStopped() {
        enablePlayerControls();
    }

    @Override
    public void onMediaPrepared() {
        enablePlayerControls();
        updateSeekBar();
    }

    @Override
    public void onMediaChanged(Post pTrack) {
        updateView(pTrack);
        seekbarHandler.removeCallbacks(updateSeekTime);
    }

    @Override
    public void playStatusChanged(boolean pIsPlaying) {
        if (pIsPlaying) {
            play.setImageResource(android.R.drawable.ic_media_pause);
        } else {
            play.setImageResource(android.R.drawable.ic_media_play);
        }
    }

    private void updateView(Post pPost) {
        title.setText(pPost.getTitle());
        description.setText(pPost.getDescription());
    }

    private void disablePlayerControls() {
        bufferingText.setVisibility(View.VISIBLE);
        play.setEnabled(false);
        next.setEnabled(false);
        prev.setEnabled(false);
        seekbar.setEnabled(false);
    }

    private void enablePlayerControls() {
        bufferingText.setVisibility(View.GONE);
        play.setEnabled(true);
        next.setEnabled(true);
        prev.setEnabled(true);
        seekbar.setEnabled(true);
    }

    private void updateSeekBar() {
        seekbarHandler.removeCallbacks(updateSeekTime);
        seekbarHandler.postDelayed(updateSeekTime, 100);
    }
}
