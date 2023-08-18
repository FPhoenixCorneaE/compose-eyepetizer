package com.fphoenixcorneae.eyepetizer.mvi.ui.widget

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.delay
import kotlin.math.roundToLong

/**
 * @desc：打字机效果
 * @date：2023/08/18 11:06
 * @param fixedText    默认显示的文字
 * @param animatedText 需要打字机效果的文字
 */
@Composable
fun TypewriterText(
    animatedText: String,
    modifier: Modifier = Modifier,
    showAnimated: Boolean = true,
    fixedText: String = "",
    typingInterval: Long = 0,
    duration: Long = 0,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
) {
    var textToDisplay by remember { mutableStateOf(if (showAnimated) "" else fixedText + animatedText) }
    LaunchedEffect(key1 = animatedText) {
        if (showAnimated) {
            animatedText.forEachIndexed { charIndex, _ ->
                textToDisplay = fixedText + animatedText.substring(startIndex = 0, endIndex = charIndex + 1)
                if (typingInterval > 0) {
                    delay(typingInterval)
                } else if (duration > 0) {
                    delay(duration.toFloat().div(animatedText.length).roundToLong())
                } else {
                    delay(200)
                }
            }
        }
    }
    // textToDisplay改变触发重组。
    Text(
        text = textToDisplay,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = style,
    )
}