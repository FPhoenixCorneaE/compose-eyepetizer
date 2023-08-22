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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import androidx.compose.ui.zIndex
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
import com.fphoenixcorneae.common.ext.view.gone
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.const.Constant
import com.fphoenixcorneae.eyepetizer.ext.DisposableLifecycleEffect
import com.fphoenixcorneae.eyepetizer.ext.clickableNoRipple
import com.fphoenixcorneae.eyepetizer.ext.toReplyTimeString
import com.fphoenixcorneae.eyepetizer.mvi.model.VideoCommentsReply
import com.fphoenixcorneae.eyepetizer.mvi.model.VideoDetailReply
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavHostController
import com.fphoenixcorneae.eyepetizer.mvi.ui.page.home.HomepageItem
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black45
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black50
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black55
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black60
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black85
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black95
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Blue
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Gray
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White10
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White20
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White25
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White35
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White80
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White85
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.DetailVideoPlayer
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.SystemUiScaffold
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.TypewriterText
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.lazyColumnFooter
import com.fphoenixcorneae.eyepetizer.mvi.viewmodel.VideoDetailAction
import com.fphoenixcorneae.eyepetizer.mvi.viewmodel.VideoDetailViewModel
import com.fphoenixcorneae.eyepetizer.mvi.viewmodel.VideoDetailViewModelFactory
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView
import com.zj.refreshlayout.RefreshHeaderState
import com.zj.refreshlayout.SwipeRefreshLayout
import com.zj.refreshlayout.SwipeRefreshStyle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @desc：视频详情
 * @date：2023/08/16 16:48
 */
