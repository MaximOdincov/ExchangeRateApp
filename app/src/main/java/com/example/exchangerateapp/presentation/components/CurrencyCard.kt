package com.example.exchangerateapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.exchangerateapp.R
import com.example.exchangerateapp.presentation.models.ChangeColor
import com.example.exchangerateapp.presentation.models.CurrencyUI

@Composable
fun CurrencyCard(
    currencyUi: CurrencyUI,
    onFavouriteClick: (String, Boolean) -> Unit,
    onItemClick: (CurrencyUI) -> Unit
) {
    val background =
        if (currencyUi.isFavourite)
            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        else
            MaterialTheme.colorScheme.surface

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onItemClick(currencyUi) },
        colors = CardDefaults.cardColors(background)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(
                    if (currencyUi.isFavourite) R.drawable.favourite_filled else R.drawable.favourite_icon
                ),
                contentDescription = null,
                tint = if (currencyUi.isFavourite)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .clickable {
                        onFavouriteClick(
                            currencyUi.currency.id,
                            currencyUi.isFavourite
                        )
                    }
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = currencyUi.currency.name,
                modifier = Modifier.weight(1f)
            )

            Column(horizontalAlignment = Alignment.End) {
                Text(currencyUi.currency.value.toString())

                Text(
                    currencyUi.changePercent,
                    color = when (currencyUi.changeColor) {
                        ChangeColor.POSITIVE -> Color.Green
                        ChangeColor.NEGATIVE -> Color.Red
                        ChangeColor.NEUTRAL -> Color.Gray
                    }
                )
            }
        }
    }
}
