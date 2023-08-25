package com.fphoenixcorneae.eyepetizer.mvi.ui.page.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavHostController
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavRoute
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Blue
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.LocalThemeColors
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White50
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White85

/**
 * @desc：通知-互动
 * @date：2023/08/24 17:26
 */
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun InteractionScreen() {
    NotificationLoginGuide()
}

@Composable
fun NotificationLoginGuide() {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_login_prompt_gray_24dp),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 88.dp)
                .size(size = 100.dp),
        )
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 15.sp,
                        fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                            ?.let { FontFamily(it) },
                    )
                ) {
                    append(stringResource(R.string.notification_login_prompt_1))
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 12.sp,
                        fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                            ?.let { FontFamily(it) },
                    )
                ) {
                    append(stringResource(R.string.notification_login_prompt_2))
                }
            },
            color = LocalThemeColors.current.textColor,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            modifier = Modifier.padding(top = 36.dp),
        )
        // 登录
        Button(
            onClick = {
                NavHostController.get().navigate(NavRoute.LOGIN)
            },
            modifier = Modifier
                .padding(top = 60.dp)
                .width(width = 220.dp)
                .height(height = 40.dp),
            shape = RoundedCornerShape(size = 4.dp),
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = White50,
                disabledContentColor = White85,
                containerColor = Blue,
                contentColor = White,
            ),
            contentPadding = PaddingValues(all = 0.dp),
        ) {
            Text(
                text = stringResource(R.string.login),
                fontSize = 15.sp,
                fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                    ?.let { FontFamily(it) },
            )
        }
    }
}