@Composable
fun VideoDetailScreen(
    videoId: String = "0",
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current
    var initial by remember { mutableStateOf(false) }
    val viewModel: VideoDetailViewModel = viewModel(factory = VideoDetailViewModelFactory(videoId = videoId))
    val videoDetailUiState by viewModel.videoDetailUiState.collectAsState()
    val videoComments = viewModel.videoComments.collectAsLazyPagingItems()
    val statusBarColor by animateColorAsState(
        targetValue = if (initial) Black else Color.Transparent,
        animationSpec = tween(durationMillis = 600)
    )
    var videoPlayer by remember { mutableStateOf<DetailVideoPlayer?>(null) }
    var orientationUtils: OrientationUtils? = null
    var delayHideBackJob: Job? = null
    var delayHideBottomJob: Job? = null
    var visibleBack by remember { mutableStateOf(false) }
    var visibleCollect by remember { mutableStateOf(false) }
    var visibleShare by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = videoId) {
        // singleTop模式，需要手动刷新Ui
        viewModel.updateVideoId(videoId)
        viewModel.dispatchIntent(VideoDetailAction.Initial)
    }
    LaunchedEffect(key1 = videoDetailUiState.videoDetailReply?.playUrl, key2 = videoPlayer) {
        // 播放地址和播放器同时不为null
        if (videoDetailUiState.videoDetailReply?.playUrl == null || videoPlayer == null) {
            return@LaunchedEffect
        }
        // 增加封面
        videoPlayer?.thumbImageView = ImageView(context).apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            load(data = videoDetailUiState.videoDetailReply?.cover?.detail) {
                placeholder(GradientDrawable().apply {
                    setColor(Black.toArgb())
                    cornerRadius = density.run { 4.dp.toPx() }
                })
                crossfade(true)
            }
            setOnClickListener {
                if (videoPlayer?.currentPlayer?.currentState != GSYVideoView.CURRENT_STATE_AUTO_COMPLETE) {
                    delayHideBackJob?.cancel()
                    if (!visibleBack) {
                        delayHideBackJob = coroutineScope.launch(Dispatchers.Main) {
                            delay(videoPlayer?.dismissControlTime?.toLong() ?: 3000)
                            visibleBack = false
                            visibleCollect = false
                        }
                    }
                    visibleBack = !visibleBack
                    visibleCollect = !visibleCollect
                }
            }
        }
        // 设置播放URL
        videoPlayer?.setUp(
            /* url = */ videoDetailUiState.videoDetailReply?.playUrl,
            /* cacheWithPlay = */ false,
            /* title = */ videoDetailUiState.videoDetailReply?.title,
        )
        // 开始播放
        videoPlayer?.startPlayLogic()
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
                onDestroy = {
                    GSYVideoManager.releaseAllVideos()
                    orientationUtils?.releaseListener()
                    orientationUtils = null
                    videoPlayer?.release()
                    videoPlayer?.setVideoAllCallBack(null)
                    videoPlayer = null
                }
            ) {
                initial = true
            }
            // 播放器
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            ) {
                AndroidView(
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
                            isShowFullAnimation = true

                            videoPlayer = this
                            // 外部辅助的旋转，帮助全屏
                            orientationUtils = OrientationUtils(context.getActivity(), this)

                            // 设置播放过程中的回调
                            videoPlayer?.setVideoAllCallBack(object : GSYSampleCallBack() {
                                override fun onStartPrepared(url: String?, vararg objects: Any?) {
                                    super.onStartPrepared(url, *objects)
                                    visibleBack = false
                                    visibleCollect = false
                                    visibleShare = false
                                }

                                override fun onClickBlank(url: String?, vararg objects: Any?) {
                                    super.onClickBlank(url, *objects)
                                    if (videoPlayer?.currentPlayer?.currentState != GSYVideoView.CURRENT_STATE_AUTO_COMPLETE) {
                                        delayHideBackJob?.cancel()
                                        if (!visibleBack) {
                                            delayHideBackJob = coroutineScope.launch(Dispatchers.Main) {
                                                delay(videoPlayer?.dismissControlTime?.toLong() ?: 3000)
                                                visibleBack = false
                                                visibleCollect = false
                                            }
                                        }
                                        visibleBack = !visibleBack
                                        visibleCollect = !visibleCollect
                                    }
                                }

                                override fun onClickStop(url: String?, vararg objects: Any?) {
                                    super.onClickStop(url, *objects)
                                    delayHideBottomJob?.cancel()
                                    delayHideBottomJob = coroutineScope.launch(Dispatchers.Main) {
                                        delay(videoPlayer?.dismissControlTime?.toLong() ?: 3000)
                                        videoPlayer?.getBottomContainer()?.gone()
                                        videoPlayer?.startButton?.gone()
                                    }
                                }

                                override fun onAutoComplete(url: String?, vararg objects: Any?) {
                                    super.onAutoComplete(url, *objects)
                                    delayHideBackJob?.cancel()
                                    visibleBack = true
                                    visibleCollect = false
                                    visibleShare = true
                                }
                            })
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                )
                VideoPlayerControllerUi(
                    visibleBack = visibleBack,
                    visibleCollect = visibleCollect,
                    visibleShare = visibleShare,
                )
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
                // 是否显示打字机效果
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
                    lazyColumnFooter(videoComments)
                }
            }
            // 底部评论Ui
            BottomCommentUi(replyCount = videoDetailUiState.videoDetailReply?.consumption?.replyCount ?: 0)
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

