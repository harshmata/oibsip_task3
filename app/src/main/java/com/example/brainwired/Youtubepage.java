package com.example.brainwired;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class Youtubepage extends AppCompatActivity {

    YouTubePlayerView youTubePlayerView;
    Button homebtn;
    TextView desc;

    private FullScreenHelper fullScreenHelper = new FullScreenHelper(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_activity);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("yt_details");
        String videoId = bundle.getString("vidid");
        String description = bundle.getString("desc");

        homebtn = (Button) findViewById(R.id.returnn);
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        desc = findViewById(R.id.tag_state_description);
        desc.setText(description);
        getLifecycle().addObserver(youTubePlayerView);


        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0);
            }
        });


        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iintent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(iintent);
            }
        });

    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            youTubePlayerView.enterFullScreen();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            youTubePlayerView.exitFullScreen();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        youTubePlayerView.release();
    }


    public class FullScreenHelper {

        private Activity context;
        private View[] views;


        public FullScreenHelper(Activity context, View... views) {
            this.context = context;
            this.views = views;
        }


        public void enterFullScreen() {
            View decorView = context.getWindow().getDecorView();

            hideSystemUi(decorView);

            for (View view : views) {
                view.setVisibility(View.GONE);
                view.invalidate();
            }
        }


        public void exitFullScreen() {
            View decorView = context.getWindow().getDecorView();

            showSystemUi(decorView);

            for (View view : views) {
                view.setVisibility(View.VISIBLE);
                view.invalidate();
            }
        }

        private void hideSystemUi(View mDecorView) {
            mDecorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

        private void showSystemUi(View mDecorView) {
            mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }


}
