package com.arpan.alosproject.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.arpan.alosproject.R;
import com.arpan.alosproject.adapters.PlayListAdapter;
import com.arpan.alosproject.model.firebase.AllCourse;
import com.arpan.alosproject.util.YoutubeConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.awt.font.TextAttribute;

import static com.arpan.alosproject.util.Others.POSITION;

public class CourseActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, PlayListAdapter.OnItemClickListener {

    private static final String TAG = "CourseActivity";

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener mOnInitializedListener;
    private YouTubePlayer mPlayer;
    private AllCourse course;

    private TextView titleText;

    private RecyclerView recyclerView;
    private PlayListAdapter playListAdapter;

    private int curPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Intent intent = getIntent();
        course = (AllCourse) intent.getSerializableExtra("COURSE");

        youTubePlayerView = findViewById(R.id.youtubePlayerView);
        youTubePlayerView.initialize(YoutubeConfig.getApiKey(), this);

        titleText = findViewById(R.id.titleText);
        titleText.setText(course.getCourseName());

        recyclerView = findViewById(R.id.playList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        playListAdapter = new PlayListAdapter(this, course.courseVideo, this);
        recyclerView.setAdapter(playListAdapter);

        Log.d(TAG, "onCreate: " + course.courseVideo.size());
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        mPlayer = youTubePlayer;

        youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);
        youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);

        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);

//        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);

        POSITION = 0;
        youTubePlayer.loadVideo(course.courseVideo.get(POSITION).videoUrl);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {
            POSITION++;
            Log.d(TAG, "onVideoEnded: Called");
            if(course.courseVideo.size() > POSITION) {
                mPlayer.loadVideo(course.courseVideo.get(POSITION).videoUrl);
            } 
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    @Override
    public void onItemClick(int position) {
        mPlayer.loadVideo(course.courseVideo.get(position).videoUrl);
        POSITION  = position;
    }

    @Override
    protected void onDestroy() {
        mPlayer.release();
        super.onDestroy();
    }
}