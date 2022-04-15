package jamoliddin.tj.testvideoplayer

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.video.VideoSize


@Composable
fun VideoPlayer(
    sourceUrl: String,
    onSizeChanged: (Dp) -> Unit
) {
    val context = LocalContext.current

    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build()
    }

    LaunchedEffect(sourceUrl) {
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, context.packageName)
        )

        val source = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(
                MediaItem.fromUri(
                    Uri.parse(
                        sourceUrl
                    )
                )
            )

        exoPlayer.setMediaSource(source)
        exoPlayer.prepare()
//        exoPlayer.addListener(object : Player.Listener {
//            override fun onVideoSizeChanged(videoSize: VideoSize) {
//                val height = videoSize.height
//                if (height >= 200) {
//                    onSizeChanged(height.dp)
//                }
//                super.onVideoSizeChanged(videoSize)
//            }
//
//            override fun onPlaybackStateChanged(state: Int) {
//                if (state == Player.STATE_ENDED) {
//                    Toast.makeText(context, "End", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onPlayerError(error: PlaybackException) {
//                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
//                super.onPlayerError(error)
//            }
//        })
    }

    ExoPlayer(
        exoPlayer = exoPlayer
    )

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.pause()
            exoPlayer.release()
        }
    }
}


@Composable
fun ExoPlayer(
    exoPlayer: SimpleExoPlayer
) {
    val context = LocalContext.current
    val playerView = remember {
        val layout = LayoutInflater.from(context).inflate(
            R.layout.video_player,
            null,
            true
        )

        val playerView = layout.findViewById(R.id.playerView) as PlayerView
        playerView.apply {
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            player = exoPlayer
            player?.playWhenReady = true
        }
    }

    AndroidView({ playerView })
}

















//import android.net.Uri
//import android.os.Bundle
//import android.widget.TextView
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.viewinterop.AndroidView
//import com.google.android.exoplayer2.ExoPlayer
//import com.google.android.exoplayer2.MediaItem
//import com.google.android.exoplayer2.SimpleExoPlayer
//import com.google.android.exoplayer2.source.ProgressiveMediaSource
//import com.google.android.exoplayer2.ui.PlayerView
//import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
//import com.google.android.exoplayer2.util.Util
//
//
//// Creating a composable
//// function to display Top Bar
//@Composable
//fun MainContent() {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("GFG | ExoPlayer Video", color = Color.White) },
//                backgroundColor = Color(0xff0f9d58)
//            )
//        },
//        content = {
//            MyContent()
//        }
//    )
//}
//
//// Creating a composable function to create
//// two Images and a spacer between them
//// Calling this function as content in the above function
//@Composable
//fun MyContent() {
//
//    Column(
//        Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//
//        // Fetching the Local Context
//        val mContext = LocalContext.current
//
//        // Declaring a string value
//        // that stores raw video url
//        val mVideoUrl =
//            "http://api.fenix.colibri.tj/storage/media/gZIxjFE4LffCIPhSkC4OODA0hpTuWkjNxuvbPSo1.mp4"
//
//        val media =
//            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
//
//        val mediaItem = MediaItem.fromUri(Uri.parse(media))
//
//        // Declaring ExoPlayer
//        val mExoPlayer = remember(mContext) {
//            ExoPlayer.Builder(mContext).build().apply {
//                val dataSourceFactory = DefaultDataSourceFactory(
//                    mContext,
//                    Util.getUserAgent(mContext, mContext.packageName)
//                )
//                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
//                    .createMediaSource(mediaItem)
//                prepare(source)
//            }
//        }
//
//        // Implementing ExoPlayer
//        AndroidView(factory = { context ->
//            PlayerView(context).apply {
//                player = mExoPlayer
//            }
//        })
//    }
//}
//
//// For displaying preview in
//// the Android Studio IDE emulator
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MainContent()
//}
//
//
//@Composable
//fun MyPlayer() {
//    val sampleVideo =
//        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
//    val context = LocalContext.current
//    val player = SimpleExoPlayer.Builder(context).build()
//    val playerView = PlayerView(context)
//    val mediaItem = MediaItem.fromUri(sampleVideo)
//    val playWhenReady = rememberSaveable {
//        mutableStateOf(true)
//    }
//    player.setMediaItem(mediaItem)
//    playerView.player = player
//    LaunchedEffect(player) {
//        player.prepare()
//        player.playWhenReady = playWhenReady.value
//
//    }
//    AndroidView(factory = {
//        playerView
//    })
//}