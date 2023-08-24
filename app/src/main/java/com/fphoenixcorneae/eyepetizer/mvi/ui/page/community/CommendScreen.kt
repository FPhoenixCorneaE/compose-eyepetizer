package com.fphoenixcorneae.eyepetizer.mvi.ui.page.community

import android.graphics.drawable.GradientDrawable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.snap
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.mvi.model.CommunityReply
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black55
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black70
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black80
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Gray
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Gray20
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.LocalThemeColors
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White50
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White80
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.BannerViewPager
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.SwipeRefreshStaggeredGrid
import com.fphoenixcorneae.eyepetizer.mvi.viewmodel.CommunityViewModel

/**
 * @desc：社区-推荐
 * @date：2023/08/23 14:14
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommendScreen() {
    val viewModel = viewModel<CommunityViewModel>()
    val communityCommends = viewModel.communityCommends.collectAsLazyPagingItems()
    SwipeRefreshStaggeredGrid(
        lazyPagingItems = communityCommends,
        modifier = Modifier.fillMaxSize(),
        horizontalItemSpacing = 6.dp,
        verticalItemSpacing = 14.dp,
        contentPadding = PaddingValues(all = 14.dp),
    ) {
        items(
            count = communityCommends.itemCount,
            span = {
                if (communityCommends[it]?.type == "horizontalScrollCard") {
                    StaggeredGridItemSpan.FullLine
                } else {
                    StaggeredGridItemSpan.SingleLane
                }
            },
        ) {
            val item = communityCommends[it] ?: return@items
            CommunityCommendItem(item = item)
        }
    }
}

@Composable
fun CommunityCommendItem(
    item: CommunityReply.Item,
) {
    when (item.type) {
        "horizontalScrollCard" -> when (item.data?.dataType) {
            "HorizontalScrollCard" -> CommunityHorizontalScrollCard(item)
            "ItemCollection" -> CommunityHorizontalScrollCardItemCollection(item)
            else -> {}
        }

        "communityColumnsCard" -> if (item.data?.dataType == "FollowCard") CommunityFollowCard(item)

        else -> {}
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun CommunityHorizontalScrollCard(
    item: CommunityReply.Item = CommunityReply.Item(
        data = CommunityReply.Item.Data(
            itemList = buildList {
                repeat(4) {
                    CommunityReply.Item.Data.Item()
                }
            },
            label = CommunityReply.Item.Data.Item.Data.Label(
                text = "广告",
            ),
        )
    ),
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(208.dp),
    ) {
        BannerViewPager(
            data = item.data?.itemList,
            imageModel = { item ->
                ImageRequest.Builder(context)
                    .data(item?.data?.image)
                    .transformations(RoundedCornersTransformation(density.run { 4.dp.toPx() }))
                    .placeholder(GradientDrawable().apply {
                        setColor(Gray20.toArgb())
                        cornerRadius = density.run { 4.dp.toPx() }
                    })
                    .crossfade(true)
                    .build()
            },
            modifier = Modifier.fillMaxSize(),
            canLoop = false,
            isInfinite = false,
            contentPadding = PaddingValues(horizontal = 0.dp),
            pageSpacing = 4.dp,
            showIndicator = false,
        ) {
            // 标签
            AnimatedVisibility(
                visible = !item.data?.label?.text.isNullOrEmpty(),
                enter = fadeIn(animationSpec = snap()),
                exit = fadeOut(animationSpec = snap()),
                modifier = Modifier
                    .align(alignment = Alignment.TopEnd)
                    .padding(all = 8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .width(36.dp)
                        .height(20.dp)
                        .border(width = 0.2.dp, color = White80, shape = RoundedCornerShape(2.dp))
                        .background(color = Black70, shape = RoundedCornerShape(2.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = item.data?.label?.text.orEmpty(),
                        color = White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                            ?.let { FontFamily(it) },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun CommunityHorizontalScrollCardItemCollection(
    item: CommunityReply.Item = CommunityReply.Item(
        data = CommunityReply.Item.Data(
            itemList = buildList {
                repeat(4) {
                    CommunityReply.Item.Data.Item(
                        data = CommunityReply.Item.Data.Item.Data(
                            title = "主题创作广场",
                            subTitle = "发布你的作品和日常",
                        )
                    )
                }
            },
        )
    ),
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 65.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 6.dp),
    ) {
        items(count = item.data?.itemList?.size ?: 0) {
            Box(
                modifier = Modifier
                    .width(width = (screenWidth - 14.dp * 2 - 6.dp) / 2)
                    .fillMaxHeight(),
            ) {
                // 背景图片
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(item.data?.itemList?.getOrNull(it)?.data?.bgPicture)
                        .transformations(RoundedCornersTransformation(density.run { 4.dp.toPx() }))
                        .placeholder(GradientDrawable().apply {
                            setColor(Gray20.toArgb())
                            cornerRadius = density.run { 4.dp.toPx() }
                        })
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    // 标题
                    Text(
                        text = item.data?.itemList?.getOrNull(it)?.data?.title.orEmpty(),
                        color = White,
                        fontSize = 14.sp,
                        fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                            ?.let { FontFamily(it) },
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    // 副标题
                    Text(
                        text = item.data?.itemList?.getOrNull(it)?.data?.subTitle.orEmpty(),
                        color = Gray,
                        fontSize = 12.sp,
                        fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                            ?.let { FontFamily(it) },
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun CommunityFollowCard(
    item: CommunityReply.Item = CommunityReply.Item(
        data = CommunityReply.Item.Data(
            content = CommunityReply.Item.Data.Content(
                data = CommunityReply.Item.Data.Content.Data(
                    library = "DAILY",
                    description = "时隔六年，他的声音，再次唤醒了我们对平凡生活的向往。",
                    urls = listOf("", ""),
                    owner = CommunityReply.Item.Data.Content.Data.Owner(
                        nickname = "乌苏少年",
                    ),
                    consumption = CommunityReply.Item.Data.Content.Data.Consumption(
                        collectionCount = 63,
                    ),
                ),
                type = "ugcPicture",
            ),
        ),
    ),
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val cardWidth = (screenWidth - 14.dp * 2 - 6.dp) / 2
    val imageWidth = item.data?.content?.data?.width ?: 0
    val imageHeight = item.data?.content?.data?.height ?: 0
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        val (cover, description, choiceness, layers, play, avatar, nickname, collectionCount) = createRefs()
        // 封面图片
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.data?.content?.data?.cover?.feed)
                .transformations(RoundedCornersTransformation(radius = LocalDensity.current.run { 4.dp.toPx() }))
                .placeholder(GradientDrawable().apply {
                    setColor(Gray20.toArgb())
                    cornerRadius = LocalDensity.current.run { 4.dp.toPx() }
                })
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(if (imageWidth == 0 || imageHeight == 0) cardWidth else cardWidth * imageHeight / imageWidth)
                .constrainAs(cover) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            contentScale = ContentScale.FillBounds,
        )
        // 描述
        Text(
            text = item.data?.content?.data?.description.orEmpty(),
            color = LocalThemeColors.current.textColor,
            fontSize = 12.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                ?.let { FontFamily(it) },
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 16.sp,
            modifier = Modifier.constrainAs(description) {
                top.linkTo(cover.bottom, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
        )
        // 精选
        AnimatedVisibility(
            visible = item.data?.content?.data?.library == "DAILY",
            enter = fadeIn(animationSpec = snap()),
            exit = fadeOut(animationSpec = snap()),
            modifier = Modifier
                .constrainAs(choiceness) {
                    top.linkTo(cover.top, margin = 8.dp)
                    start.linkTo(cover.start, margin = 8.dp)
                },
        ) {
            Box(
                modifier = Modifier
                    .width(30.dp)
                    .height(16.dp)
                    .border(width = 0.2.dp, color = White50, shape = RoundedCornerShape(2.dp))
                    .background(color = Black55, shape = RoundedCornerShape(2.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.choiceness),
                    color = White,
                    fontSize = 10.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                        ?.let { FontFamily(it) },
                )
            }
        }
        // 图层
        AnimatedVisibility(
            visible = item.data?.content?.type == "ugcPicture" && item.data.content.data?.run {
                !urls.isNullOrEmpty() && urls.size > 1
            } ?: false,
            enter = fadeIn(animationSpec = snap()),
            exit = fadeOut(animationSpec = snap()),
            modifier = Modifier.constrainAs(layers) {
                top.linkTo(cover.top, margin = 8.dp)
                end.linkTo(cover.end, margin = 8.dp)
            },
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_layers_white_30dp),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
            )
        }
        // 播放按钮
        AnimatedVisibility(
            visible = item.data?.content?.type == "video",
            enter = fadeIn(animationSpec = snap()),
            exit = fadeOut(animationSpec = snap()),
            modifier = Modifier.constrainAs(play) {
                top.linkTo(cover.top, margin = 8.dp)
                end.linkTo(cover.end, margin = 8.dp)
            },
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_play_white_24dp),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .border(width = 1.5.dp, color = White, shape = CircleShape)
                    .background(color = Black80, shape = CircleShape),
            )
        }
        // 头像
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.data?.content?.data?.owner?.avatar)
                .transformations(CircleCropTransformation())
                .error(R.drawable.ic_avatar_gray_76dp)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(size = 20.dp)
                .border(width = 1.dp, color = Gray, shape = CircleShape)
                .constrainAs(avatar) {
                    top.linkTo(description.bottom, margin = 8.dp)
                    start.linkTo(description.start)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                },
            contentScale = ContentScale.FillBounds,
        )
        // 昵称
        Text(
            text = item.data?.content?.data?.owner?.nickname.orEmpty(),
            color = LocalThemeColors.current.textColorTertiary,
            fontSize = 12.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                ?.let { FontFamily(it) },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
            maxLines = 1,
            modifier = Modifier
                .constrainAs(nickname) {
                    start.linkTo(avatar.end, margin = 5.dp)
                    top.linkTo(avatar.top)
                    bottom.linkTo(avatar.bottom)
                    end.linkTo(collectionCount.start, margin = 4.dp)
                    width = Dimension.fillToConstraints
                },
        )
        // 收藏数
        Row(
            modifier = Modifier.constrainAs(collectionCount) {
                top.linkTo(avatar.top)
                bottom.linkTo(avatar.bottom)
                end.linkTo(parent.end)
            },
            horizontalArrangement = Arrangement.spacedBy(space = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = item.data?.content?.data?.consumption?.collectionCount?.toString() ?: "0",
                color = LocalThemeColors.current.textColor,
                fontSize = 13.sp,
                fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                    ?.let { FontFamily(it) },
            )
            Image(
                painter = painterResource(id = R.drawable.ic_favorite_border_white_20dp),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                colorFilter = ColorFilter.tint(color = LocalThemeColors.current.textColorTertiary),
            )
        }
    }
}

