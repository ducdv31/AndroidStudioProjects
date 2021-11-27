package com.example.entertainment.ui.activity.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.entertainment.R


@Composable
fun MyTabRow(
    tabIndex: MutableState<Int>,
    listTab: List<MyTabList>,
    onClick: (String) -> Unit
) {
    val colorSelected: Color = Color.Red
    val colorUnSelected: Color = Color.White
    TabRow(
        selectedTabIndex = tabIndex.value,
        indicator = {
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(it[tabIndex.value]),
                color = colorSelected,
                height = 2.dp
            )
        }
    ) {
        listTab.forEachIndexed { index, myTabList ->
            Tab(selected = tabIndex.value == index,
                selectedContentColor = colorSelected,
                unselectedContentColor = colorUnSelected,
                onClick = {
                    tabIndex.value = index
                    onClick(myTabList.name)
                }) {
                Spacer(modifier = Modifier.height(4.dp))
                Image(
                    painter = painterResource(id = myTabList.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .height(32.dp),
                    colorFilter = ColorFilter.tint(
                        if (tabIndex.value == index)
                            colorSelected else colorUnSelected
                    )
                )
                Text(
                    text = stringResource(id = myTabList.title),
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

enum class MyTabList(
    @DrawableRes val icon: Int,
    @StringRes val title: Int
) {
    TabMovie(
        R.drawable.outline_live_tv_white_48dp,
        R.string.movie
    ),
    TabBitCoin(
        R.drawable.outline_attach_money_white_48dp,
        R.string.bitcoin
    )
}