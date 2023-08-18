package com.fphoenixcorneae.eyepetizer.mvi.ui.page.detail

import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.fphoenixcorneae.common.ext.getActivity
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.const.Constant
import com.fphoenixcorneae.eyepetizer.ext.DisposableLifecycleEffect
import com.fphoenixcorneae.eyepetizer.mvi.model.VideoCommentsReply
import com.fphoenixcorneae.eyepetizer.mvi.model.VideoDetailReply
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavHostController
import com.fphoenixcorneae.eyepetizer.mvi.ui.page.home.HomepageItem
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black50
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black55
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black85
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Blue
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Gray
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White20
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White35
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White85
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.DetailVideoPlayer
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.SystemUiScaffold
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.TypewriterText
import com.fphoenixcorneae.eyepetizer.mvi.viewmodel.VideoDetailAction
import com.fphoenixcorneae.eyepetizer.mvi.viewmodel.VideoDetailViewModel
import com.fphoenixcorneae.eyepetizer.mvi.viewmodel.VideoDetailViewModelFactory
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.zj.refreshlayout.RefreshHeaderState
import com.zj.refreshlayout.SwipeRefreshLayout
import com.zj.refreshlayout.SwipeRefreshStyle
import kotlinx.coroutines.delay

/**
 * @desc：视频详情
 * @date：2023/08/16 16:48
 */
