package jamoliddin.tj.testvideoplayer

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.view.View
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import jamoliddin.tj.testvideoplayer.test.Video

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun VideoSection() {
        Video()
}




@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun BoxScope.Controls(
    onFullScreenClicked: () -> Unit,
    isFullScreen: Boolean
) {

    Box(modifier = Modifier.matchParentSize()) {

        IconButton(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = onFullScreenClicked
        ) {
            Icon(
                imageVector = if (isFullScreen) Icons.Default.Check
                else Icons.Default.Search,
                tint = Color.White,
                contentDescription = null
            )
        }
    }
}


@Composable
fun SetScreen(isFullScreen: Boolean) {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        val orientation = if (isFullScreen) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        activity.requestedOrientation = orientation

        if (isFullScreen) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        } else activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

