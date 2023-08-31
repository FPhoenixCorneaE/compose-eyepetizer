package com.fphoenixcorneae.eyepetizer.mvi.ui.page.community

import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.fphoenixcorneae.common.ext.HHmmFormat
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.const.Constant
import com.fphoenixcorneae.eyepetizer.ext.DisposableLifecycleEffect
import com.fphoenixcorneae.eyepetizer.ext.clickableNoRipple
import com.fphoenixcorneae.eyepetizer.ext.toVideoDuration
import com.fphoenixcorneae.eyepetizer.mvi.model.CommunityReply
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavHostController
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black20
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Blue
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Gray
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Gray20
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.GrayDark
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.LocalThemeColors
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.AutoPlayVideoPlayer
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.SwipeRefresh
import com.fphoenixcorneae.eyepetizer.mvi.viewmodel.CommunityViewModel
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack

/**
 * @desc：社区-关注
 * @date：2023/08/23 14:13
 */
@Composable
fun FollowScreen() {
    val viewModel = viewModel<CommunityViewModel>()
    val communityFollows = viewModel.communityFollows.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()
    val firstVisibleItemIndex by remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }
    SwipeRefresh(
        lazyPagingItems = communityFollows,
        modifier = Modifier.fillMaxSize(),
        lazyListState = lazyListState,
    ) {
        item {
            CommunityFollowHeader()
        }
        items(communityFollows.itemCount) {
            val item = communityFollows[it] ?: return@items
            CommunityFollowItem(
                item = item,
                position = it,
                playTag = Constant.PlayTag.FOLLOW,
                isFocused = it == firstVisibleItemIndex,
            )
        }
    }
    DisposableLifecycleEffect(
        onStop = {
            GSYVideoManager.releaseAllVideos()
        },
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun CommunityFollowHeader() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickableNoRipple {
                NavHostController.navToLogin()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_avatar_gray_76dp),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 52.dp)
                .size(size = 76.dp),
        )
        Text(
            text = stringResource(R.string.follow_login_prompt),
            color = GrayDark,
            fontSize = 12.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
            modifier = Modifier
                .padding(top = 16.dp, bottom = 48.dp),
        )
        Divider(thickness = 8.dp, color = Gray)
    }
}

