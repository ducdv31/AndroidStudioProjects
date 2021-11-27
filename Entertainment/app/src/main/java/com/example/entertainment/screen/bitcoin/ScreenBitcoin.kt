package com.example.entertainment.screen.bitcoin

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.entertainment.data.EMPTY
import com.example.entertainment.data.bitcoin.model.Time
import com.example.entertainment.data.bitcoin.model.UnitCurrency
import com.example.entertainment.screen.bitcoin.viewmodel.BitcoinViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState

@Composable
fun ScreenBitcoin(
    innerPadding: PaddingValues,
    bitcoinViewModel: BitcoinViewModel,
    scrollState: ScrollState
) {
    SwipeRefresh(
        state = SwipeRefreshState(false),
        onRefresh = { bitcoinViewModel.getBitcoinPrice() },
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(
                    state = scrollState
                )
                .fillMaxWidth()
        ) {
            if (bitcoinViewModel.isLoading.value) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(CenterHorizontally)
                ) {
                    CircularProgressIndicator(
                        color = Color.Red
                    )
                }
            } else {
                UnitCurrencyItem(unitCurrency = bitcoinViewModel.bpi.value?.USD)
                UnitCurrencyItem(unitCurrency = bitcoinViewModel.bpi.value?.GBP)
                UnitCurrencyItem(unitCurrency = bitcoinViewModel.bpi.value?.EUR)
                UpdateTime(time = bitcoinViewModel.time.value)
            }
        }
    }
}

@Composable
fun UnitCurrencyItem(
    unitCurrency: UnitCurrency?
) {
    if (unitCurrency != null) {
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${unitCurrency.description} (${unitCurrency.code})",
                    style = TextStyle(
                        color = Color.Red,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = unitCurrency.rate ?: EMPTY,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun UpdateTime(
    time: Time?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(CenterHorizontally)
    ) {
        if (time != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Updated ${time.updated}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Updated ISO: ${time.updatedISO}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Updated Uk: ${time.updatedUk}")
        }
    }
}