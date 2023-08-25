package com.fphoenixcorneae.eyepetizer.mvi.ui.page.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.fphoenixcorneae.compose.ext.toast
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.const.Constant
import com.fphoenixcorneae.eyepetizer.ext.clickableNoRipple
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavHostController
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavRoute
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black85
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Blue
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Gray70
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White10
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White50
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White85
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.CustomEditText
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.SystemUiScaffold

/**
 * @desc：登录
 * @date：2023/08/09 16:08
 */
@Preview
@Composable
fun LoginScreen() {
    val context = LocalContext.current
    SystemUiScaffold(statusBarsPadding = false) {
        Image(
            painter = painterResource(id = R.mipmap.img_bg_login),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
        )
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 44.dp)
            ) {
                // 关闭页面
                Image(
                    painter = painterResource(id = R.drawable.ic_close_white_24dp),
                    contentDescription = null,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterStart)
                        .padding(start = 14.dp)
                        .size(size = 28.dp)
                        .clickableNoRipple {
                            NavHostController
                                .get()
                                .navigateUp()
                        },
                )
                Text(
                    text = stringResource(R.string.forget_password),
                    color = White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterEnd)
                        .padding(horizontal = 14.dp)
                        .clickableNoRipple {
                            NavHostController
                                .get()
                                .navigate("${NavRoute.WEB}?${Constant.Key.WEB_URL}=${Constant.Url.FORGET_PASSWORD}")
                        },
                )
            }
            Image(
                painter = painterResource(id = R.mipmap.ic_logo_slogan),
                contentDescription = null,
                modifier = Modifier
                    .width(width = 134.dp)
                    .height(height = 40.dp)
                    .padding(top = 14.dp),
            )
            Text(
                text = stringResource(R.string.login_prompt),
                color = White,
                fontSize = 12.sp,
                fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                    ?.let { FontFamily(it) },
                modifier = Modifier.padding(top = 24.dp),
                textAlign = TextAlign.Center,
            )
        }
        Column(
            modifier = Modifier.align(alignment = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 账号
            var accountFocused by remember { mutableStateOf(false) }
            var account by remember { mutableStateOf("") }
            CustomEditText(
                modifier = Modifier
                    .padding(horizontal = 60.dp)
                    .fillMaxWidth()
                    .height(44.dp),
                text = account,
                onValueChange = {
                    account = it
                },
                onFocusChanged = {
                    accountFocused = it
                },
                backgroundColor = Color.Transparent,
                hint = stringResource(R.string.login_account_hint),
                hintColor = White,
                textStyle = TextStyle(
                    color = White,
                    fontSize = 12.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                ),
                startIcon = R.drawable.ic_account_white_24dp,
                startFocusedIcon = R.drawable.ic_account_white_24dp,
                startIconSize = 20.dp,
                paddingEnd = 16.dp,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            )
            Divider(
                thickness = 0.5.dp,
                color = if (accountFocused) White else White85,
                modifier = Modifier.padding(horizontal = 60.dp),
            )
            // 密码
            var passwordFocused by remember { mutableStateOf(false) }
            var password by remember { mutableStateOf("") }
            CustomEditText(
                modifier = Modifier
                    .padding(start = 60.dp, top = 12.dp, end = 60.dp)
                    .fillMaxWidth()
                    .height(44.dp),
                text = password,
                onValueChange = {
                    password = it
                },
                onFocusChanged = {
                    passwordFocused = it
                },
                backgroundColor = Color.Transparent,
                hint = stringResource(R.string.login_password_hint),
                hintColor = White,
                textStyle = TextStyle(
                    color = White,
                    fontSize = 12.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                ),
                startIcon = R.drawable.ic_password_white_24dp,
                startFocusedIcon = R.drawable.ic_password_white_24dp,
                startIconSize = 20.dp,
                paddingEnd = 16.dp,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                visualTransformation = PasswordVisualTransformation(),
            )
            Divider(
                thickness = 0.5.dp,
                color = if (passwordFocused) White else White85,
                modifier = Modifier.padding(horizontal = 60.dp),
            )
            // 登录
            Button(
                onClick = {
                    context.getString(R.string.coming_soon).toast()
                },
                modifier = Modifier
                    .padding(start = 60.dp, top = 32.dp, end = 60.dp)
                    .fillMaxWidth()
                    .height(height = 40.dp),
                enabled = account.isNotBlank() && password.isNotBlank(),
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
                    fontSize = 16.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                        ?.let { FontFamily(it) },
                )
            }
            Row(
                modifier = Modifier
                    .padding(start = 60.dp, top = 32.dp, end = 60.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // 用户注册
                Text(
                    text = stringResource(R.string.user_register),
                    color = White,
                    fontSize = 12.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                        ?.let { FontFamily(it) },
                    modifier = Modifier.clickableNoRipple {
                        NavHostController.get()
                            .navigate("${NavRoute.WEB}?${Constant.Key.WEB_URL}=${Constant.Url.USER_REGISTER}")
                    },
                )
                // 作者登录
                Text(
                    text = stringResource(R.string.author_login),
                    color = White,
                    fontSize = 12.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                        ?.let { FontFamily(it) },
                    modifier = Modifier.clickableNoRipple {
                        NavHostController.get()
                            .navigate("${NavRoute.WEB}?${Constant.Key.WEB_URL}=${Constant.Url.AUTHOR_LOGIN}")
                    },
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(alignment = Alignment.BottomCenter)
                .padding(start = 60.dp, end = 60.dp, bottom = 100.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_login_method_wechat),
                contentDescription = null,
                modifier = Modifier
                    .size(size = 44.dp)
                    .clickableNoRipple {
                        context
                            .getString(R.string.coming_soon)
                            .toast()
                    },
            )
            Image(
                painter = painterResource(id = R.mipmap.ic_login_method_weibo),
                contentDescription = null,
                modifier = Modifier
                    .size(size = 44.dp)
                    .clickableNoRipple {
                        context
                            .getString(R.string.coming_soon)
                            .toast()
                    },
            )
            Image(
                painter = painterResource(id = R.mipmap.ic_login_method_qq),
                contentDescription = null,
                modifier = Modifier
                    .size(size = 44.dp)
                    .clickableNoRipple {
                        context
                            .getString(R.string.coming_soon)
                            .toast()
                    },
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 48.dp)
                .align(alignment = Alignment.BottomCenter)
                .background(color = Black85),
            contentAlignment = Alignment.Center,
        ) {
            val loginOrRegisterAgree = stringResource(R.string.login_or_register_agree)
            val userServiceAgreement = stringResource(R.string.user_service_agreement)
            ClickableText(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = Gray70,
                            fontSize = 12.sp,
                            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                                ?.let { FontFamily(it) },
                        )
                    ) {
                        append(loginOrRegisterAgree)
                    }
                    withStyle(
                        SpanStyle(
                            color = White10,
                            fontSize = 12.sp,
                            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                                ?.let { FontFamily(it) },
                        )
                    ) {
                        append(stringResource(R.string.user_service_agreement))
                    }
                },
                onClick = {
                    if (it in loginOrRegisterAgree.length..loginOrRegisterAgree.length + userServiceAgreement.length) {
                        // 点击了用户服务协议
                        NavHostController.get()
                            .navigate("${NavRoute.WEB}?${Constant.Key.WEB_URL}=${Constant.Url.USER_SERVICE_AGREEMENT}")
                    }
                },
            )
        }
    }
}