@Preview
@Composable
fun VideoPlayerControllerUi(
    visibleBack: Boolean = true,
    visibleCollect: Boolean = true,
    visibleShare: Boolean = true,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        AnimatedVisibility(
            visible = visibleBack,
            enter = fadeIn(tween(1000)),
            exit = fadeOut(tween(1000)),
            modifier = Modifier.align(alignment = Alignment.TopStart),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_pull_down_black_24dp),
                contentDescription = null,
                modifier = Modifier
                    .padding(14.dp)
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
            visible = visibleCollect,
            enter = fadeIn(tween(1000)),
            exit = fadeOut(tween(1000)),
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .padding(all = 10.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_favorite_border_white_20dp),
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                        .padding(4.dp),
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_share_white_20dp),
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                        .padding(4.dp),
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_more_white_20dp),
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                        .padding(4.dp),
                )
            }
        }
        AnimatedVisibility(
            visible = visibleShare,
            enter = fadeIn(tween(1000)),
            exit = fadeOut(tween(1000)),
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(bottom = 8.dp),
        ) {
            Row(
                modifier = Modifier
                    .background(color = Black60, shape = RoundedCornerShape(size = 4.dp))
                    .padding(all = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 2.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_share_wechat_white_24dp),
                    contentDescription = null,
                    modifier = Modifier
                        .size(44.dp)
                        .padding(8.dp),
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_share_wechat_moments_white_24dp),
                    contentDescription = null,
                    modifier = Modifier
                        .size(44.dp)
                        .padding(8.dp),
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_share_weibo_white_24dp),
                    contentDescription = null,
                    modifier = Modifier
                        .size(44.dp)
                        .padding(8.dp),
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_share_qq_white_24dp),
                    contentDescription = null,
                    modifier = Modifier
                        .size(44.dp)
                        .padding(8.dp),
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_share_qq_zone_white_24dp),
                    contentDescription = null,
                    modifier = Modifier
                        .size(44.dp)
                        .padding(8.dp),
                )
            }
        }
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
                        .border(width = 1.dp, color = White80, shape = CircleShape)
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
                        .border(width = 1.dp, color = Gray, shape = CircleShape)
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
        item.type == "textCard" && item.data?.type == "header4" -> TextCardHeader4(item = item)

        item.type == "reply" && item.data?.dataType == "ReplyBeanForClient" -> ReplyBeanForClient(item = item)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun TextCardHeader4(
    item: VideoCommentsReply.Item = VideoCommentsReply.Item(
        data = VideoCommentsReply.Item.Data(
            text = "最新评论",
            actionUrl = Constant.ActionUrl.REPLIES_HOT,
        ),
    ),
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        val (title, arrow) = createRefs()
        // 标题
        Text(
            text = item.data?.text.orEmpty(),
            color = White,
            fontSize = 14.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
            modifier = Modifier.constrainAs(title) {
                top.linkTo(
                    parent.top,
                    margin = if (item.data?.actionUrl?.startsWith(Constant.ActionUrl.REPLIES_HOT) == true) 30.dp else 24.dp,
                )
                start.linkTo(parent.start, margin = 14.dp)
                bottom.linkTo(parent.bottom, margin = 6.dp)
            },
        )
        // 箭头, 热门评论显示, 相关推荐、最新评论不显示
        if (item.data?.actionUrl?.startsWith(Constant.ActionUrl.REPLIES_HOT) == true) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_right_gray_24dp),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .constrainAs(arrow) {
                        top.linkTo(title.top)
                        bottom.linkTo(title.bottom)
                        start.linkTo(title.end, margin = 5.dp)
                    },
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun ReplyBeanForClient(
    item: VideoCommentsReply.Item = VideoCommentsReply.Item(
        data = VideoCommentsReply.Item.Data(
            user = VideoCommentsReply.Item.Data.User(
                nickname = "幻想里",
            ),
            likeCount = 0,
            message = "太酷了",
            parentReply = VideoCommentsReply.Item.Data.ParentReply(
                user = VideoCommentsReply.Item.Data.User(
                    nickname = "狼爱上羊",
                ),
                message = "我与大海的浪漫约定",
            ),
            createTime = 1692685876000,
        ),
    ),
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        // 头像
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.data?.user?.avatar)
                .transformations(CircleCropTransformation())
                .error(R.drawable.ic_avatar_gray_76dp)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 14.dp, top = 14.dp)
                .size(size = 40.dp)
                .border(width = 1.dp, color = White80, shape = CircleShape),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .width(0.dp)
                .weight(1f)
                .padding(start = 12.dp, top = 14.dp, end = 14.dp)
                .wrapContentHeight(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ) {
                // 昵称
                Text(
                    text = item.data?.user?.nickname.orEmpty(),
                    color = White,
                    fontSize = 13.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                        ?.let { FontFamily(it) },
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false,
                    maxLines = 1,
                    modifier = Modifier
                        .width(width = 0.dp)
                        .weight(weight = 1f)
                        .padding(top = 2.dp, end = 12.dp),
                )
                // 点赞
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(space = 5.dp),
                ) {
                    // 点赞数
                    Text(
                        text = "${item.data?.likeCount ?: 0}",
                        color = White,
                        fontSize = 13.sp,
                        fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                            ?.let { FontFamily(it) },
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = false,
                        maxLines = 1,
                        modifier = Modifier.padding(top = 4.dp),
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_like_white_20dp),
                        contentDescription = null,
                        modifier = Modifier.size(size = 20.dp),
                    )
                }
            }
            // 回复用户
            AnimatedVisibility(
                visible = item.data?.parentReply != null,
                enter = fadeIn(animationSpec = snap()),
                exit = fadeOut(animationSpec = snap()),
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ) {
                Text(
                    text = String.format(
                        format = stringResource(R.string.comment_reply_user_format),
                        item.data?.parentReply?.user?.nickname.orEmpty(),
                    ),
                    color = White35,
                    fontSize = 12.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                )
            }
            // 消息
            Text(
                text = item.data?.message.orEmpty(),
                color = White,
                fontSize = 13.sp,
                fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                    ?.let { FontFamily(it) },
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )
            // 回复对象
            AnimatedVisibility(
                visible = item.data?.parentReply != null,
                enter = fadeIn(animationSpec = snap()),
                exit = fadeOut(animationSpec = snap()),
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = Black85, shape = RoundedCornerShape(size = 4.dp))
                        .padding(vertical = 12.dp),
                ) {
                    val (replyAvatar, replyNickname, replyMessage) = createRefs()
                    // 回复用户头像
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(data = item.data?.parentReply?.user?.avatar)
                            .transformations(CircleCropTransformation())
                            .error(R.drawable.ic_avatar_gray_76dp)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(size = 40.dp)
                            .border(width = 1.dp, color = Gray, shape = CircleShape)
                            .constrainAs(replyAvatar) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start, margin = 8.dp)
                            },
                        contentScale = ContentScale.Crop,
                    )
                    // 回复用户昵称
                    Text(
                        text = item.data?.parentReply?.user?.nickname.orEmpty(),
                        color = White10,
                        fontSize = 13.sp,
                        fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                            ?.let { FontFamily(it) },
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = false,
                        maxLines = 1,
                        modifier = Modifier
                            .constrainAs(replyNickname) {
                                top.linkTo(replyAvatar.top, margin = 2.dp)
                                start.linkTo(replyAvatar.end, margin = 12.dp)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            },
                    )
                    // 回复消息
                    Text(
                        text = item.data?.parentReply?.message.orEmpty(),
                        color = White25,
                        fontSize = 12.sp,
                        fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                            ?.let { FontFamily(it) },
                        modifier = Modifier.constrainAs(replyMessage) {
                            top.linkTo(replyNickname.bottom, margin = 12.dp)
                            start.linkTo(replyNickname.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        },
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // 查看对话
                AnimatedVisibility(
                    visible = item.data?.parentReply != null,
                    enter = fadeIn(animationSpec = snap()),
                    exit = fadeOut(animationSpec = snap()),
                    modifier = Modifier.padding(end = 20.dp),
                ) {
                    Text(
                        text = stringResource(R.string.view_conversation),
                        color = White25,
                        fontSize = 12.sp,
                        fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                            ?.let { FontFamily(it) },
                    )
                }
                // 回复
                Text(
                    text = stringResource(R.string.reply),
                    color = White25,
                    fontSize = 12.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                        ?.let { FontFamily(it) },
                )
                // 时间
                Text(
                    text = item.data?.createTime?.toReplyTimeString().orEmpty(),
                    color = White20,
                    fontSize = 12.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                    modifier = Modifier.padding(start = 20.dp),
                )
                Spacer(
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1f),
                )
                // 更多
                Image(
                    painter = painterResource(id = R.drawable.ic_more_horizontal_white_24dp),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp),
                )
            }

            // 分割线
            Divider(
                thickness = 0.2.dp, color = White85,
                modifier = Modifier.padding(top = 6.dp),
            )
        }
    }
}

@Preview
@Composable
fun BottomCommentUi(
    replyCount: Int = 0,
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = Black45)
            .zIndex(zIndex = 1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo_gray_76dp),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 14.dp)
                .size(size = 40.dp)
                .background(color = White, shape = CircleShape)
                .border(width = 1.dp, color = Black55, shape = CircleShape),
        )
        // 发表评论
        Box(
            modifier = Modifier
                .width(0.dp)
                .weight(1f)
                .height(40.dp)
                .background(color = Black95, shape = RoundedCornerShape(size = 2.dp))
                .border(width = 1.dp, color = White80, shape = RoundedCornerShape(size = 2.dp))
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = stringResource(R.string.send_comment_hint),
                color = White20,
                fontSize = 12.sp,
                fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                    ?.let { FontFamily(it) },
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_reply_white_20dp),
            contentDescription = null,
            modifier = Modifier
                .size(size = 20.dp),
        )
        Text(
            text = replyCount.toString(),
            color = White20,
            fontSize = 12.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                ?.let { FontFamily(it) },
            modifier = Modifier.padding(end = 14.dp),
        )
    }
}

