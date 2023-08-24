package com.fphoenixcorneae.eyepetizer.mvi.ui.widget

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.ext.clickableNoRipple
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.LocalThemeColors

/**
 * @desc：选项卡
 * @date：2023/08/09 16:14
 * @param selectedTabIndex     默认选项下标
 * @param tabs                 选项内容
 * @param modifier             布局修饰符
 * @param containerColor       背景色
 * @param contentColor         选项内容颜色
 * @param selectedContentColor 选中选项内容颜色
 * @param fontSize             内容文字大小
 * @param fontFamily           内容文字字体
 * @param isFixTabWidth        选项宽度是否固定，默认为true
 * @param tabWidth             选项宽度
 * @param indicatorWidth       指示器宽度
 * @param indicatorHeight      指示器高度
 * @param indicatorRadius      指示器圆角
 * @param bottomSpace          底部内边距
 * @param dividerColor         分割线颜色
 * @param onTabClick           选项点击事件
 */
@Composable
fun TabRow(
    selectedTabIndex: Int,
    tabs: List<String>,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    contentColor: Color = Color.Gray,
    selectedContentColor: Color = Color.Black,
    fontSize: TextUnit = 12.sp,
    fontFamily: FontFamily? = null,
    isFixTabWidth: Boolean = true,
    tabWidth: Dp = 66.dp,
    indicatorWidth: Dp = 12.dp,
    indicatorHeight: Dp = 3.dp,
    indicatorRadius: Dp = 2.5.dp,
    bottomSpace: Dp = 4.dp,
    dividerColor: Color = Color.LightGray,
    onTabClick: (Int) -> Unit = {},
) {
    var tabWidth = tabWidth
    var tabOffset = 0.dp
    BoxWithConstraints(modifier = modifier.background(color = containerColor)) {
        if (!isFixTabWidth && tabs.isNotEmpty()) {
            tabWidth = maxWidth / tabs.size
        } else {
            tabOffset = (maxWidth - tabWidth * tabs.size) / 2
        }
        Column {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(0.dp)
                    .weight(weight = 1f)
                    .align(alignment = Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                tabs.forEachIndexed { index, s ->
                    Box(
                        modifier = Modifier
                            .width(tabWidth)
                            .fillMaxHeight()
                            .clickableNoRipple {
                                if (selectedTabIndex != index) {
                                    onTabClick(index)
                                }
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = s,
                            color = if (selectedTabIndex == index) selectedContentColor else contentColor,
                            fontSize = fontSize,
                            fontFamily = fontFamily,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else null,
                        )
                    }
                }
            }
            val indicatorOffset by animateDpAsState(
                targetValue = tabWidth * selectedTabIndex,
                animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
            )
            Box(
                modifier = Modifier
                    .width(indicatorWidth)
                    .height(indicatorHeight)
                    .offset(x = tabOffset + indicatorOffset + (tabWidth - indicatorWidth) / 2)
                    .background(
                        color = selectedContentColor,
                        shape = RoundedCornerShape(size = indicatorRadius),
                    )
            )
            Spacer(modifier = Modifier.height(bottomSpace))
            Divider(color = dividerColor, thickness = 0.2.dp)
        }
    }
}

@Preview
@Composable
fun PreviewTabRow() {
    TabRow(
        selectedTabIndex = 1,
        tabs = listOf(
            stringResource(R.string.discovery),
            stringResource(R.string.commend),
            stringResource(R.string.daily)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(LocalThemeColors.current.backgroundColor),
        containerColor = LocalThemeColors.current.colorPrimary,
        contentColor = LocalThemeColors.current.textColorTertiary,
        selectedContentColor = LocalThemeColors.current.textColorSecondary,
        dividerColor = Color.Red,
    )
}