@Composable
fun VideoDetailScreen(
    videoId: String = "0",
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    var initial by remember { mutableStateOf(false) }
    val viewModel: VideoDetailViewModel = viewModel(factory = VideoDetailViewModelFactory(videoId = videoId))
    val videoDetailUiState by viewModel.videoDetailUiState.collectAsState()
    val videoComments = viewModel.videoComments.collectAsLazyPagingItems()
    val statusBarColor by animateColorAsState(
        targetValue = if (initial) Black else Color.Transparent,
        animationSpec = tween(durationMillis = 600)
    )
    var videoPlayer: DetailVideoPlayer? = null
    var orientationUtils: OrientationUtils? = null
    LaunchedEffect(key1 = videoId) {
        // singleTop模式，需要手动刷新Ui
        viewModel.updateVideoId(videoId)
        viewModel.dispatchIntent(VideoDetailAction.Initial)
    }
    SystemUiScaffold(statusBarColor = statusBarColor, isDarkFont = false) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = videoDetailUiState.videoDetailReply?.cover?.blurred)
                .error(R.mipmap.img_bg_splash)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Black50)
        ) {
            DisposableLifecycleEffect(
                onPause = {
                    videoPlayer?.onVideoPause()
                },
                onResume = {
                    videoPlayer?.onVideoResume()
                    videoPlayer?.onConfigurationChanged(
                        /* activity = */ context.getActivity(),
                        /* newConfig = */ configuration,
                        /* orientationUtils = */ orientationUtils,
                        /* hideActionBar = */ true,
                        /* hideStatusBar = */ true,
                    )
                },
            ) {
                initial = true
            }
            DisposableEffect(
                key1 = AndroidView(
                    factory = {
                        DetailVideoPlayer(context).apply {
                            // 设置全屏按键功能，这是使用的是选择屏幕，而不是全屏
                            fullscreenButton.setOnClickListener {
                                orientationUtils?.resolveByClick()
                                startWindowFullscreen(context, true, true)
                            }
                            // 防止错位设置
                            playTag = Constant.PlayTag.VIDEO_DETAIL
                            // 音频焦点冲突时是否释放
                            isReleaseWhenLossAudio = false
                            // 是否开启自动旋转
                            isRotateViewAuto = false
                            // 是否需要全屏锁定屏幕功能
                            isNeedLockFull = true
                            // 是否可以滑动调整
                            setIsTouchWiget(true)
                            // 设置触摸显示控制ui的消失时间
                            dismissControlTime = 5000
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                ) {
                    videoPlayer = it
                    // 外部辅助的旋转，帮助全屏
                    orientationUtils = OrientationUtils(context.getActivity(), it)
                    it.run {
                        // 增加封面
                        thumbImageView = ImageView(context).apply {
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            load(data = videoDetailUiState.videoDetailReply?.cover?.detail) {
                                placeholder(GradientDrawable().apply {
                                    setColor(Black.toArgb())
                                    cornerRadius = density.run { 4.dp.toPx() }
                                })
                                crossfade(true)
                            }
                            setOnClickListener {

                            }
                        }
                        //设置播放过程中的回调
                        setVideoAllCallBack(object : GSYSampleCallBack() {
                            override fun onStartPrepared(url: String?, vararg objects: Any?) {
                                super.onStartPrepared(url, *objects)
                            }

                            override fun onClickBlank(url: String?, vararg objects: Any?) {
                                super.onClickBlank(url, *objects)
                            }

                            override fun onClickStop(url: String?, vararg objects: Any?) {
                                super.onClickStop(url, *objects)
                            }

                            override fun onAutoComplete(url: String?, vararg objects: Any?) {
                                super.onAutoComplete(url, *objects)
                            }
                        })
                        // 设置播放URL
                        setUp(
                            /* url = */ videoDetailUiState.videoDetailReply?.playUrl,
                            /* cacheWithPlay = */ false,
                            /* title = */ videoDetailUiState.videoDetailReply?.title,
                        )
                        // 开始播放
                        startPlayLogic()
                    }
                },
            ) {
                onDispose {
                    GSYVideoManager.releaseAllVideos()
                    orientationUtils?.releaseListener()
                    orientationUtils = null
                    videoPlayer?.release()
                    videoPlayer?.setVideoAllCallBack(null)
                    videoPlayer = null
                }
            }
            SwipeRefreshLayout(
                isRefreshing = false,
                onRefresh = {
                    NavHostController.get().navigateUp()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 0.dp)
                    .weight(weight = 1f)
                    .background(color = Black55),
                swipeStyle = SwipeRefreshStyle.FixedBehind,
                indicator = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .alpha(if (it.headerState == RefreshHeaderState.ReleaseToRefresh) 1f else 0f),
                    ) {
                        Text(
                            text = stringResource(R.string.pull_down_to_close_page),
                            color = White,
                            fontSize = 10.sp,
                            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                                ?.let { FontFamily(it) },
                            modifier = Modifier.align(alignment = Alignment.BottomCenter),
                        )
                    }
                }
            ) {
                var showAnimated by remember { mutableStateOf(true) }
                LaunchedEffect(key1 = videoId) {
                    showAnimated = true
                    delay(1000)
                    showAnimated = false
                }
                LazyColumn {
                    item {
                        VideoInfo(videoDetailReply = videoDetailUiState.videoDetailReply, showAnimated = showAnimated)
                    }
                    item {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(15.dp)
                        )
                    }
                    items(videoDetailUiState.videoRelatedReply?.itemList?.size ?: 0) {
                        val item = videoDetailUiState.videoRelatedReply?.itemList?.getOrNull(it) ?: return@items
                        HomepageItem(item = item, position = it, playTag = Constant.PlayTag.VIDEO_DETAIL)
                    }
                    items(videoComments.itemCount) {
                        val item = videoComments[it] ?: return@items
                        VideoCommentsItem(item = item)
                    }
                }
            }
        }
    }
    BackHandler {
        orientationUtils?.backToProtVideo()
        if (GSYVideoManager.backFromWindowFull(context)) {
            return@BackHandler
        }
        NavHostController.get().navigateUp()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun VideoInfo(
    videoDetailReply: VideoDetailReply? = VideoDetailReply(
        title = "迪拜：时光驻留，未来之城",
        library = "DAILY",
        category = "旅行",
        description = "每当谈起迪拜，第一印象会是奢侈得流油，在经济繁荣的同时，迪拜也有它的另一副面孔。跟随创作者的镜头来到这座辉煌的「未来之城」迪拜，饱览一场城市与大自然的盛宴。迪拜是阿拉伯联合酋长国的瑰宝，也是中东地区的经济和金融中心，是一座散发着富裕、宏伟和创新气息的城市，从高耸的摩天大楼和繁华度假村到原始海滩和文化宝藏，这座城市总能给人留下深刻印象，现代奇迹与古老传统在这里交相辉映。 From Dimitri Fafutis",
        consumption = VideoDetailReply.Consumption(
            collectionCount = 126,
            shareCount = 59,
        ),
        author = VideoDetailReply.Author(
            name = "全球旅行视频精选",
            description = "在这个世界上的很多地方都有着美丽的景色，我们用相机用镜头记录下这一切。",
        ),
    ),
    showAnimated: Boolean = false,
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        val (videoInfo, author) = createRefs()
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = Black85)
                .constrainAs(videoInfo) {
                    top.linkTo(parent.top)
                },
        ) {
            val (title, category, description, consumption) = createRefs()
            // 标题
            TypewriterText(
                animatedText = videoDetailReply?.title.orEmpty(),
                color = White,
                fontSize = 15.sp,
                duration = 1000,
                fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                    ?.let { FontFamily(it) },
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 14.dp)
                    end.linkTo(parent.end, margin = 14.dp)
                    width = Dimension.fillToConstraints
                },
                showAnimated = showAnimated,
            )
            // 分类
            TypewriterText(
                animatedText = if (videoDetailReply?.library == "DAILY") "#${videoDetailReply.category} / 开眼精选" else "#${videoDetailReply?.category}",
                color = White35,
                fontSize = 12.sp,
                duration = 1000,
                fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                    ?.let { FontFamily(it) },
                modifier = Modifier.constrainAs(category) {
                    top.linkTo(title.bottom, margin = 8.dp)
                    start.linkTo(title.start)
                    end.linkTo(title.end)
                    width = Dimension.fillToConstraints
                },
                showAnimated = showAnimated,
            )
            // 描述
            TypewriterText(
                animatedText = videoDetailReply?.description.orEmpty(),
                color = White20,
                fontSize = 13.sp,
                duration = 1000,
                fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                    ?.let { FontFamily(it) },
                lineHeight = 18.sp,
                modifier = Modifier.constrainAs(description) {
                    top.linkTo(category.bottom, margin = 18.dp)
                    start.linkTo(title.start)
                    end.linkTo(title.end)
                    width = Dimension.fillToConstraints
                },
                showAnimated = showAnimated,
            )
            // consumption
            Row(
                modifier = Modifier.constrainAs(consumption) {
                    top.linkTo(description.bottom, margin = 16.dp)
                    start.linkTo(title.start)
                    end.linkTo(title.end)
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                    width = Dimension.fillToConstraints
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_favorite_border_white_20dp),
                    contentDescription = null,
                    modifier = Modifier
                        .size(size = 20.dp),
                )
                Text(
                    text = "${videoDetailReply?.consumption?.collectionCount ?: 0}",
                    color = White35,
                    fontSize = 13.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                    modifier = Modifier
                        .width(0.dp)
                        .weight(weight = 1f)
                        .padding(top = 4.dp),
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_share_white_20dp),
                    contentDescription = null,
                    modifier = Modifier
                        .size(size = 20.dp),
                )
                Text(
                    text = "${videoDetailReply?.consumption?.shareCount ?: 0}",
                    color = White35,
                    fontSize = 13.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                    modifier = Modifier
                        .width(0.dp)
                        .weight(weight = 1f)
                        .padding(top = 4.dp),
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_cache_white_20dp),
                    contentDescription = null,
                    modifier = Modifier
                        .size(size = 20.dp),
                )
                Text(
                    text = stringResource(R.string.cache),
                    color = White35,
                    fontSize = 13.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                    modifier = Modifier
                        .width(0.dp)
                        .weight(weight = 1f)
                        .padding(top = 4.dp),
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_star_white_20dp),
                    contentDescription = null,
                    modifier = Modifier
                        .size(size = 20.dp),
                )
                Text(
                    text = stringResource(R.string.collect),
                    color = White35,
                    fontSize = 13.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                    modifier = Modifier
                        .width(0.dp)
                        .weight(weight = 1f)
                        .padding(top = 4.dp),
                )
            }
        }
        AnimatedVisibility(
            visible = videoDetailReply?.author != null,
            enter = fadeIn(animationSpec = snap()),
            exit = fadeOut(animationSpec = snap()),
            modifier = Modifier
                .constrainAs(author) {
                    top.linkTo(videoInfo.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ) {
                val (avatar, avatarStar, name, description, follow, divider) = createRefs()
                // 头像
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(data = videoDetailReply?.author?.icon)
                        .transformations(CircleCropTransformation())
                        .error(R.drawable.ic_avatar_gray_76dp)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(size = 40.dp)
                        .border(width = 0.2.dp, color = Gray, shape = CircleShape)
                        .constrainAs(avatar) {
                            top.linkTo(parent.top, margin = 14.dp)
                            start.linkTo(parent.start, margin = 14.dp)
                        },
                    contentScale = ContentScale.FillBounds,
                )
                // 头像星星
                Image(
                    painter = painterResource(id = R.drawable.ic_star_white_15dp),
                    contentDescription = null,
                    modifier = Modifier
                        .size(size = 10.dp)
                        .border(width = 0.2.dp, color = Gray, shape = CircleShape)
                        .background(color = Blue, shape = CircleShape)
                        .constrainAs(avatarStar) {
                            bottom.linkTo(avatar.bottom, margin = 1.dp)
                            end.linkTo(avatar.end, margin = 1.dp)
                        },
                )
                // 作者名称
                Text(
                    text = videoDetailReply?.author?.name.orEmpty(),
                    color = White,
                    fontSize = 14.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                        ?.let { FontFamily(it) },
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false,
                    maxLines = 1,
                    modifier = Modifier
                        .constrainAs(name) {
                            top.linkTo(avatar.top, margin = 2.dp)
                            start.linkTo(avatar.end, margin = 12.dp)
                            end.linkTo(description.end)
                            width = Dimension.fillToConstraints
                        },
                )
                // 作者描述
                Text(
                    text = videoDetailReply?.author?.description.orEmpty(),
                    color = White35,
                    fontSize = 13.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false,
                    maxLines = 1,
                    modifier = Modifier
                        .constrainAs(description) {
                            top.linkTo(name.bottom, margin = 2.dp)
                            start.linkTo(name.start)
                            end.linkTo(follow.start, margin = 14.dp)
                            width = Dimension.fillToConstraints
                        },
                )
                // 关注
                Box(
                    modifier = Modifier
                        .width(44.dp)
                        .height(22.dp)
                        .border(
                            width = 0.2.dp,
                            color = White,
                            shape = RoundedCornerShape(2.dp),
                        )
                        .constrainAs(follow) {
                            top.linkTo(avatar.top)
                            end.linkTo(parent.end, margin = 14.dp)
                            bottom.linkTo(avatar.bottom)
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(R.string.plus_follow),
                        color = White,
                        fontSize = 10.sp,
                        fontFamily = ResourcesCompat.getFont(
                            context,
                            R.font.fz_lan_ting_hei_s_db1_gb_regular
                        )?.let { FontFamily(it) },
                    )
                }
                // 分割线
                Divider(
                    modifier = Modifier
                        .constrainAs(divider) {
                            top.linkTo(avatar.bottom, margin = 15.dp)
                        },
                    color = White85,
                    thickness = 0.2.dp,
                )
            }
        }
    }
}

@Composable
fun VideoCommentsItem(item: VideoCommentsReply.Item) {
    when {
        item.type == "textCard" && item.data?.dataType == "header4" -> {

        }

        item.type == "reply" && item.data?.dataType == "ReplyBeanForClient" -> {

        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun TextCardHeader4() {

}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun ReplyBeanForClient() {

}

