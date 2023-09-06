package com.fphoenixcorneae.eyepetizer.mvi.ui.page.detail

import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
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
import coil.compose.AsyncImage
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.const.Constant
import com.fphoenixcorneae.eyepetizer.ext.DisposableLifecycleEffect
import com.fphoenixcorneae.eyepetizer.ext.clickableNoRipple
import com.fphoenixcorneae.eyepetizer.mvi.model.CommunityReply
import com.fphoenixcorneae.eyepetizer.mvi.ui.effect.EffectScreen
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavHostController
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black50
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Blue
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Gray
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Gray20
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White60
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.SystemUiScaffold
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.UgcDetailVideoPlayer
import com.fphoenixcorneae.eyepetizer.mvi.viewmodel.UgcDetailAction
import com.fphoenixcorneae.eyepetizer.mvi.viewmodel.UgcDetailViewModel
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack

/**
 * @desc：社区-推荐详情
 * @date：2023/08/29 13:42
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UgcDetailScreen(
    id: Int,
) {
    val viewModel = viewModel<UgcDetailViewModel>()
    val communityCommends by viewModel.communityCommends.collectAsState()
    // 筛选数据
    val pageDatas = communityCommends?.filter { it.type == "communityColumnsCard" && it.data?.dataType == "FollowCard" }
    val currentPage = pageDatas?.indexOfFirst { it.id == id } ?: 0
    val pagerState = rememberPagerState(initialPage = currentPage.coerceAtLeast(0))
    SystemUiScaffold(statusBarsPadding = false, isDarkFont = false) {
        EffectScreen(
            onRefresh = {
                viewModel.dispatchIntent(UgcDetailAction.Refresh)
            },
        ) {
            VerticalPager(
                pageCount = pageDatas?.size ?: 0,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Black)
                    .statusBarsPadding(),
                state = pagerState,
            ) {
                val item = pageDatas?.getOrNull(it) ?: return@VerticalPager
                UgcDetailItem(
                    viewModel = viewModel,
                    item = item,
                    position = it,
                    isFocused = it == pagerState.currentPage
                )
            }
        }
    }
    DisposableLifecycleEffect(
        onResume = {
            GSYVideoManager.onResume()
        },
        onPause = {
            GSYVideoManager.onPause()
        },
        onStop = {
            GSYVideoManager.releaseAllVideos()
        },
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UgcDetailItem(
    viewModel: UgcDetailViewModel = UgcDetailViewModel(),
    item: CommunityReply.Item = CommunityReply.Item(
        data = CommunityReply.Item.Data(
            content = CommunityReply.Item.Data.Content(
                data = CommunityReply.Item.Data.Content.Data(
                    library = "DAILY",
                    description = "时隔六年，他的声音，再次唤醒了我们对平凡生活的向往。",
                    tags = buildList {
                        add(CommunityReply.Item.Data.Content.Data.Tag(name = "摄影师日常"))
                    },
                    urls = listOf("", ""),
                    owner = CommunityReply.Item.Data.Content.Data.Owner(
                        nickname = "乌苏少年",
                        expert = true,
                    ),
                    consumption = CommunityReply.Item.Data.Content.Data.Consumption(
                        collectionCount = 63,
                    ),
                ),
                type = "ugcPicture",
            ),
        ),
    ),
    position: Int = 0,
    isFocused: Boolean = false,
) {
    val pagerState = rememberPagerState()
    // 视频
    UgcDetailVideo(
        item = item,
        position = position,
        isFocused = isFocused,
    ) {
        viewModel.dispatchIntent(UgcDetailAction.ToggleBackAndUgcInfoVisibility)
    }
    // 图片画廊
    UgcDetailPhotos(
        item = item,
        pagerState = pagerState,
    ) {
        viewModel.dispatchIntent(UgcDetailAction.ToggleBackAndUgcInfoVisibility)
    }
    UgcDetailCover(
        item = item,
        pagerState = pagerState,
        viewModel = viewModel
    )
}

@Composable
private fun UgcDetailVideo(
    item: CommunityReply.Item,
    position: Int,
    isFocused: Boolean,
    onClickBlank: () -> Unit = {},
) {
    val context = LocalContext.current
    AnimatedVisibility(
        visible = item.data?.content?.type == "video",
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier.fillMaxSize(),
    ) {
        AndroidView(
            factory = {
                UgcDetailVideoPlayer(it)
            },
            modifier = Modifier.fillMaxSize(),
        ) {
            it.run {
                // 防止错位设置
                playTag = Constant.PlayTag.UGC_DETAIL
                // 设置播放位置防止错位
                playPosition = position
                // 音频焦点冲突时是否释放
                isReleaseWhenLossAudio = false
                // 设置循环播放
                isLooping = true
                // 增加封面
                thumbImageView = ImageView(context).apply {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    load(data = item.data?.content?.data?.cover?.detail) {
                        placeholder(GradientDrawable().apply {
                            setColor(Black.toArgb())
                        })
                        crossfade(true)
                    }
                    parent?.run { removeView(this@apply) }
                }
                setThumbPlay(true)
                setIsTouchWiget(false)
                // 设置播放过程中的回调
                setVideoAllCallBack(object : GSYSampleCallBack() {
                    override fun onClickBlank(url: String?, vararg objects: Any?) {
                        super.onClickBlank(url, *objects)
                        onClickBlank()
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
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun UgcDetailPhotos(
    item: CommunityReply.Item,
    pagerState: PagerState,
    onClick: () -> Unit = {},
) {
    AnimatedVisibility(
        visible = item.data?.content?.type == "ugcPicture",
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier.fillMaxSize(),
    ) {
        HorizontalPager(
            pageCount = item.data?.content?.data?.urls?.size ?: 0,
            state = pagerState,
            modifier = Modifier
                .padding(bottom = 50.dp)
                .fillMaxSize(),
        ) {
            AsyncImage(
                model = item.data?.content?.data?.urls?.getOrNull(it),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clickableNoRipple {
                        onClick()
                    },
                contentScale = ContentScale.FillWidth,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun UgcDetailCover(
    item: CommunityReply.Item,
    pagerState: PagerState,
    viewModel: UgcDetailViewModel,
) {
    val context = LocalContext.current
    val ugcDetailUiState by viewModel.ugcDetailUiState.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        // 顶部
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 60.dp),
        ) {
            AnimatedVisibility(
                visible = ugcDetailUiState.visibleBackAndUgcInfo,
                enter = fadeIn(animationSpec = tween(durationMillis = 500)),
                exit = fadeOut(animationSpec = tween(durationMillis = 500)),
                modifier = Modifier
                    .padding(start = 14.dp)
                    .align(alignment = Alignment.CenterStart),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_pull_down_black_24dp),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .background(color = Gray, shape = CircleShape)
                        .border(width = 4.dp, color = Color.Transparent, shape = CircleShape)
                        .clickableNoRipple {
                            NavHostController
                                .get()
                                .navigateUp()
                        },
                    colorFilter = ColorFilter.tint(color = Black),
                )
            }
            AnimatedVisibility(
                visible = (item.data?.content?.data?.urls?.size ?: 0) > 1,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .align(alignment = Alignment.Center),
            ) {
                Text(
                    text = String.format(
                        format = stringResource(R.string.photo_gallery_count_format),
                        pagerState.currentPage + 1,
                        item.data?.content?.data?.urls?.size,
                    ),
                    color = White,
                    fontSize = 11.sp,
                    modifier = Modifier
                        .background(color = Black50, shape = RoundedCornerShape(size = 4.dp))
                        .padding(horizontal = 5.dp, vertical = 2.dp),
                    fontFamily = ResourcesCompat.getFont(
                        context, R.font.fz_lan_ting_hei_s_db1_gb_regular
                    )?.let { FontFamily(it) },
                )
            }
        }
        // 底部
        AnimatedVisibility(
            visible = ugcDetailUiState.visibleBackAndUgcInfo,
            enter = fadeIn(animationSpec = tween(durationMillis = 500)),
            exit = fadeOut(animationSpec = tween(durationMillis = 500)),
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = Black50)
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .padding(start = 14.dp, top = 14.dp, end = 14.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                ) {
                    val (avatar, avatarStar, nickname, privateLetter, follow) = createRefs()
                    // 头像
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(data = item.data?.content?.data?.owner?.avatar)
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
                    if (item.data?.content?.data?.owner?.expert == true) {
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
                    }
                    // 昵称
                    Text(
                        text = item.data?.content?.data?.owner?.nickname.orEmpty(),
                        color = White,
                        fontSize = 14.sp,
                        fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                            ?.let { FontFamily(it) },
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = false,
                        maxLines = 1,
                        modifier = Modifier
                            .constrainAs(nickname) {
                                start.linkTo(avatar.end, margin = 12.dp)
                                top.linkTo(avatar.top, margin = 2.dp)
                                end.linkTo(privateLetter.start, margin = 12.dp)
                                bottom.linkTo(avatar.bottom)
                                width = Dimension.fillToConstraints
                            },
                    )
                    // 私信
                    Box(
                        modifier = Modifier
                            .width(44.dp)
                            .height(22.dp)
                            .background(color = Blue, shape = RoundedCornerShape(4.dp))
                            .constrainAs(privateLetter) {
                                top.linkTo(avatar.top)
                                end.linkTo(follow.start, margin = 14.dp)
                                bottom.linkTo(avatar.bottom)
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(R.string.private_letter),
                            color = White,
                            fontSize = 10.sp,
                            fontFamily = ResourcesCompat.getFont(
                                context, R.font.fz_lan_ting_hei_s_db1_gb_regular
                            )?.let { FontFamily(it) },
                        )
                    }
                    // 关注
                    Box(
                        modifier = Modifier
                            .width(44.dp)
                            .height(22.dp)
                            .border(
                                width = 0.2.dp,
                                color = White,
                                shape = RoundedCornerShape(4.dp),
                            )
                            .constrainAs(follow) {
                                top.linkTo(avatar.top)
                                end.linkTo(parent.end)
                                bottom.linkTo(avatar.bottom)
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(R.string.plus_follow),
                            color = White,
                            fontSize = 10.sp,
                            fontFamily = ResourcesCompat.getFont(
                                context, R.font.fz_lan_ting_hei_s_db1_gb_regular
                            )?.let { FontFamily(it) },
                        )
                    }
                }
                // 描述
                if (item.data?.content?.data?.description?.isNotBlank() == true) {
                    Text(
                        text = item.data.content.data.description,
                        color = White,
                        fontSize = 13.sp,
                        fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                            ?.let { FontFamily(it) },
                        modifier = Modifier
                            .padding(start = 14.dp, top = 8.dp, end = 14.dp)
                            .fillMaxWidth(),
                    )
                }
                // 标签
                if (!item.data?.content?.data?.tags.isNullOrEmpty()) {
                    Text(
                        text = item.data?.content?.data?.tags?.firstOrNull()?.name.orEmpty(),
                        color = White,
                        fontSize = 12.sp,
                        fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                            ?.let { FontFamily(it) },
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(start = 14.dp, top = 8.dp, end = 14.dp)
                            .background(color = White60, shape = RoundedCornerShape(size = 4.dp))
                            .padding(horizontal = 5.dp, vertical = 2.dp),
                    )
                }
                Divider(thickness = 0.2.dp, color = White60, modifier = Modifier.padding(top = 14.dp))
                Row(
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .fillMaxWidth()
                        .height(height = 50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(space = 20.dp),
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
                            colorFilter = ColorFilter.tint(color = White),
                        )
                        Text(
                            text = "${item.data?.content?.data?.consumption?.collectionCount ?: 0}",
                            color = White,
                            fontSize = 13.sp,
                            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
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
                            colorFilter = ColorFilter.tint(color = White),
                        )
                        Text(
                            text = "${item.data?.content?.data?.consumption?.replyCount ?: 0}",
                            color = White,
                            fontSize = 13.sp,
                            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
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
                            colorFilter = ColorFilter.tint(color = White),
                        )
                        Text(
                            text = stringResource(id = R.string.collect),
                            color = White,
                            fontSize = 13.sp,
                            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                                ?.let { FontFamily(it) },
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .width(width = 0.dp)
                            .weight(weight = 1f),
                    )
                    // 分享
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_share_gray_20dp),
                            contentDescription = null,
                            modifier = Modifier.size(size = 28.dp),
                            colorFilter = ColorFilter.tint(color = White),
                        )
                    }
                }
            }
        }
    }
}
