package com.rifqi.fitmate.ui.model

import androidx.annotation.DrawableRes
import com.rifqi.fitmate.ui.screens.routelist.ScreenRoute

data class NavigationItem(
    val title: String,
    @DrawableRes val icon: Int,
    val screen: ScreenRoute
)
