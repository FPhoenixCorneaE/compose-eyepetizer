package com.fphoenixcorneae.eyepetizer.mvi.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.ext.clickableNoRipple
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavHostController
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Gray
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.LocalThemeColors

/**
 * @desc：工具栏
 * @date：2023/04/21 11:47
 */
@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    background: Color = LocalThemeColors.current.backgroundColor,
    leftIconTint: Color? = null,
    titleText: String = "",
    titleTextColor: Color = Color.Black,
    rightText: String? = null,
    rightIcon: Any? = null,
    onRightTextClick: () -> Unit = {},
    onRightIconClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .height(height = 48.dp)
            .background(color = background),
    ) {
        AsyncImage(
            model = R.drawable.ic_back_black_44dp,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 20.dp)
                .size(24.dp)
                .align(alignment = Alignment.CenterStart)
                .clickableNoRipple {
                    NavHostController
                        .get()
                        .navigateUp()
                },
            colorFilter = leftIconTint?.run { ColorFilter.tint(this) },
        )
        Text(
            text = titleText,
            color = titleTextColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .widthIn(0.dp, 200.dp)
                .align(alignment = Alignment.Center),
        )
        rightText?.let {
            Text(
                text = it,
                color = Color.Black,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .align(alignment = Alignment.CenterEnd)
                    .clickableNoRipple {
                        onRightTextClick()
                    },
            )
        }
        rightIcon?.let {
            AsyncImage(
                model = it,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .size(24.dp)
                    .align(alignment = Alignment.CenterEnd)
                    .clickableNoRipple {
                        onRightIconClick()
                    },
            )
        }
        Divider(
            thickness = 0.5.dp, color = Gray, modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewToolbar() {
    Toolbar(titleText = "标题", rightText = "测试")
}