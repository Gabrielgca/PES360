package br.com.vr.pes360;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView;


public class PlayerActivity extends AppCompatActivity {
    public static final String SPHERICAL_STEREO_MODE_EXTRA = "spherical_stereo_mode";
    public static final String SPHERICAL_STEREO_MODE_MONO = "mono";
    public static final String SPHERICAL_STEREO_MODE_TOP_BOTTOM = "top_bottom";
    public static final String SPHERICAL_STEREO_MODE_LEFT_RIGHT = "left_right";

    private SimpleExoPlayer player;
    private PlayerView playerView;

    protected String userAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("TYPE_ON_CREATE", "Tipo TYPE_ON_CREATE!!");
        userAgent  = Util.getUserAgent(this, "PES360");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player_layout);
        playerView = findViewById(R.id.player_view);
        playerView.requestFocus();
    }

    private void showToast(int messageId) {
        showToast(getString(messageId));
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player == null) {
            initializePlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void initializePlayer() {
        if (player == null) {
            player = new SimpleExoPlayer.Builder(this).build();
            SphericalGLSurfaceView sphericalGLSurfaceView = new SphericalGLSurfaceView(this);

            MediaItem mediaItem = new MediaItem.Builder()
                    .setUri("http://gpds.ene.unb.br/videos/normal/clans_4320x2160_30_3100000.mp4")
                    .build();

            player.setMediaItem(mediaItem);
            String sphericalStereoMode = getIntent().getStringExtra(SPHERICAL_STEREO_MODE_EXTRA);

            if (sphericalStereoMode != null) {
                int stereoMode;
                switch (sphericalStereoMode) {
                    case SPHERICAL_STEREO_MODE_MONO:
                        stereoMode = C.STEREO_MODE_MONO;
                        break;
                    case SPHERICAL_STEREO_MODE_TOP_BOTTOM:
                        stereoMode = C.STEREO_MODE_TOP_BOTTOM;
                        break;
                    case SPHERICAL_STEREO_MODE_LEFT_RIGHT:
                        stereoMode = C.STEREO_MODE_LEFT_RIGHT;
                        break;
                    default:
                        showToast(R.string.error_unrecognized_stereo_mode);
                        finish();
                        return;
                }
                sphericalGLSurfaceView.setDefaultStereoMode(stereoMode);
            }

            player.setVideoSurfaceView(sphericalGLSurfaceView);
            player.prepare();
        }

        playerView.setPlayer(player);
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
