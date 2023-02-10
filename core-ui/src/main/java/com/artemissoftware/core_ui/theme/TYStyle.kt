package com.artemissoftware.core_ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artemissoftware.core_ui.R

private val InterFont = FontFamily(
    Font(R.font.inter_regular),
    Font(R.font.inter_thin, FontWeight.Thin),
    Font(R.font.inter_light, FontWeight.Light),
    Font(R.font.inter_extralight, FontWeight.ExtraLight),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_extrabold, FontWeight.ExtraBold)
)


val InterTypography = Typography(
    h4 = TextStyle(
        fontFamily = InterFont,
        fontWeight = FontWeight.W600,
        fontSize = 30.sp
    ),
    h5 = TextStyle(
        fontFamily = InterFont,
        fontWeight = FontWeight.W600,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
        fontFamily = InterFont,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = InterFont,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = InterFont,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    body1 = TextStyle(
        fontFamily = InterFont,
        fontWeight = FontWeight.Normal, //W400
        fontSize = 16.sp,
        lineHeight = 30.sp,
        letterSpacing = 5.sp
    ),
    body2 = TextStyle(
        fontFamily = InterFont,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = InterFont,
        fontWeight = FontWeight.W700,
        fontSize = 16.sp,
        lineHeight = 30.sp,
    ),
    caption = TextStyle(
        fontFamily = InterFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 14.4.sp,
    ),
    overline = TextStyle(
        fontFamily = InterFont,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp
    )
)
