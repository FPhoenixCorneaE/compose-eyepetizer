package com.fphoenixcorneae.eyepetizer.mvi.ui.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fphoenixcorneae.eyepetizer.ext.clickableNoRipple

/**
 * @param modifier             修饰符
 * @param text                 输入文字
 * @param onValueChange        输入文字变化监听
 * @param onFocusChanged       焦点变化监听
 * @param backgroundColor      背景颜色
 * @param corners              背景圆角
 * @param borderStroke         背景边框
 * @param paddingEnd           输入框右边内边距
 * @param hint                 空字符时的提示文字
 * @param hintColor            提示文字颜色
 * @param startIcon            左侧图标;  -1 则不显示
 * @param startFocusedIcon     获取焦点时左侧图标;  -1 则不显示
 * @param startIconSize        左侧图标大小
 * @param startIconMargin      左侧图标左边外边距
 * @param iconSpacing          左侧图标与文字的距离; 相当于: drawablePadding
 * @param endIcon              右侧图标;  -1 则不显示
 * @param endIconSize          右侧图标大小
 * @param onEndIconClick       右侧图标点击监听
 * @param singleLine           是否单行
 * @param enabled              控制BasicTextField的启用状态。当为false时，文本字段将既不可编辑也不可聚焦，文本字段的输入将不可选择
 * @param readOnly             控制BasicTextField的可编辑状态。当为true时，文本字段不能被修改，但是，用户可以聚焦它并从中复制文本。只读文本字段通常用于显示用户无法编辑的预填写表单
 * @param textStyle            文字样式
 * @param keyboardOptions      键盘设置
 * @param keyboardActions      键盘点击监听
 * @param visualTransformation 用于更改输入的可视表示的可视转换过滤器。默认情况下，不应用可视转换。
 * @param cursorBrush          光标
 */
@Composable
fun CustomEditText(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit = {},
    backgroundColor: Color = White,
    corners: Dp = 2.dp,
    borderStroke: BorderStroke = BorderStroke(width = 1.dp, color = Transparent),
    paddingEnd: Dp = 12.dp,
    hint: String = "",
    hintColor: Color = Gray,
    @DrawableRes startIcon: Int = -1,
    @DrawableRes startFocusedIcon: Int = -1,
    startIconSize: Dp = 12.dp,
    startIconMargin: Dp = 0.dp,
    iconSpacing: Dp = 4.dp,
    @DrawableRes endIcon: Int = -1,
    endIconSize: Dp = 12.dp,
    onEndIconClick: () -> Unit = {},
    singleLine: Boolean = true,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    cursorBrush: Brush = SolidColor(MaterialTheme.colorScheme.primary),
) {
    // 焦点, 用于控制左侧Icon、底部分割线状态以及右侧叉号显示
    var hasFocus by rememberSaveable { mutableStateOf(false) }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxSize()
                .onFocusChanged {
                    hasFocus = it.isFocused
                    onFocusChanged(it.isFocused)
                },
            singleLine = singleLine,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            cursorBrush = cursorBrush,
            decorationBox = @Composable { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = backgroundColor, shape = RoundedCornerShape(size = corners))
                        .border(border = borderStroke, shape = RoundedCornerShape(size = corners)),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // -1 不显示 左侧Icon
                    if (!hasFocus && startIcon != -1) {
                        Image(
                            painter = painterResource(id = startIcon),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = startIconMargin)
                                .size(size = startIconSize),
                        )
                        Spacer(modifier = Modifier.width(iconSpacing))
                    } else if (hasFocus && startFocusedIcon != -1) {
                        Image(
                            painter = painterResource(id = startFocusedIcon),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = startIconMargin)
                                .size(size = startIconSize),
                        )
                        Spacer(modifier = Modifier.width(iconSpacing))
                    }

                    Box(
                        modifier = Modifier
                            .padding(end = paddingEnd)
                            .weight(1f)
                    ) {
                        // 当空字符时, 显示hint
                        if (text.isEmpty()) {
                            Text(text = hint, color = hintColor, style = textStyle)
                        }
                        // 原本输入框的内容
                        innerTextField()
                    }

                    // 存在焦点 且 有输入内容时. 显示右侧Icon
                    if (hasFocus && text.isNotEmpty() && endIcon != -1) {
                        Image(
                            painter = painterResource(id = endIcon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(size = endIconSize)
                                .padding(2.dp)
                                .clickableNoRipple {
                                    onEndIconClick()
                                },
                        )
                    }
                }
            },
        )
    }
}
