package com.fphoenixcorneae.eyepetizer.mvi.ui.page.home

import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.snap
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.navOptions
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.const.Constant
import com.fphoenixcorneae.eyepetizer.ext.DisposableLifecycleEffect
import com.fphoenixcorneae.eyepetizer.ext.clickableNoRipple
import com.fphoenixcorneae.eyepetizer.ext.toVideoDuration
import com.fphoenixcorneae.eyepetizer.mvi.model.HomepageReply
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavHostController
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavRoute
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black20
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black70
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Blue
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Gray
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Gray20
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.LocalThemeColors
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White35
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White80
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.AutoPlayVideoPlayer
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.BannerViewPager
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.SwipeRefresh
import com.fphoenixcorneae.eyepetizer.mvi.viewmodel.HomepageViewModel
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack

/**
 * @desc：首页-推荐
 * @date：2023/08/10 17:17
 */
@Composable
fun CommendScreen() {
    val viewModel = viewModel<HomepageViewModel>()
    val homepageCommends = viewModel.homepageCommends.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()
    val firstVisibleItemIndex by remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }
    SwipeRefresh(lazyPagingItems = homepageCommends) {
        items(homepageCommends.itemCount) {
            val item = homepageCommends[it] ?: return@items
            HomepageItem(
                item = item,
                position = it,
                playTag = Constant.PlayTag.COMMEND,
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

@Composable
fun HomepageItem(
    item: HomepageReply.Item,
    position: Int,
    playTag: String,
    isFocused: Boolean = false,
) {
    when (item.type) {
        "textCard" -> when (item.data?.type) {
            "header5" -> TextCardHeader5(item = item)
            "header7", "header8" -> TextCardHeader7(item = item)
            "footer2" -> TextCardFooter2(item = item)
            "footer3" -> TextCardFooter3(item = item)
            else -> {}
        }

        "horizontalScrollCard" -> if (item.data?.dataType == "HorizontalScrollCard") HorizontalScrollCard(item = item)

        "specialSquareCardCollection" -> if (item.data?.dataType == "ItemCollection") SpecialSquareCardCollection(item = item)

        "columnCardList" -> if (item.data?.dataType == "ItemCollection") ColumnCardList(item = item)

        "banner" -> if (item.data?.dataType == "Banner") Banner(item = item)

        "banner3" -> if (item.data?.dataType == "Banner") Banner3(item = item)

        "videoSmallCard" -> if (item.data?.dataType == "VideoBeanForClient") VideoSmallCard(
            item = item,
            playTag = playTag,
        )

        "briefCard" -> when (item.data?.dataType) {
            "TagBriefCard" -> TagBriefCard(item = item)
            "TopicBriefCard" -> TopicBriefCard(item = item)
        }

        "followCard" -> if (item.data?.dataType == "FollowCard") FollowCard(item = item)

        "informationCard" -> if (item.data?.dataType == "InformationCard") InformationCard(item = item)

        "ugcSelectedCardCollection" -> if (item.data?.dataType == "ItemCollection") UgcSelectedCardCollection(item = item)

        "autoPlayVideoAd" -> if (item.data?.dataType == "AutoPlayVideoAdDetail") AutoPlayVideoAd(
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
private fun TextCardHeader5(
    item: HomepageReply.Item = HomepageReply.Item(
        data = HomepageReply.Item.Data(
            text = "五分钟新知",
            actionUrl = "eyepetizer://tag/44/?title=5%20%E5%88%86%E9%92%9F%E6%96%B0%E7%9F%A5",
            follow = HomepageReply.Item.Data.Author.Follow(),
        )
    ),
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(53.dp)
    ) {
        val (title, arrow, follow) = createRefs()
        // 标题
        Text(
            text = item.data?.text.orEmpty(),
            color = LocalThemeColors.current.textColor,
            fontSize = 22.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = 19.dp)
                start.linkTo(parent.start, margin = 14.dp)
            },
        )
        // 箭头
        if (item.data?.actionUrl != null) {
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
        // 关注
        AnimatedVisibility(
            visible = item.data?.follow != null,
            enter = fadeIn(animationSpec = snap()),
            exit = fadeOut(animationSpec = snap()),
            modifier = Modifier.constrainAs(follow) {
                top.linkTo(title.top)
                end.linkTo(parent.end, margin = 14.dp)
            },
        ) {
            Box(
                modifier = Modifier
                    .width(44.dp)
                    .height(22.dp)
                    .border(
                        width = 0.2.dp,
                        color = LocalThemeColors.current.textColorSecondary,
                        shape = RoundedCornerShape(2.dp),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.plus_follow),
                    color = LocalThemeColors.current.textColorSecondary,
                    fontSize = 10.sp,
                    fontFamily = ResourcesCompat.getFont(
                        context,
                        R.font.fz_lan_ting_hei_s_db1_gb_regular
                    )?.let { FontFamily(it) },
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TextCardHeader7(
    item: HomepageReply.Item = HomepageReply.Item(
        data = HomepageReply.Item.Data(
            text = "开眼专栏",
            rightText = "查看全部",
        )
    ),
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(53.dp)
    ) {
        val (title, arrow, rightText) = createRefs()
        // 标题
        Text(
            text = item.data?.text.orEmpty(),
            color = LocalThemeColors.current.textColor,
            fontSize = 22.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = 19.dp)
                start.linkTo(parent.start, margin = 14.dp)
            },
        )
        // 箭头
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_right_gray_24dp),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .constrainAs(arrow) {
                    bottom.linkTo(title.bottom)
                    end.linkTo(parent.end, margin = 14.dp)
                },
            colorFilter = ColorFilter.tint(color = Blue)
        )
        // 右边文案
        Text(
            text = item.data?.rightText.orEmpty(),
            color = Blue,
            fontSize = 14.sp,
            fontFamily = ResourcesCompat.getFont(
                context,
                R.font.fz_lan_ting_hei_s_db1_gb_regular
            )?.let { FontFamily(it) },
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(rightText) {
                    top.linkTo(arrow.top)
                    bottom.linkTo(arrow.bottom)
                    end.linkTo(arrow.start, margin = 5.dp)
                }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TextCardFooter2(
    item: HomepageReply.Item = HomepageReply.Item(
        data = HomepageReply.Item.Data(
            text = "查看全部",
        )
    ),
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(53.dp)
    ) {
        val (divider, arrow, rightText) = createRefs()
        // 分割线
        Divider(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .constrainAs(divider) {
                    top.linkTo(parent.top)
                },
            color = Gray,
            thickness = 0.2.dp,
        )
        // 箭头
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_right_gray_24dp),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .constrainAs(arrow) {
                    top.linkTo(rightText.top)
                    bottom.linkTo(rightText.bottom)
                    end.linkTo(parent.end, margin = 14.dp)
                },
            colorFilter = ColorFilter.tint(color = Blue)
        )
        // 右边文案
        Text(
            text = item.data?.text.orEmpty(),
            color = Blue,
            fontSize = 14.sp,
            fontFamily = ResourcesCompat.getFont(
                context,
                R.font.fz_lan_ting_hei_s_db1_gb_regular
            )?.let { FontFamily(it) },
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(rightText) {
                    top.linkTo(parent.top, margin = 28.dp)
                    end.linkTo(arrow.start, margin = 5.dp)
                }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TextCardFooter3(
    item: HomepageReply.Item = HomepageReply.Item(
        data = HomepageReply.Item.Data(
            text = "查看全部",
        )
    ),
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(53.dp)
    ) {
        val (divider, refreshCommend, arrow, rightText) = createRefs()
        // 分割线
        Divider(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .constrainAs(divider) {
                    top.linkTo(parent.top)
                },
            color = Gray,
            thickness = 0.2.dp,
        )
        // 刷新推荐
        Box(
            modifier = Modifier
                .width(78.dp)
                .height(34.dp)
                .constrainAs(refreshCommend) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(rightText.bottom)
                },
            contentAlignment = Alignment.Center,
        ) {
            AsyncImage(
                model = R.mipmap.ic_bg_refresh_commend,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
            )
            Text(
                text = stringResource(R.string.refresh_commend),
                color = White,
                fontSize = 14.sp,
                fontFamily = ResourcesCompat.getFont(
                    context,
                    R.font.fz_lan_ting_hei_s_db1_gb_regular
                )?.let { FontFamily(it) },
                modifier = Modifier.padding(top = 7.dp)
            )
        }
        // 箭头
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_right_gray_24dp),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .constrainAs(arrow) {
                    top.linkTo(rightText.top)
                    bottom.linkTo(rightText.bottom)
                    end.linkTo(parent.end, margin = 14.dp)
                },
            colorFilter = ColorFilter.tint(color = Blue)
        )
        // 右边文案
        Text(
            text = item.data?.text.orEmpty(),
            color = Blue,
            fontSize = 14.sp,
            fontFamily = ResourcesCompat.getFont(
                context,
                R.font.fz_lan_ting_hei_s_db1_gb_regular
            )?.let { FontFamily(it) },
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(rightText) {
                    top.linkTo(parent.top, margin = 28.dp)
                    end.linkTo(arrow.start, margin = 5.dp)
                }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun HorizontalScrollCard(
    item: HomepageReply.Item = HomepageReply.Item(
        data = HomepageReply.Item.Data(
            itemList = buildList {
                repeat(4) {
                    HomepageReply.Item.Data.Item()
                }
            },
            label = HomepageReply.Item.Data.Item.Data.Label(
                text = "标签",
            ),
        )
    ),
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(208.dp)
            .background(color = Color.White),
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
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp, bottom = 12.dp),
            canLoop = false,
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
        // 分割线
        Divider(
            color = Gray,
            thickness = 0.2.dp,
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .align(Alignment.BottomCenter),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SpecialSquareCardCollection(
    item: HomepageReply.Item = HomepageReply.Item(
        data = HomepageReply.Item.Data(
            header = HomepageReply.Item.Data.Header(
                title = "热门分类",
                rightText = "查看全部分类",
            ),
            itemList = buildList {
                repeat(16) {
                    HomepageReply.Item.Data.Item()
                }
            },
        )
    ),
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(285.dp),
    ) {
        val (title, arrow, rightText, categories, divider) = createRefs()
        // 标题
        Text(
            text = item.data?.header?.title.orEmpty(),
            color = LocalThemeColors.current.textColor,
            fontSize = 22.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = 19.dp)
                start.linkTo(parent.start, margin = 14.dp)
            },
        )
        // 箭头
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_right_gray_24dp),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .constrainAs(arrow) {
                    bottom.linkTo(title.bottom)
                    end.linkTo(parent.end, margin = 14.dp)
                },
            colorFilter = ColorFilter.tint(color = Blue)
        )
        // 右边文案
        Text(
            text = item.data?.header?.rightText.orEmpty(),
            color = Blue,
            fontSize = 14.sp,
            fontFamily = ResourcesCompat.getFont(
                context,
                R.font.fz_lan_ting_hei_s_db1_gb_regular
            )?.let { FontFamily(it) },
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(rightText) {
                    top.linkTo(arrow.top)
                    bottom.linkTo(arrow.bottom)
                    end.linkTo(arrow.start, margin = 5.dp)
                }
        )
        // 分类
        LazyHorizontalGrid(
            rows = GridCells.Adaptive(minSize = 108.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 53.dp, bottom = 12.dp)
                .constrainAs(categories) {
                },
            contentPadding = PaddingValues(horizontal = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            val categoryList = item.data?.itemList
                ?.filter { it.type == "squareCardOfCategory" && it.data?.dataType == "SquareCard" }
                .orEmpty()
            items(items = categoryList) { category ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(data = category.data?.image)
                            .transformations(RoundedCornersTransformation(LocalDensity.current.run { 4.dp.toPx() }))
                            .placeholder(GradientDrawable().apply {
                                setColor(Gray20.toArgb())
                                cornerRadius = LocalDensity.current.run { 4.dp.toPx() }
                            })
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                    )
                    Text(
                        text = category.data?.title.orEmpty(),
                        color = White,
                        fontSize = 14.sp,
                        fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                            ?.let { FontFamily(it) },
                    )
                }
            }
        }
        // 分割线
        Divider(
            color = Gray,
            thickness = 0.2.dp,
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .constrainAs(divider) {
                    bottom.linkTo(parent.bottom)
                },
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ColumnCardList(
    item: HomepageReply.Item = HomepageReply.Item(
        data = HomepageReply.Item.Data(
            header = HomepageReply.Item.Data.Header(
                title = "专题策划",
                rightText = "查看全部",
            ),
            itemList = buildList {
                repeat(4) {
                    HomepageReply.Item.Data.Item()
                }
            },
        )
    ),
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
    ) {
        val (title, arrow, rightText, cards, divider) = createRefs()
        // 标题
        Text(
            text = item.data?.header?.title.orEmpty(),
            color = LocalThemeColors.current.textColor,
            fontSize = 22.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = 19.dp)
                start.linkTo(parent.start, margin = 14.dp)
            },
        )
        // 箭头
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_right_gray_24dp),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .constrainAs(arrow) {
                    bottom.linkTo(title.bottom)
                    end.linkTo(parent.end, margin = 14.dp)
                },
            colorFilter = ColorFilter.tint(color = Blue)
        )
        // 右边文案
        Text(
            text = item.data?.header?.rightText.orEmpty(),
            color = Blue,
            fontSize = 14.sp,
            fontFamily = ResourcesCompat.getFont(
                context,
                R.font.fz_lan_ting_hei_s_db1_gb_regular
            )?.let { FontFamily(it) },
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(rightText) {
                    top.linkTo(arrow.top)
                    bottom.linkTo(arrow.bottom)
                    end.linkTo(arrow.start, margin = 5.dp)
                }
        )
        // 卡片
        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 2),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 53.dp, bottom = 12.dp)
                .constrainAs(cards) {
                },
            contentPadding = PaddingValues(horizontal = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            val categoryList = item.data?.itemList
                ?.filter { it.type == "squareCardOfColumn" && it.data?.dataType == "SquareCard" }
                .orEmpty()
            items(items = categoryList) { category ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(data = category.data?.image)
                            .transformations(RoundedCornersTransformation(LocalDensity.current.run { 4.dp.toPx() }))
                            .placeholder(GradientDrawable().apply {
                                setColor(Gray20.toArgb())
                                cornerRadius = LocalDensity.current.run { 4.dp.toPx() }
                            })
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds,
                    )
                    Text(
                        text = category.data?.title.orEmpty(),
                        color = White,
                        fontSize = 14.sp,
                        fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                            ?.let { FontFamily(it) },
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    )
                }
            }
        }
        // 分割线
        Divider(
            color = Gray,
            thickness = 0.2.dp,
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .constrainAs(divider) {
                    bottom.linkTo(parent.bottom)
                },
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun Banner(
    item: HomepageReply.Item = HomepageReply.Item(),
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        // 横幅图片
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.data?.image)
                .transformations(RoundedCornersTransformation(LocalDensity.current.run { 4.dp.toPx() }))
                .placeholder(GradientDrawable().apply {
                    setColor(Gray20.toArgb())
                    cornerRadius = LocalDensity.current.run { 4.dp.toPx() }
                })
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(185.dp)
                .padding(horizontal = 14.dp, vertical = 10.dp),
            contentScale = ContentScale.FillBounds,
        )
        // 分割线
        Divider(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .align(Alignment.BottomCenter),
            color = Gray,
            thickness = 0.2.dp,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun Banner3(
    item: HomepageReply.Item = HomepageReply.Item(
        data = HomepageReply.Item.Data(
            label = HomepageReply.Item.Data.Item.Data.Label(
                text = "广告",
            ),
            header = HomepageReply.Item.Data.Header(
                title = "J12. IT’S ALL ABOUT SECONDS*",
                description = "*J12腕表 分秒背后",
            )
        )
    ),
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (image, label, avatar, title, description, divider) = createRefs()
        // 图片
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.data?.image)
                .transformations(RoundedCornersTransformation(LocalDensity.current.run { 4.dp.toPx() }))
                .placeholder(GradientDrawable().apply {
                    setColor(Gray20.toArgb())
                    cornerRadius = LocalDensity.current.run { 4.dp.toPx() }
                })
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .height(185.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top, margin = 10.dp)
                    start.linkTo(parent.start, margin = 14.dp)
                    end.linkTo(parent.end, margin = 14.dp)
                    width = Dimension.fillToConstraints
                },
            contentScale = ContentScale.FillBounds,
        )
        // 标签
        AnimatedVisibility(
            visible = !item.data?.label?.text.isNullOrEmpty(),
            enter = fadeIn(animationSpec = snap()),
            exit = fadeOut(animationSpec = snap()),
            modifier = Modifier.constrainAs(label) {
                top.linkTo(image.top, margin = 8.dp)
                end.linkTo(image.end, margin = 8.dp)
            },
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
        // 头像
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.data?.header?.icon)
                .transformations(CircleCropTransformation())
                .error(R.drawable.ic_avatar_gray_76dp)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(size = 40.dp)
                .border(width = 1.dp, color = Gray, shape = CircleShape)
                .constrainAs(avatar) {
                    top.linkTo(image.bottom, margin = 10.dp)
                    start.linkTo(image.start)
                },
            contentScale = ContentScale.FillBounds,
        )
        // 标题
        Text(
            text = item.data?.header?.title.orEmpty(),
            color = LocalThemeColors.current.textColor,
            fontSize = 14.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
            maxLines = 1,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(avatar.top, margin = 2.dp)
                    start.linkTo(avatar.end, margin = 12.dp)
                    end.linkTo(description.end)
                    width = Dimension.fillToConstraints
                },
        )
        // 描述
        Text(
            text = item.data?.header?.description.orEmpty(),
            color = LocalThemeColors.current.textColorTertiary,
            fontSize = 12.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                ?.let { FontFamily(it) },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
            maxLines = 1,
            modifier = Modifier
                .constrainAs(description) {
                    top.linkTo(title.bottom, margin = 2.dp)
                    start.linkTo(title.start)
                    end.linkTo(image.end, margin = 35.dp)
                    width = Dimension.fillToConstraints
                },
        )
        // 分割线
        Divider(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .constrainAs(divider) {
                    top.linkTo(avatar.bottom, margin = 10.dp)
                    bottom.linkTo(parent.bottom)
                },
            color = Gray,
            thickness = 0.2.dp,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun VideoSmallCard(
    item: HomepageReply.Item = HomepageReply.Item(
        data = HomepageReply.Item.Data(
            duration = 130,
            title = "当病毒把人类隔离在家，大自然开始了狂欢",
            category = "动画",
            library = "DAILY",
        )
    ),
    playTag: String = "",
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickableNoRipple {
                NavHostController
                    .get()
                    .navigate(
                        route = "${NavRoute.VIDEO_DETAIL}/${item.data?.id}",
                        navOptions = navOptions {
                            launchSingleTop = true
                        },
                    )
            },
    ) {
        val (cover, duration, title, description, share) = createRefs()
        // 视频封面
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.data?.cover?.feed)
                .transformations(RoundedCornersTransformation(LocalDensity.current.run { 4.dp.toPx() }))
                .placeholder(GradientDrawable().apply {
                    setColor(Gray20.toArgb())
                    cornerRadius = LocalDensity.current.run { 4.dp.toPx() }
                })
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .width(173.dp)
                .height(100.dp)
                .constrainAs(cover) {
                    start.linkTo(parent.start, margin = 14.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom, margin = 15.dp)
                },
            contentScale = ContentScale.FillBounds,
        )
        // 视频时长
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(color = Black20, shape = RoundedCornerShape(2.dp))
                .constrainAs(duration) {
                    end.linkTo(cover.end, margin = 5.dp)
                    bottom.linkTo(cover.bottom, margin = 5.dp)
                },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = item.data?.duration?.toVideoDuration().orEmpty(),
                color = Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ResourcesCompat.getFont(context, R.font.din_condensed_bold)
                    ?.let { FontFamily(it) },
                modifier = Modifier.padding(horizontal = 3.5.dp, vertical = 1.dp),
            )
        }
        // 标题
        Text(
            text = item.data?.title.orEmpty(),
            color = if (playTag == Constant.PlayTag.VIDEO_DETAIL) White else LocalThemeColors.current.textColor,
            fontSize = 14.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(cover.top, margin = 8.dp)
                    start.linkTo(cover.end, margin = 12.dp)
                    end.linkTo(parent.end, margin = 14.dp)
                    width = Dimension.fillToConstraints
                },
        )
        // 描述
        Text(
            text = if (item.data?.library == "DAILY") "#${item.data.category.orEmpty()} / 开眼精选" else "#${item.data?.category.orEmpty()}",
            color = if (playTag == Constant.PlayTag.VIDEO_DETAIL) White35 else LocalThemeColors.current.textColorTertiary,
            fontSize = 12.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                ?.let { FontFamily(it) },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
            maxLines = 1,
            modifier = Modifier
                .constrainAs(description) {
                    bottom.linkTo(cover.bottom, margin = 8.dp)
                    start.linkTo(title.start)
                    end.linkTo(share.start, margin = 16.dp, goneMargin = 14.dp)
                    width = Dimension.fillToConstraints
                },
        )
        // 分享
        Image(
            painter = painterResource(id = R.drawable.ic_share_gray_20dp),
            contentDescription = null,
            modifier = Modifier
                .size(size = 24.dp)
                .constrainAs(share) {
                    bottom.linkTo(cover.bottom, margin = 6.dp)
                    end.linkTo(parent.end, margin = 14.dp)
                },
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TagBriefCard(
    item: HomepageReply.Item = HomepageReply.Item(
        data = HomepageReply.Item.Data(
            title = "我的宝藏书架",
            description = "你曾是少年的青春时代",
            follow = HomepageReply.Item.Data.Author.Follow(),
        )
    ),
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
    ) {
        val (icon, title, description, follow) = createRefs()
        // 图标
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.data?.icon)
                .transformations(RoundedCornersTransformation(LocalDensity.current.run { 4.dp.toPx() }))
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(50.dp)
                .constrainAs(icon) {
                    start.linkTo(parent.start, margin = 14.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
        )
        // 标题
        Text(
            text = item.data?.title.orEmpty(),
            color = LocalThemeColors.current.textColor,
            fontSize = 14.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
            maxLines = 1,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(icon.top, margin = 8.dp)
                    start.linkTo(icon.end, margin = 12.dp)
                    end.linkTo(description.end)
                    width = Dimension.fillToConstraints
                },
        )
        // 描述
        Text(
            text = item.data?.description.orEmpty(),
            color = LocalThemeColors.current.textColorTertiary,
            fontSize = 12.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                ?.let { FontFamily(it) },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
            maxLines = 1,
            modifier = Modifier
                .constrainAs(description) {
                    top.linkTo(title.bottom, margin = 2.dp)
                    start.linkTo(title.start)
                    end.linkTo(follow.start, margin = 16.dp, goneMargin = 14.dp)
                    width = Dimension.fillToConstraints
                },
        )
        // 关注
        AnimatedVisibility(
            visible = item.data?.follow != null,
            enter = fadeIn(animationSpec = snap()),
            exit = fadeOut(animationSpec = snap()),
            modifier = Modifier.constrainAs(follow) {
                top.linkTo(parent.top)
                end.linkTo(parent.end, margin = 14.dp)
                bottom.linkTo(parent.bottom)
            },
        ) {
            Box(
                modifier = Modifier
                    .width(44.dp)
                    .height(22.dp)
                    .border(
                        width = 0.2.dp,
                        color = LocalThemeColors.current.textColorSecondary,
                        shape = RoundedCornerShape(2.dp),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.plus_follow),
                    color = LocalThemeColors.current.textColorSecondary,
                    fontSize = 10.sp,
                    fontFamily = ResourcesCompat.getFont(
                        context,
                        R.font.fz_lan_ting_hei_s_db1_gb_regular
                    )?.let { FontFamily(it) },
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TopicBriefCard(
    item: HomepageReply.Item = HomepageReply.Item(
        data = HomepageReply.Item.Data(
            title = "如果发挥你的专业去摆摊，你会去摆什么样的地摊？",
            description = "9527人浏览 / 498人参与",
            follow = HomepageReply.Item.Data.Author.Follow(),
        )
    ),
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
    ) {
        val (icon, title, description) = createRefs()
        // 图标
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.data?.icon)
                .transformations(RoundedCornersTransformation(LocalDensity.current.run { 4.dp.toPx() }))
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(50.dp)
                .constrainAs(icon) {
                    start.linkTo(parent.start, margin = 14.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
        )
        // 标题
        Text(
            text = item.data?.title.orEmpty(),
            color = LocalThemeColors.current.textColor,
            fontSize = 14.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(icon.top)
                    start.linkTo(icon.end, margin = 12.dp)
                    end.linkTo(description.end)
                    width = Dimension.fillToConstraints
                },
        )
        // 描述
        Text(
            text = item.data?.description.orEmpty(),
            color = LocalThemeColors.current.textColorTertiary,
            fontSize = 12.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                ?.let { FontFamily(it) },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
            maxLines = 1,
            modifier = Modifier
                .constrainAs(description) {
                    top.linkTo(title.bottom, margin = 2.dp)
                    start.linkTo(title.start)
                    end.linkTo(parent.end, margin = 14.dp)
                    width = Dimension.fillToConstraints
                },
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun FollowCard(
    item: HomepageReply.Item = HomepageReply.Item(
        data = HomepageReply.Item.Data(
            content = HomepageReply.Item.Data.Content(
                data = HomepageReply.Item.Data.Content.Data(
                    library = "DAILY",
                    ad = true,
                    duration = 130,
                )
            ),
            header = HomepageReply.Item.Data.Header(
                title = "广告裁判",
                description = "开眼广告精选 / #广告",
            )
        )
    ),
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickableNoRipple {
                NavHostController
                    .get()
                    .navigate(
                        route = "${NavRoute.VIDEO_DETAIL}/${item.data?.content?.data?.id}",
                        navOptions = navOptions {
                            launchSingleTop = true
                        },
                    )
            },
    ) {
        val (cover, choiceness, advert, duration, avatar, avatarStar, title, description, share, divider) = createRefs()
        // 视频封面
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.data?.content?.data?.cover?.feed)
                .transformations(RoundedCornersTransformation(LocalDensity.current.run { 4.dp.toPx() }))
                .placeholder(GradientDrawable().apply {
                    setColor(Gray20.toArgb())
                    cornerRadius = LocalDensity.current.run { 4.dp.toPx() }
                })
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .height(183.dp)
                .constrainAs(cover) {
                    top.linkTo(parent.top, margin = 5.dp)
                    start.linkTo(parent.start, margin = 14.dp)
                    end.linkTo(parent.end, margin = 14.dp)
                    width = Dimension.fillToConstraints
                },
            contentScale = ContentScale.FillBounds,
        )
        // 精选
        AnimatedVisibility(
            visible = item.data?.content?.data?.library == "DAILY",
            enter = fadeIn(animationSpec = snap()),
            exit = fadeOut(animationSpec = snap()),
            modifier = Modifier.constrainAs(choiceness) {
                top.linkTo(cover.top, margin = 12.dp)
                start.linkTo(cover.start, margin = 12.dp)
            },
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_choiceness),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp),
            )
        }
        // 广告
        AnimatedVisibility(
            visible = item.data?.content?.data?.ad == true,
            enter = fadeIn(animationSpec = snap()),
            exit = fadeOut(animationSpec = snap()),
            modifier = Modifier.constrainAs(advert) {
                top.linkTo(cover.top, margin = 8.dp)
                end.linkTo(cover.end, margin = 8.dp)
            },
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
                    text = stringResource(R.string.advert),
                    color = White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                        ?.let { FontFamily(it) },
                )
            }
        }
        // 视频时长
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(color = Black20, shape = RoundedCornerShape(2.dp))
                .constrainAs(duration) {
                    end.linkTo(cover.end, margin = 5.dp)
                    bottom.linkTo(cover.bottom, margin = 5.dp)
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
        // 头像
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.data?.header?.icon)
                .transformations(CircleCropTransformation())
                .error(R.drawable.ic_avatar_gray_76dp)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(size = 40.dp)
                .border(width = 1.dp, color = Gray, shape = CircleShape)
                .constrainAs(avatar) {
                    top.linkTo(cover.bottom, margin = 10.dp)
                    start.linkTo(cover.start)
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
        // 标题
        Text(
            text = item.data?.header?.title.orEmpty(),
            color = LocalThemeColors.current.textColor,
            fontSize = 14.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
            maxLines = 1,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(avatar.top, margin = 2.dp)
                    start.linkTo(avatar.end, margin = 12.dp)
                    end.linkTo(description.end)
                    width = Dimension.fillToConstraints
                },
        )
        // 描述
        Text(
            text = item.data?.header?.description.orEmpty(),
            color = LocalThemeColors.current.textColorTertiary,
            fontSize = 12.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                ?.let { FontFamily(it) },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
            maxLines = 1,
            modifier = Modifier
                .constrainAs(description) {
                    top.linkTo(title.bottom, margin = 2.dp)
                    start.linkTo(title.start)
                    end.linkTo(share.start, margin = 16.dp, goneMargin = 14.dp)
                    width = Dimension.fillToConstraints
                },
        )
        // 分享
        Image(
            painter = painterResource(id = R.drawable.ic_share_gray_20dp),
            contentDescription = null,
            modifier = Modifier
                .size(size = 24.dp)
                .constrainAs(share) {
                    top.linkTo(avatar.top)
                    bottom.linkTo(avatar.bottom)
                    end.linkTo(cover.end)
                },
        )
        // 分割线
        Divider(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .constrainAs(divider) {
                    top.linkTo(avatar.bottom, margin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 5.dp)
                },
            color = Gray,
            thickness = 0.2.dp,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun InformationCard(
    item: HomepageReply.Item = HomepageReply.Item(
        data = HomepageReply.Item.Data(
            titleList = buildList {
                repeat(4) {
                    add("Netfix | 因社会戾气沉重，[黑镜]第六季或延迟推出")
                }
            }
        )
    ),
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (cover, titleList) = createRefs()
        // 视频封面
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.data?.content?.data?.cover?.feed)
                .transformations(
                    RoundedCornersTransformation(
                        topLeft = LocalDensity.current.run { 4.dp.toPx() },
                        topRight = LocalDensity.current.run { 4.dp.toPx() },
                    )
                )
                .placeholder(GradientDrawable().apply {
                    setColor(Gray20.toArgb())
                    cornerRadii = floatArrayOf(
                        LocalDensity.current.run { 4.dp.toPx() },
                        LocalDensity.current.run { 4.dp.toPx() },
                        LocalDensity.current.run { 4.dp.toPx() },
                        LocalDensity.current.run { 4.dp.toPx() },
                        0f, 0f, 0f, 0f
                    )
                })
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .height(183.dp)
                .constrainAs(cover) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = 14.dp)
                    end.linkTo(parent.end, margin = 14.dp)
                    width = Dimension.fillToConstraints
                },
            contentScale = ContentScale.FillBounds,
        )
        // 标题列表
        Column(
            modifier = Modifier
                .background(
                    color = Gray,
                    shape = RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp)
                )
                .constrainAs(titleList) {
                    top.linkTo(cover.bottom)
                    start.linkTo(cover.start)
                    end.linkTo(cover.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
            verticalArrangement = Arrangement.spacedBy(13.dp),
        ) {
            item.data?.titleList?.run {
                Spacer(modifier = Modifier.height(5.dp))
                forEach {
                    Text(
                        text = it,
                        color = LocalThemeColors.current.textColor,
                        fontSize = 12.sp,
                        fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                            ?.let { FontFamily(it) },
                        modifier = Modifier.padding(horizontal = 12.dp),
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun UgcSelectedCardCollection(
    item: HomepageReply.Item = HomepageReply.Item(
        data = HomepageReply.Item.Data(
            header = HomepageReply.Item.Data.Header(
                title = "社区精选",
                rightText = "查看全部",
            ),
            itemList = listOf(
                HomepageReply.Item.Data.Item(
                    data = HomepageReply.Item.Data.Item.Data(
                        urls = listOf("", ""),
                        nickname = "全球广告精选",
                    ),
                ),
                HomepageReply.Item.Data.Item(
                    data = HomepageReply.Item.Data.Item.Data(
                        urls = listOf("", ""),
                        nickname = "全球广告精选",
                    ),
                ),
                HomepageReply.Item.Data.Item(
                    data = HomepageReply.Item.Data.Item.Data(
                        urls = listOf("", ""),
                        nickname = "全球广告精选",
                    ),
                ),
            ),
        )
    ),
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (title, arrow, rightText) = createRefs()
        val (imageLeft, layersLeft, avatarLeft, nicknameLeft) = createRefs()
        val (imageRightTop, layersRightTop, avatarRightTop, nicknameRightTop) = createRefs()
        val (imageRightBottom, layersRightBottom, avatarRightBottom, nicknameRightBottom) = createRefs()
        // 标题
        Text(
            text = item.data?.header?.title.orEmpty(),
            color = LocalThemeColors.current.textColor,
            fontSize = 22.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = 19.dp)
                start.linkTo(parent.start, margin = 14.dp)
            },
        )
        // 箭头
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_right_gray_24dp),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .constrainAs(arrow) {
                    bottom.linkTo(title.bottom)
                    end.linkTo(parent.end, margin = 14.dp)
                },
            colorFilter = ColorFilter.tint(color = Blue)
        )
        // 右边文案
        Text(
            text = item.data?.header?.rightText.orEmpty(),
            color = Blue,
            fontSize = 14.sp,
            fontFamily = ResourcesCompat.getFont(
                context,
                R.font.fz_lan_ting_hei_s_db1_gb_regular
            )?.let { FontFamily(it) },
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(rightText) {
                    top.linkTo(arrow.top)
                    bottom.linkTo(arrow.bottom)
                    end.linkTo(arrow.start, margin = 5.dp)
                }
        )
        // 左边
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.data?.itemList?.getOrNull(0)?.data?.url)
                .transformations(
                    RoundedCornersTransformation(
                        topLeft = LocalDensity.current.run { 4.dp.toPx() },
                        bottomLeft = LocalDensity.current.run { 4.dp.toPx() },
                    )
                )
                .placeholder(GradientDrawable().apply {
                    setColor(Gray20.toArgb())
                    cornerRadii = floatArrayOf(
                        LocalDensity.current.run { 4.dp.toPx() },
                        LocalDensity.current.run { 4.dp.toPx() },
                        0f, 0f, 0f, 0f,
                        LocalDensity.current.run { 4.dp.toPx() },
                        LocalDensity.current.run { 4.dp.toPx() },
                    )
                })
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .height(183.dp)
                .constrainAs(imageLeft) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start, margin = 14.dp)
                    end.linkTo(imageRightTop.start)
                    width = Dimension.fillToConstraints
                },
            contentScale = ContentScale.FillBounds,
        )
        AnimatedVisibility(
            visible = item.data?.itemList?.getOrNull(0)?.data?.run {
                !urls.isNullOrEmpty() && urls.size > 1
            } ?: false,
            enter = fadeIn(animationSpec = snap()),
            exit = fadeOut(animationSpec = snap()),
            modifier = Modifier.constrainAs(layersLeft) {
                top.linkTo(imageLeft.top, margin = 8.dp)
                end.linkTo(imageLeft.end, margin = 8.dp)
            },
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_layers_white_15dp),
                contentDescription = null,
                modifier = Modifier.size(25.dp),
            )
        }
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.data?.itemList?.getOrNull(0)?.data?.userCover)
                .transformations(CircleCropTransformation())
                .error(R.drawable.ic_avatar_gray_76dp)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(size = 20.dp)
                .border(width = 1.dp, color = White, shape = CircleShape)
                .constrainAs(avatarLeft) {
                    end.linkTo(nicknameLeft.start, margin = 5.dp)
                    bottom.linkTo(imageLeft.bottom, margin = 4.dp)
                },
            contentScale = ContentScale.FillBounds,
        )
        Text(
            text = item.data?.itemList?.getOrNull(0)?.data?.nickname.orEmpty(),
            color = White,
            fontSize = 11.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
            maxLines = 1,
            modifier = Modifier
                .constrainAs(nicknameLeft) {
                    top.linkTo(avatarLeft.top)
                    bottom.linkTo(avatarLeft.bottom)
                    end.linkTo(imageLeft.end, margin = 4.dp)
                },
        )
        // 右上
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.data?.itemList?.getOrNull(1)?.data?.url)
                .transformations(
                    RoundedCornersTransformation(
                        topRight = LocalDensity.current.run { 4.dp.toPx() },
                    )
                )
                .placeholder(GradientDrawable().apply {
                    setColor(Gray20.toArgb())
                    cornerRadii = floatArrayOf(
                        0f, 0f,
                        LocalDensity.current.run { 4.dp.toPx() },
                        LocalDensity.current.run { 4.dp.toPx() },
                        0f, 0f, 0f, 0f,
                    )
                })
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(imageRightTop) {
                    start.linkTo(imageLeft.end, margin = 2.dp)
                    top.linkTo(imageLeft.top)
                    end.linkTo(arrow.end)
                    bottom.linkTo(imageRightBottom.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            contentScale = ContentScale.FillBounds,
        )
        AnimatedVisibility(
            visible = item.data?.itemList?.getOrNull(1)?.data?.run {
                !urls.isNullOrEmpty() && urls.size > 1
            } ?: false,
            enter = fadeIn(animationSpec = snap()),
            exit = fadeOut(animationSpec = snap()),
            modifier = Modifier.constrainAs(layersRightTop) {
                top.linkTo(imageRightTop.top, margin = 8.dp)
                end.linkTo(imageRightTop.end, margin = 8.dp)
            },
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_layers_white_15dp),
                contentDescription = null,
                modifier = Modifier.size(25.dp),
            )
        }
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.data?.itemList?.getOrNull(1)?.data?.userCover)
                .transformations(CircleCropTransformation())
                .error(R.drawable.ic_avatar_gray_76dp)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(size = 20.dp)
                .border(width = 1.dp, color = White, shape = CircleShape)
                .constrainAs(avatarRightTop) {
                    end.linkTo(nicknameRightTop.start, margin = 5.dp)
                    bottom.linkTo(imageRightTop.bottom, margin = 4.dp)
                },
            contentScale = ContentScale.FillBounds,
        )
        Text(
            text = item.data?.itemList?.getOrNull(1)?.data?.nickname.orEmpty(),
            color = White,
            fontSize = 10.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
            maxLines = 1,
            modifier = Modifier
                .constrainAs(nicknameRightTop) {
                    top.linkTo(avatarRightTop.top)
                    bottom.linkTo(avatarRightTop.bottom)
                    end.linkTo(imageRightTop.end, margin = 4.dp)
                },
        )
        // 右下
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.data?.itemList?.getOrNull(2)?.data?.url)
                .transformations(
                    RoundedCornersTransformation(
                        topLeft = LocalDensity.current.run { 4.dp.toPx() },
                        bottomLeft = LocalDensity.current.run { 4.dp.toPx() },
                    )
                )
                .placeholder(GradientDrawable().apply {
                    setColor(Gray20.toArgb())
                    cornerRadii = floatArrayOf(
                        0f, 0f, 0f, 0f,
                        LocalDensity.current.run { 4.dp.toPx() },
                        LocalDensity.current.run { 4.dp.toPx() },
                        0f, 0f,
                    )
                })
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(imageRightBottom) {
                    start.linkTo(imageRightTop.start)
                    top.linkTo(imageRightTop.bottom, margin = 2.dp)
                    end.linkTo(imageRightTop.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            contentScale = ContentScale.FillBounds,
        )
        AnimatedVisibility(
            visible = item.data?.itemList?.getOrNull(2)?.data?.run {
                !urls.isNullOrEmpty() && urls.size > 1
            } ?: false,
            enter = fadeIn(animationSpec = snap()),
            exit = fadeOut(animationSpec = snap()),
            modifier = Modifier.constrainAs(layersRightBottom) {
                top.linkTo(imageRightBottom.top, margin = 8.dp)
                end.linkTo(imageRightBottom.end, margin = 8.dp)
            },
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_layers_white_15dp),
                contentDescription = null,
                modifier = Modifier.size(25.dp),
            )
        }
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.data?.itemList?.getOrNull(2)?.data?.userCover)
                .transformations(CircleCropTransformation())
                .error(R.drawable.ic_avatar_gray_76dp)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(size = 20.dp)
                .border(width = 1.dp, color = White, shape = CircleShape)
                .constrainAs(avatarRightBottom) {
                    end.linkTo(nicknameRightBottom.start, margin = 5.dp)
                    bottom.linkTo(imageRightBottom.bottom, margin = 4.dp)
                },
            contentScale = ContentScale.FillBounds,
        )
        Text(
            text = item.data?.itemList?.getOrNull(2)?.data?.nickname.orEmpty(),
            color = White,
            fontSize = 10.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
            maxLines = 1,
            modifier = Modifier
                .constrainAs(nicknameRightBottom) {
                    top.linkTo(avatarRightBottom.top)
                    bottom.linkTo(avatarRightBottom.bottom)
                    end.linkTo(imageRightBottom.end, margin = 4.dp)
                },
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun AutoPlayVideoAd(
    item: HomepageReply.Item = HomepageReply.Item(
        data = HomepageReply.Item.Data(
            detail = HomepageReply.Item.Data.Detail(
                title = "J12. IT’S ALL ABOUT SECONDS*",
                description = "*J12腕表 分秒背后",
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
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (video, label, avatar, title, description, divider) = createRefs()
        // 视频
        Card(
            modifier = Modifier
                .height(185.dp)
                .constrainAs(video) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start, margin = 14.dp)
                    end.linkTo(parent.end, margin = 14.dp)
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
                        load(data = item.data?.detail?.imageUrl) {
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
                            GSYVideoManager.instance().isNeedMute = true
                        }

                        override fun onClickBlank(url: String?, vararg objects: Any?) {
                            super.onClickBlank(url, *objects)
                        }
                    })
                    // 设置播放URL
                    setUp(item.data?.detail?.url, false, null)
                    if (isFocused) {
                        // 完全可见时自动播放
                        startPlayLogic()
                    }
                }
            }
        }
        // 标签
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(animationSpec = snap()),
            exit = fadeOut(animationSpec = snap()),
            modifier = Modifier.constrainAs(label) {
                top.linkTo(video.top, margin = 8.dp)
                end.linkTo(video.end, margin = 8.dp)
            },
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
                    text = stringResource(id = R.string.advert),
                    color = White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                        ?.let { FontFamily(it) },
                )
            }
        }
        // 头像
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data = item.data?.detail?.icon)
                .transformations(CircleCropTransformation())
                .error(R.drawable.ic_avatar_gray_76dp)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(size = 40.dp)
                .border(width = 1.dp, color = Gray, shape = CircleShape)
                .constrainAs(avatar) {
                    top.linkTo(video.bottom, margin = 10.dp)
                    start.linkTo(video.start)
                },
            contentScale = ContentScale.FillBounds,
        )
        // 标题
        Text(
            text = item.data?.detail?.title.orEmpty(),
            color = LocalThemeColors.current.textColor,
            fontSize = 14.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
            maxLines = 1,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(avatar.top, margin = 2.dp)
                    start.linkTo(avatar.end, margin = 12.dp)
                    end.linkTo(description.end)
                    width = Dimension.fillToConstraints
                },
        )
        // 描述
        Text(
            text = item.data?.detail?.description.orEmpty(),
            color = LocalThemeColors.current.textColorTertiary,
            fontSize = 12.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                ?.let { FontFamily(it) },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
            maxLines = 1,
            modifier = Modifier
                .constrainAs(description) {
                    top.linkTo(title.bottom, margin = 2.dp)
                    start.linkTo(title.start)
                    end.linkTo(video.end, margin = 35.dp)
                    width = Dimension.fillToConstraints
                },
        )
        // 分割线
        Divider(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .constrainAs(divider) {
                    top.linkTo(avatar.bottom, margin = 10.dp)
                    bottom.linkTo(parent.bottom)
                },
            color = Gray,
            thickness = 0.2.dp,
        )
    }
}

