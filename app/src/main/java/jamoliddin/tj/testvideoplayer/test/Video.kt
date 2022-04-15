package jamoliddin.tj.testvideoplayer.test

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import jamoliddin.tj.testvideoplayer.R

@SuppressLint("InflateParams")
@Composable
fun Video() {

    val sourceUrl =
        "https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4"
    val context = LocalContext.current

    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, context.packageName)
            )

            val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(
                    MediaItem.fromUri(
                        Uri.parse(
                            "http://api.fenix.colibri.tj/storage/media/gZIxjFE4LffCIPhSkC4OODA0hpTuWkjNxuvbPSo1.mp4"
                        )
                    )
                )

            this.prepare(source)
        }
    }

    Box(modifier = Modifier.width(400.dp).height(400.dp)) {
        AndroidView({
            LayoutInflater.from(context).inflate(R.layout.test_layout, null, true)
        }) {
            val exoPlayerView = it.findViewById<PlayerView>(R.id.test_layout)

            exoPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            exoPlayerView.player = exoPlayer
            exoPlayer.playWhenReady = true
        }
    }

}