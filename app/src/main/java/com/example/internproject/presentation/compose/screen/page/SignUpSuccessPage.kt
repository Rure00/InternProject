package com.example.internproject.presentation.compose.screen.page

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.internproject.R
import com.example.internproject.ui.theme.TossBlue
import com.example.internproject.ui.theme.Typography
import com.example.internproject.ui.theme.White

@Composable
fun SignUpSuccessPage(
    onBack: () -> Unit
) {
    val bitmap: MutableState<Bitmap?> = remember { mutableStateOf(null) }
    val source = "https://cdn-icons-mp4.flaticon.com/512/6569/6569153.mp4"
    val iconModifier = Modifier.height(100.dp).width(80.dp)

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Glide.with(LocalContext.current)
            .asBitmap()
            .load(source)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmap.value = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            bitmap.value?.asImageBitmap()?.let { fetchedBitmap ->
                Image(
                    bitmap = fetchedBitmap,
                    contentDescription = null,
                    modifier = iconModifier
                )
            } ?: Image(
                painter = painterResource(id = R.drawable.empty_gif),
                contentDescription = null,
                modifier = iconModifier
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "가입이 완료되었습니다.",
            style = Typography.bodyLarge,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = stringResource(R.string.signup_success_guide_str),
            style = Typography.bodySmall,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp).background(color = TossBlue, shape = RoundedCornerShape(10.dp))
                .clickable {
                    onBack()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.back_str),
                style = Typography.bodyMedium,
                color = White,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}