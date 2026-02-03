package com.example.exchangerateapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exchangerateapp.presentation.models.CurrencyUI
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import com.example.exchangerateapp.R

@Composable
fun CurrencyList(
    favourites: List<CurrencyUI>,
    currencies: List<CurrencyUI>,
    onFavouriteClick: (String, Boolean) -> Unit,
    onRefresh: () -> Unit,
    onItemClick: (com.example.exchangerateapp.presentation.models.CurrencyUI) -> Unit,
    isRefreshing: Boolean
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = onRefresh
    ) {
        if (favourites.isEmpty() && currencies.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(R.drawable.nothing_icon),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        stringResource(R.string.nothing_found),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
        else{
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                if (favourites.isNotEmpty()) {
                    item {
                        Text(
                            stringResource(R.string.favourites_label),
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    items(favourites) {
                        CurrencyCard(it, onFavouriteClick, onItemClick)
                    }

                    item {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            thickness = DividerDefaults.Thickness,
                            color = DividerDefaults.color
                        )
                    }
                }
                val grouped = currencies.groupBy { it.currency.name.first() }

                grouped.forEach { (letter, list) ->
                    stickyHeader {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(8.dp)
                        ) {
                            Text(letter.toString())
                        }
                    }
                    items(list) {
                        CurrencyCard(it, onFavouriteClick, onItemClick)
                    }
                }
            }
        }
    }
}