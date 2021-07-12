package br.com.vr.pes360;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Util;
import java.io.File;


public class PlayerActivity extends AppCompatActivity {


    public static final String SPHERICAL_STEREO_MODE_EXTRA = "spherical_stereo_mode";
    public static final String SPHERICAL_STEREO_MODE_MONO = "mono";
    public static final String SPHERICAL_STEREO_MODE_TOP_BOTTOM = "top_bottom";
    public static final String SPHERICAL_STEREO_MODE_LEFT_RIGHT = "left_right";

    private boolean playWhenReady = true;
    private static final String DOWNLOAD_CONTENT_DIRECTORY = "downloads";

    private Cache downloadCache;
    private SimpleExoPlayer player;
    private PlayerView playerView;
    private DataSource.Factory dataSourceFactory;

    protected String userAgent;

    private File downloadDirectory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("TYPE_ON_CREATE", "Tipo TYPE_ON_CREATE!!");
        //String sphericalStereoMode = getIntent().getStringExtra(SPHERICAL_STEREO_MODE_EXTRA);
        userAgent  = Util.getUserAgent(this, "PES360");
//        if (sphericalStereoMode != null) {
//            setTheme(R.style.PlayerTheme_Spherical);
//        }
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
        if (Build.VERSION.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((Build.VERSION.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT > 23) {
            releasePlayer();
        }
    }

//    private MediaSource buildMediaSource(Uri uri) {
//        return buildMediaSource(uri, null);
//    }
//
//    private MediaSource buildMediaSource(Uri uri, @Nullable String overrideExtension) {
//        @C.ContentType int type = Util.inferContentType(uri, overrideExtension);
//        switch (type) {
//            case C.TYPE_DASH:
//                Log.d("TYPE_DASH", "Tipo Dash!!");
//                return new DashMediaSource.Factory(dataSourceFactory)
//                        .setManifestParser(
//                                new FilteringManifestParser<>(new DashManifestParser(), getOfflineStreamKeys(uri)))
//                        .createMediaSource(uri);
//            case C.TYPE_SS:
//                Log.d("TYPE_SS", "Tipo SS!!");
//                return new SsMediaSource.Factory(dataSourceFactory)
//                        .setManifestParser(
//                                new FilteringManifestParser<>(new SsManifestParser(), getOfflineStreamKeys(uri)))
//                        .createMediaSource(uri);
//            case C.TYPE_HLS:
//                Log.d("TYPE_HLS", "Tipo HLS!!");
//                return new HlsMediaSource.Factory(dataSourceFactory)
//                        .setPlaylistParserFactory(
//                                new DefaultHlsPlaylistParserFactory(getOfflineStreamKeys(uri)))
//                        .createMediaSource(uri);
//            case C.TYPE_OTHER:
//                Log.d("TYPE_OTHER", "Tipo OTHER!!");
//                return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
//            default: {
//                Log.d("TYPE_DEFAULT", "Tipo DEFAULT!!");
//                throw new IllegalStateException("Unsupported type: " + type);
//            }
//        }
//    }

    private void initializePlayer() {
        if (player == null) {



            player = new SimpleExoPlayer.Builder(this).build();
            SphericalGLSurfaceView sphericalGLSurfaceView = new SphericalGLSurfaceView(this);

            MediaItem mediaItem = new MediaItem.Builder()
                    .setUri("http://gpds.ene.unb.br/videos/normal/clans_4320x2160_30_3100000.mp4")
                    .build();

            player.setMediaItem(mediaItem);
            //Uri uri = Uri.parse("https://storage.googleapis.com/exoplayer-test-media-1/360/sphericalv2.mp4");
            //https://storage.googleapis.com/exoplayer-test-media-1/360/iceland0.ts
            //https://storage.googleapis.com/exoplayer-test-media-1/360/congo.mp4
            //https://storage.googleapis.com/exoplayer-test-media-1/360/sphericalv2.mp4
            //MediaSource mediaSource = buildMediaSource(uri);

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
            //player.setPlayWhenReady(playWhenReady);
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
