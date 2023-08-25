package com.fphoenixcorneae.eyepetizer.mvi.ui.page.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.fphoenixcorneae.common.ext.getFriendlyTimeSpanByNow
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.mvi.model.PushMessageReply
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Gray
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.LocalThemeColors
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.SwipeRefresh
import com.fphoenixcorneae.eyepetizer.mvi.viewmodel.NotificationViewModel

/**
 * @desc：通知-推送
 * @date：2023/08/24 17:26
 */
@Composable
fun PushScreen() {
    val viewModel = viewModel<NotificationViewModel>()
    val pushMessages = viewModel.pushMessages.collectAsLazyPagingItems()
    SwipeRefresh(lazyPagingItems = pushMessages) {
        items(pushMessages.itemCount) {
            val item = pushMessages[it] ?: return@items
            NotificationPushItem(item)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun NotificationPushItem(
    item: PushMessageReply.Message = PushMessageReply.Message(
        title = "官方通知",
        date = 1692929273000,
        content = "你的乐队=你现在的心情+刚刚吃的东西",
    ),
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        val (avatar, title, time, content, arrow, divider) = createRefs()

        // 头像
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.icon)
                .transformations(CircleCropTransformation())
                .error(R.mipmap.ic_launcher)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(size = 20.dp)
                .border(width = 1.dp, color = Gray, shape = CircleShape)
                .background(color = White, shape = CircleShape)
                .constrainAs(avatar) {
                    top.linkTo(parent.top, margin = 14.dp)
                    start.linkTo(parent.start, margin = 14.dp)
                },
            contentScale = ContentScale.FillBounds,
        )
        // 标题
        Text(
            text = item.title.orEmpty(),
            color = LocalThemeColors.current.textColor,
            fontSize = 14.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(avatar.end, margin = 12.dp)
                    top.linkTo(avatar.top)
                    end.linkTo(arrow.start, margin = 4.dp)
                    width = Dimension.fillToConstraints
                },
        )
        // 时间
        Text(
            text = item.date.getFriendlyTimeSpanByNow(),
            color = LocalThemeColors.current.textColorTertiary,
            fontSize = 12.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                ?.let { FontFamily(it) },
            modifier = Modifier
                .constrainAs(time) {
                    start.linkTo(title.start)
                    top.linkTo(title.bottom, margin = 6.dp)
                },
        )
        // 内容
        Text(
            text = item.content.orEmpty(),
            color = LocalThemeColors.current.textColorSecondary,
            fontSize = 12.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                ?.let { FontFamily(it) },
            modifier = Modifier
                .constrainAs(content) {
                    start.linkTo(title.start)
                    top.linkTo(time.bottom, margin = 8.dp)
                    end.linkTo(title.end)
                    width = Dimension.fillToConstraints
                },
        )
        // 箭头
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_right_gray_24dp),
            contentDescription = null,
            modifier = Modifier
                .size(size = 44.dp)
                .padding(all = 12.dp)
                .constrainAs(arrow) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
            colorFilter = ColorFilter.tint(color = LocalThemeColors.current.textColorSecondary),
        )
        // 分割线
        Divider(
            thickness = 0.2.dp, color = Gray,
            modifier = Modifier.constrainAs(divider) {
                top.linkTo(content.bottom, margin = 14.dp)
                start.linkTo(title.start)
                end.linkTo(title.end)
                width = Dimension.fillToConstraints
            },
        )
    }
}
