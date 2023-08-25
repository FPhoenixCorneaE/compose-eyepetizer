package com.fphoenixcorneae.eyepetizer.mvi.ui.page.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.ext.clickableNoRipple
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavHostController
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Blue
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Gray
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.GrayDark
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.LocalThemeColors
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.CustomEditText
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.SystemUiScaffold
import com.fphoenixcorneae.eyepetizer.mvi.viewmodel.SearchAction
import com.fphoenixcorneae.eyepetizer.mvi.viewmodel.SearchViewModel

/**
 * @desc：搜索
 * @date：2023/08/25 13:48
 */
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SearchScreen() {
    val viewModel = viewModel<SearchViewModel>()
    val searchUiState by viewModel.searchUiState.collectAsState()
    LaunchedEffect(key1 = Unit) {
        viewModel.dispatchIntent(SearchAction.GetHotSearchKeys)
    }
    SystemUiScaffold {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchToolbar()
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    SearchHotKeysHeader()
                }
                items(searchUiState.hotSearchKeys?.size ?: 0) {
                    val item = searchUiState.hotSearchKeys?.getOrNull(it) ?: return@items
                    SearchHotKeysItem(item = item)
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SearchToolbar() {
    val context = LocalContext.current
    var searchKey by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 0.dp),
    ) {
        CustomEditText(
            text = searchKey,
            onValueChange = {
                searchKey = it
            },
            modifier = Modifier
                .padding(start = 14.dp)
                .width(width = 0.dp)
                .weight(weight = 1f)
                .height(height = 28.dp),
            hint = stringResource(R.string.search_hint),
            hintColor = GrayDark,
            backgroundColor = Color(0xFFF0F0F0),
            corners = 6.dp,
            startIcon = R.drawable.ic_search_black_24dp,
            startFocusedIcon = R.drawable.ic_search_black_24dp,
            startIconSize = 16.dp,
            startIconMargin = 16.dp,
            paddingEnd = 16.dp,
            iconSpacing = 4.dp,
            textStyle = TextStyle(
                color = LocalThemeColors.current.textColorSecondary,
                fontSize = 12.sp,
                fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                    ?.let { FontFamily(it) },
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {

                },
            ),
        )
        Box(
            modifier = Modifier
                .size(size = 48.dp)
                .clickableNoRipple {
                    NavHostController
                        .get()
                        .navigateUp()
                },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.cancel),
                fontSize = 14.sp,
                fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                    ?.let { FontFamily(it) },
            )
        }
    }
}

@Composable
private fun SearchHotKeysHeader() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = stringResource(R.string.search_hot_keys),
            color = LocalThemeColors.current.textColor,
            fontSize = 21.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
            modifier = Modifier.padding(horizontal = 14.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SearchHotKeysItem(item: String = "艺术") {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
    ) {
        Text(
            text = item,
            color = Blue,
            fontSize = 14.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .align(alignment = Alignment.CenterStart),
            textAlign = TextAlign.Center,
        )
        Divider(
            thickness = 0.2.dp,
            color = Gray,
            modifier = Modifier
                .padding(start = 14.dp)
                .align(alignment = Alignment.BottomStart),
        )
    }
}