@Composable
fun CommunityFollowItem(
    item: CommunityReply.Item,
    position: Int = 0,
    playTag: String = "",
    isFocused: Boolean = false,
) {
    when (item.type) {
        "autoPlayFollowCard" -> if (item.data?.dataType == "FollowCard") AutoPlayFollowCard(
            item = item,
            position = position,
            playTag = playTag,
            isFocused = isFocused,
        )

        else -> {}
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun AutoPlayFollowCard(
    item: CommunityReply.Item = CommunityReply.Item(
        data = CommunityReply.Item.Data(
            content = CommunityReply.Item.Data.Content(
                data = CommunityReply.Item.Data.Content.Data(
                    author = CommunityReply.Item.Data.Content.Data.Author(
                        name = "天文一族",
                    ),
                    releaseTime = 1692861777000,
                    title = "跟随天文君一起旅行宇宙，探索未知的世界！",
                    description = "时隔六年，他的声音，再次唤醒了我们对平凡生活的向往。",
                    duration = 99,
                    consumption = CommunityReply.Item.Data.Content.Data.Consumption(
                        collectionCount = 63,
                        replyCount = 0,
                    )
                )
            )
        )
    ),
    position: Int = 0,
    playTag: String = "",
    isFocused: Boolean = false,
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    ConstraintLayout(
        modifier = Modifier
            .padding(start = 14.dp, top = 12.dp, end = 14.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickableNoRipple {
                NavHostController.navToVideoDetail(videoId = item.data?.content?.data?.id?.toString())
            },
    ) {
        val (avatar, avatarStar, nickname, releaseTime, title, description, video, duration, consumption, divider) = createRefs()
        // 头像
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.data?.run { header?.icon ?: content?.data?.author?.icon })
                .transformations(CircleCropTransformation())
                .placeholder(GradientDrawable().apply {
                    shape = GradientDrawable.OVAL
                    setColor(Gray20.toArgb())
                })
                .error(R.drawable.ic_avatar_gray_76dp)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(size = 40.dp)
                .border(width = 1.dp, color = Gray, shape = CircleShape)
                .constrainAs(avatar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            contentScale = ContentScale.FillBounds,
        )
        // 头像星星
        Image(
            painter = painterResource(id = R.drawable.ic_star_white_15dp),
            contentDescription = null,
            modifier = Modifier
                .size(size = 10.dp)
                .border(width = 1.dp, color = Gray, shape = CircleShape)
                .background(color = Blue, shape = CircleShape)
                .constrainAs(avatarStar) {
                    bottom.linkTo(avatar.bottom, margin = 1.dp)
                    end.linkTo(avatar.end, margin = 1.dp)
                },
        )
        // 昵称
        Text(
            text = item.data?.content?.data?.author?.name.orEmpty(),
            color = LocalThemeColors.current.textColor,
            fontSize = 14.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
            maxLines = 1,
            modifier = Modifier
                .constrainAs(nickname) {
                    top.linkTo(avatar.top, margin = 2.dp)
                    start.linkTo(avatar.end, margin = 12.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
        )
        // 发布时间
        Text(
            text = HHmmFormat.format(
                /* obj = */
                item.data?.content?.data?.releaseTime ?: item.data?.content?.data?.author?.latestReleaseTime
                ?: System.currentTimeMillis(),
            ) + stringResource(R.string.release),
            color = LocalThemeColors.current.textColorTertiary,
            fontSize = 12.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                ?.let { FontFamily(it) },
            modifier = Modifier
                .constrainAs(releaseTime) {
                    start.linkTo(nickname.start)
                    bottom.linkTo(avatar.bottom)
                },
        )
        // 标题
        Text(
            text = item.data?.content?.data?.title.orEmpty(),
            color = LocalThemeColors.current.textColor,
            fontSize = 12.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                ?.let { FontFamily(it) },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
            maxLines = 1,
            modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(releaseTime.end)
                    top.linkTo(releaseTime.top)
                    bottom.linkTo(releaseTime.bottom)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
        )
        // 描述
        Text(
            text = item.data?.content?.data?.description.orEmpty(),
            color = LocalThemeColors.current.textColor,
            fontSize = 14.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                ?.let { FontFamily(it) },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            lineHeight = 18.sp,
            modifier = Modifier
                .constrainAs(description) {
                    start.linkTo(parent.start)
                    top.linkTo(avatar.bottom, margin = 5.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
        )
        // 视频
        Card(
            modifier = Modifier
                .height(185.dp)
                .constrainAs(video) {
                    top.linkTo(description.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            shape = RoundedCornerShape(size = 4.dp)
        ) {
            AndroidView(
                factory = {
                    AutoPlayVideoPlayer(it)
                },
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                it.run {
                    // 防止错位设置
                    setPlayTag(playTag)
                    // 设置播放位置防止错位
                    playPosition = position
                    // 音频焦点冲突时是否释放
                    isReleaseWhenLossAudio = false
                    // 设置循环播放
                    isLooping = true
                    // 增加封面
                    thumbImageView = ImageView(context).apply {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        load(data = item.data?.content?.data?.cover?.feed) {
                            transformations(RoundedCornersTransformation(density.run { 4.dp.toPx() }))
                            placeholder(GradientDrawable().apply {
                                setColor(Gray20.toArgb())
                                cornerRadius = density.run { 4.dp.toPx() }
                            })
                            crossfade(true)
                        }
                        parent?.run { removeView(this@apply) }
                    }
                    // 设置播放过程中的回调
                    setVideoAllCallBack(object : GSYSampleCallBack() {
                        override fun onPrepared(url: String?, vararg objects: Any?) {
                            super.onPrepared(url, *objects)
                            // 是否需要静音
                            GSYVideoManager.instance().isNeedMute = true
                        }

                        override fun onClickBlank(url: String?, vararg objects: Any?) {
                            super.onClickBlank(url, *objects)
                        }
                    })
                    // 设置播放URL
                    setUp(item.data?.content?.data?.playUrl, false, null)
                    if (isFocused) {
                        // 完全可见时自动播放
                        startPlayLogic()
                    }
                }
            }
        }
        // 视频时长
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(color = Black20, shape = RoundedCornerShape(2.dp))
                .constrainAs(duration) {
                    end.linkTo(video.end, margin = 5.dp)
                    bottom.linkTo(video.bottom, margin = 5.dp)
                },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = item.data?.content?.data?.duration?.toVideoDuration().orEmpty(),
                color = Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ResourcesCompat.getFont(context, R.font.din_condensed_bold)
                    ?.let { FontFamily(it) },
                modifier = Modifier.padding(horizontal = 3.5.dp, vertical = 1.dp),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 35.dp)
                .constrainAs(consumption) {
                    top.linkTo(video.bottom)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // 收藏数
            Row(
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_favorite_border_white_20dp),
                    contentDescription = null,
                    modifier = Modifier.size(size = 20.dp),
                    colorFilter = ColorFilter.tint(color = LocalThemeColors.current.textColorTertiary),
                )
                Text(
                    text = "${item.data?.content?.data?.consumption?.collectionCount ?: 0}",
                    color = LocalThemeColors.current.textColorTertiary,
                    fontSize = 13.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                )
            }
            // 评论数
            Row(
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_reply_white_20dp),
                    contentDescription = null,
                    modifier = Modifier.size(size = 20.dp),
                    colorFilter = ColorFilter.tint(color = LocalThemeColors.current.textColorTertiary),
                )
                Text(
                    text = "${item.data?.content?.data?.consumption?.replyCount ?: 0}",
                    color = LocalThemeColors.current.textColorTertiary,
                    fontSize = 13.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                )
            }
            // 收藏
            Row(
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_star_white_20dp),
                    contentDescription = null,
                    modifier = Modifier.size(size = 20.dp),
                    colorFilter = ColorFilter.tint(color = LocalThemeColors.current.textColorTertiary),
                )
                Text(
                    text = stringResource(id = R.string.collect),
                    color = LocalThemeColors.current.textColorTertiary,
                    fontSize = 13.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                )
            }
            // 分享
            Row(
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_share_gray_20dp),
                    contentDescription = null,
                    modifier = Modifier.size(size = 28.dp),
                    colorFilter = ColorFilter.tint(color = LocalThemeColors.current.textColorTertiary),
                )
            }
        }
        // 分割线
        Divider(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .constrainAs(divider) {
                    top.linkTo(consumption.bottom, margin = 8.dp)
                    bottom.linkTo(parent.bottom)
                },
            color = Gray,
            thickness = 0.2.dp,
        )
    }
}
