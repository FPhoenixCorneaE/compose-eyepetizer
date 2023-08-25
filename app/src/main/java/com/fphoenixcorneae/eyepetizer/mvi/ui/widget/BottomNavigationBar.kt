package com.fphoenixcorneae.eyepetizer.mvi.ui.widget

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.ext.clickableNoRipple
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.GrayDark
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.LocalThemeColors

/**
 * @desc：
 * @date：2023/08/09 14:00
 */
@Preview
@Composable
fun BottomNavigationBar(
    selectedIndex: Int = 0,
    onTabClick: (Int) -> Unit = {},
) {
    val navTabs = listOf(
        NavigationTab(
            name = stringResource(R.string.homepage),
            icon = R.mipmap.ic_tab_homepage_normal,
            selectedIcon = R.mipmap.ic_tab_homepage_selected,
            iconSize = 24.dp,
            color = LocalThemeColors.current.textColorTertiary,
            selectedColor = LocalThemeColors.current.textColorSecondary
        ),
        NavigationTab(
            name = stringResource(R.string.community),
            icon = R.mipmap.ic_tab_community_normal,
            selectedIcon = R.mipmap.ic_tab_community_selected,
            iconSize = 20.dp,
            color = LocalThemeColors.current.textColorTertiary,
            selectedColor = LocalThemeColors.current.textColorSecondary
        ),
        null,
        NavigationTab(
            name = stringResource(R.string.notification),
            icon = R.mipmap.ic_tab_notification_normal,
            selectedIcon = R.mipmap.ic_tab_notification_selected,
            iconSize = 22.dp,
            color = LocalThemeColors.current.textColorTertiary,
            selectedColor = LocalThemeColors.current.textColorSecondary
        ),
        NavigationTab(
            name = stringResource(R.string.mine),
            icon = R.mipmap.ic_tab_mine_normal,
            selectedIcon = R.mipmap.ic_tab_mine_selected,
            iconSize = 20.dp,
            color = LocalThemeColors.current.textColorTertiary,
            selectedColor = LocalThemeColors.current.textColorSecondary
        ),
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
    ) {
        Divider(color = GrayDark, thickness = 0.2.dp)
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color = LocalThemeColors.current.colorPrimary),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navTabs.forEachIndexed { index, navigationTab ->
                navigationTab?.let {
                    BottomNavigationBarItem(navigationTab = it, isSelected = selectedIndex == index) {
                        if (selectedIndex != index) {
                            onTabClick(index)
                        }
                    }
                } ?: Image(
                    painter = painterResource(id = R.mipmap.ic_tab_add),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clickableNoRipple {
                            if (selectedIndex != index) {
                                onTabClick(index)
                            }
                        }
                )
            }
        }
    }
}

/**
 * @desc：
 * @date：2023/08/09 15:40
 */
@Composable
private fun BottomNavigationBarItem(
    navigationTab: NavigationTab,
    isSelected: Boolean,
    onTabClick: () -> Unit,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .wrapContentSize()
            .clickableNoRipple {
                onTabClick()
            },
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.size(24.dp)) {
            Image(
                painter = painterResource(id = if (isSelected) navigationTab.selectedIcon else navigationTab.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(navigationTab.iconSize)
                    .align(alignment = Alignment.Center),
            )
        }
        Text(
            text = navigationTab.name,
            color = if (isSelected) navigationTab.selectedColor else navigationTab.color,
            fontSize = if (isSelected) navigationTab.textSize else navigationTab.selectedTextSize,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)?.let { FontFamily(it) }
        )
    }
}

/**
 * @desc：
 * @date：2023/08/09 14:19
 */
@Keep
private data class NavigationTab(
    val name: String,
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int,
    val iconSize: Dp = 24.dp,
    val color: Color = Color.Gray,
    val selectedColor: Color = Color.Black,
    val textSize: TextUnit = 8.sp,
    val selectedTextSize: TextUnit = 8.sp,
)