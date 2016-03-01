package com.yipl.nrna.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.yipl.nrna.R;
import com.yipl.nrna.base.FacebookActivity;
import com.yipl.nrna.data.utils.Logger;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.di.module.DataModule;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.media.MediaHelper;
import com.yipl.nrna.media.MediaReceiver;
import com.yipl.nrna.media.MediaService;
import com.yipl.nrna.media.MediaService.MusicBinder;
import com.yipl.nrna.presenter.AudioDetailPresenter;
import com.yipl.nrna.ui.interfaces.AudioDetailActivityView;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Subscription;

public class AudioDetailActivity extends FacebookActivity implements
        AudioDetailActivityView,
        View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {

    private static final int GROUP1 = 101;
    private static final int SUBMENU_BLUETOOTH = 1001;
    private static final int SUBMENU_FACEBOOK = 1002;

    @Bind(R.id.content_art)
    SimpleDraweeView mAlbumArt;
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
    FloatingActionButton fab;
    @Bind(R.id.next)
    ImageView next;
    @Bind(R.id.seekbar)
    SeekBar seekbar;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.description)
    WebView description;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    Drawable playIcon, pauseIcon;

    @Inject
    AudioDetailPresenter mPresenter;

    private MediaService mService;
    private Intent mPlayIntent;
    private boolean mMusicBound = false;
    private List<Post> mTracks;
    private int mSelectedTrack = 0;
    private Handler seekbarHandler = new Handler();
    private MediaReceiver mediaReceiver;
    private IntentFilter receiverFilter;
    private Post mAudio;
    private Subscription mSubscription;
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
                    currentTime.setText(MediaHelper.getFormattedTime(lengths[0]));
                    totalTime.setText(MediaHelper.getFormattedTime(lengths[1]));
                    int progress = MediaHelper.getProgressPercentage(lengths[0], lengths[1]);
                    seekbar.setProgress(progress);
                    seekbarHandler.postDelayed(this, 500);
                } else {
                    seekbar.setProgress(0);
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
    public void onResume() {
        super.onResume();
        if (mPresenter != null) mPresenter.resume();
        updateSeekBar();
        if (mMusicBound) {
            updateView(mService.getCurrentTrack());
            onBufferStopped();
        }
        receiverFilter.addAction(MyConstants.Media.ACTION_MEDIA_BUFFER_START);
        receiverFilter.addAction(MyConstants.Media.ACTION_MEDIA_BUFFER_STOP);
        receiverFilter.addAction(MyConstants.Media.ACTION_STATUS_PREPARED);
        receiverFilter.addAction(MyConstants.Media.ACTION_MEDIA_CHANGE);
        receiverFilter.addAction(MyConstants.Media.ACTION_PLAY_STATUS_CHANGE);
        receiverFilter.addAction(MyConstants.Media.ACTION_SHOW_HIDE_CONTROLS);
        registerReceiver(mediaReceiver, receiverFilter);
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

        pauseIcon = getResources().getDrawable(R.drawable.ic_pause);
        playIcon = getResources().getDrawable(R.drawable.ic_play);

        fab.setImageDrawable(pauseIcon);
        prev.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_skip_previous)
                .color(Color.WHITE)
                .sizeDp(14));
        next.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_skip_next)
                .color(Color.WHITE)
                .sizeDp(14));

        mediaReceiver = new MediaReceiver(this);
        receiverFilter = new IntentFilter();

        prev.setOnClickListener(this);
        fab.setOnClickListener(this);
        next.setOnClickListener(this);
        seekbar.setOnSeekBarChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.audio_detail, menu);

        menu.findItem(R.id.action_download).setIcon(new IconicsDrawable(this, GoogleMaterial.Icon
                .gmd_cloud_download)
                .color(Color.WHITE)
                .actionBar());

        menu.findItem(R.id.action_share).setIcon(new IconicsDrawable(this, GoogleMaterial.Icon
                .gmd_share).color(Color.WHITE)
                .actionBar());

        menu.findItem(R.id.action_share).getSubMenu().add(GROUP1, SUBMENU_BLUETOOTH, 1,
                getString(R.string.action_share_bluetooth));
        menu.findItem(R.id.action_share).getSubMenu().add(GROUP1, SUBMENU_FACEBOOK, 2, getString
                (R.string.action_share_facebook));
        showCaseView();
        return true;
    }

    private void initialize() {
        DaggerDataComponent.builder()
                .activityModule(getActivityModule())
                .applicationComponent(getApplicationComponent())
                .dataModule(new DataModule(mAudio.getId()))
                .build()
                .inject(this);
        mPresenter.attachView(this);
    }

    private void shareViaBluetooth() {
        initialize();
        mPresenter.initialize();
        mPresenter.shareViaBluetooth(mAudio);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share:
                break;
            case SUBMENU_BLUETOOTH:
                shareViaBluetooth();
                break;
            case SUBMENU_FACEBOOK:
                showShareDialog(mService.getCurrentTrack());
                break;
            case R.id.action_download:
                initialize();
                mPresenter.initialize();
                try {
                    if (!getPreferences().getDownloadReferences().contains(mAudio
                            .getDownloadReference())) {
                        mPresenter.downloadAudioPost(mAudio);
                    }
                } catch (IOException e) {
                    Logger.e("AudioDetailActivity_onOptionsItemSelected", "errorMessage: " + e
                            .getLocalizedMessage());
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        stopService(mPlayIntent);
        // Unbind from the service
        if (mMusicBound) {
            unbindService(mConnection);
            mMusicBound = false;
        }
        mService = null;
        super.onDestroy();
        if (mPresenter != null) mPresenter.destroy();
    }

    @Override
    public void onPause() {
        seekbar.removeCallbacks(updateSeekTime);
        if (mediaReceiver != null)
            unregisterReceiver(mediaReceiver);
        super.onPause();
        if (mPresenter != null) mPresenter.pause();
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
        Logger.d("AudioDetailActivity_onStopTrackingTouch", "test: " + seekbar.getProgress());
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
        Logger.d("AudioDetailActivity_onBufferStarted", "buffering start");
        disablePlayerControls();
    }

    @Override
    public void onBufferStopped() {
        Logger.d("AudioDetailActivity_onBufferStopped", "buffering stop");
        enablePlayerControls();
    }

    @Override
    public void onMediaPrepared() {
        enablePlayerControls();
        updateSeekBar();
    }

    @Override
    public void showHidePrevNext(boolean showPrev, boolean showNext) {
        Logger.d("AudioDetailActivity_showHidePrevNext", "test: " + showPrev + "/" + showNext);
        prev.setVisibility(showPrev ? View.VISIBLE : View.GONE);
        next.setVisibility(showNext ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onMediaChanged(Post pTrack) {
        mAudio = pTrack;
        updateView(pTrack);
        seekbar.setProgress(0);
        currentTime.setText("00:00");
        totalTime.setText("00:00");
        seekbarHandler.removeCallbacks(updateSeekTime);
    }

    @Override
    public void playStatusChanged(boolean pIsPlaying) {
        if (pIsPlaying) {
            fab.setImageDrawable(pauseIcon);
        } else {
            fab.setImageDrawable(playIcon);
        }
    }

    @Override
    public void onAudioDownloadStarted(String pMessage) {
        Snackbar.make(playerControls, pMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onAudioFileNotFoundToShare() {
        Snackbar.make(playerControls, getString(R.string.error_bluetooth_share), Snackbar
                .LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void updateView(Post pPost) {
        if (mAudio.getData().getThumbnail() != null) {
            mAlbumArt.setImageURI(Uri.parse(mAudio.getData().getThumbnail()));
        }
        title.setText(pPost.getTitle());
        StringBuilder sb = new StringBuilder();
        sb.append("<HTML><HEAD><LINK href=\"styles.css\" type=\"text/css\" rel=\"stylesheet\"/></HEAD><body>");
        sb.append(pPost.getDescription());
        sb.append("</body></HTML>");
        description.loadDataWithBaseURL("file:///android_asset/", sb.toString(), "text/html",
                "utf-8", null);
        //description.setText(Html.fromHtml(pPost.getDescription()));
    }

    private void disablePlayerControls() {
        bufferingText.setVisibility(View.VISIBLE);
        fab.setEnabled(false);
        //next.setEnabled(false);
        //prev.setEnabled(false);
        //seekbar.setEnabled(false);
    }

    private void enablePlayerControls() {
        bufferingText.setVisibility(View.GONE);
        fab.setEnabled(true);
        //next.setEnabled(true);
        //prev.setEnabled(true);
        //seekbar.setEnabled(true);
    }

    private void updateSeekBar() {
        seekbarHandler.removeCallbacks(updateSeekTime);
        seekbarHandler.postDelayed(updateSeekTime, 100);
    }

    public void showCaseView() {
        ShowcaseView menuShowcaseView = new ShowcaseView.Builder(this)
                .setStyle(R.style.showcase)
                .setContentText(getString(R.string.showcase_auido_download_detail))
                .setContentTitle(getString(R.string.showcase_audio_download_title))
                .withMaterialShowcase()
                .singleShot(MyConstants.ShowCase.AudioActivity)
                .setTarget(new Target() {
                    @Override
                    public Point getPoint() {
                        return new ViewTarget(getToolbar().findViewById(R.id.action_download)).getPoint();
                    }
                })
                .build();
    }
}
