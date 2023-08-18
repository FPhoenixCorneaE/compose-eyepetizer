package com.fphoenixcorneae.eyepetizer.mvi.ui.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fphoenixcorneae.eyepetizer.ext.clickableNoRipple
import com.fphoenixcorneae.eyepetizer.ext.floorMod
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.fixedRateTimer

/**
 * @desc：BannerViewPager
 * @date：2023/08/16 11:53
 * @param data                 数据来源
 * @param imageModel           ImageRequest或ImageRequest.data。
 * @param modifier             HorizontalPager的Modifier
 * @param contentScale         图片裁剪方式
 * @param showIndicator        是否显示指示器
 * @param activeColor          选中的指示器样式
 * @param inactiveColor        未选中的指示器样式
 * @param canLoop              是否自动播放轮播图
 * @param loopDelay            任务执行前的延迟（毫秒）
 * @param loopPeriod           连续任务执行之间的时间（毫秒）。
 * @param contentPadding       整个内容周围的填充。这将在内容被剪切后为其添加填充，这是不可能通过modifier参数实现的。
 *                             您可以使用它在第一页之前或最后一页之后添加填充。使用pageSpacing来增加页面之间的间距。
 * @param pageSpacing          用于分隔此ViewPager中的页的空间量
 * @param onItemClick          Banner的item点击事件
 * @param content              文本内容
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> BannerViewPager(
    data: List<T?>?,
    imageModel: (item: T?) -> Any?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    showIndicator: Boolean = true,
    activeColor: Color = Color.White,
    inactiveColor: Color = Color(0xFF999999),
    canLoop: Boolean = true,
    loopDelay: Long = 3000,
    loopPeriod: Long = 3000,
    contentPadding: PaddingValues = PaddingValues(horizontal = 14.dp),
    pageSpacing: Dp = 4.dp,
    onItemClick: ((Int) -> Unit) = {},
    content: @Composable BoxScope.(item: T?) -> Unit = {},
) {
    val virtualCount = Int.MAX_VALUE
    val actualCount = data?.size ?: 0
    // 初始图片下标
    val initialIndex = virtualCount / 2
    val pageState = rememberPagerState(initialPage = initialIndex)
    if (canLoop) {
        val coroutineScope = rememberCoroutineScope()
        DisposableEffect(Unit) {
            val timer = fixedRateTimer(initialDelay = loopDelay, period = loopPeriod) {
                coroutineScope.launch {
                    pageState.animateScrollToPage(pageState.currentPage + 1)
                }
            }
            onDispose {
                timer.cancel()
            }
        }
    }
    Box(modifier = modifier) {
        HorizontalPager(
            pageCount = virtualCount,
            modifier = Modifier.fillMaxSize(),
            state = pageState,
            contentPadding = contentPadding,
            pageSpacing = if (actualCount > 1) pageSpacing else 0.dp,
        ) { index ->
            val actualIndex = (index - initialIndex).floorMod(actualCount)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ) {
                AsyncImage(
                    model = imageModel(data?.getOrNull(actualIndex)),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickableNoRipple {
                            onItemClick.invoke(actualIndex)
                        },
                    contentScale = contentScale,
                )
                content(data?.getOrNull(actualIndex))
            }
        }
        if (showIndicator) {
            HorizontalPagerIndicator(
                pagerState = pageState,
                count = actualCount,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                activeColor = activeColor,
                inactiveColor = inactiveColor
            )
        }
    }
}

/**
 * @desc：HorizontalPagerIndicator
 * @date：2023/08/16 11:53
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerIndicator(
    pagerState: PagerState,
    count: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    inactiveColor: Color = activeColor.copy(ContentAlpha.disabled),
    indicatorWidth: Dp = 8.dp,
    indicatorHeight: Dp = indicatorWidth,
    spacing: Dp = indicatorWidth,
    indicatorShape: Shape = CircleShape,
) {

    val indicatorWidthPx = LocalDensity.current.run { indicatorWidth.roundToPx() }
    val spacingPx = LocalDensity.current.run { spacing.roundToPx() }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val indicatorModifier = Modifier
                .size(width = indicatorWidth, height = indicatorHeight)
                .background(color = inactiveColor, shape = indicatorShape)
            repeat(count) {
                Box(indicatorModifier)
            }
        }
        Box(
            Modifier
                .offset {
                    val scrollPosition =
                        ((pagerState.currentPage - Int.MAX_VALUE / 2).floorMod(count) + pagerState.currentPageOffsetFraction)
                            .coerceIn(
                                0f,
                                (count - 1)
                                    .coerceAtLeast(0)
                                    .toFloat(),
                            )
                    IntOffset(
                        x = ((spacingPx + indicatorWidthPx) * scrollPosition).toInt(),
                        y = 0,
                    )
                }
                .size(width = indicatorWidth, height = indicatorHeight)
                .background(
                    color = activeColor,
                    shape = indicatorShape,
                )
        )
    }
}


