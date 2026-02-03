package com.example.exchangerateapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.exchangerateapp.R

@Composable
fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    onTrailingButtonClick: () -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.search_icon),
                contentDescription = null
            )
        },
        trailingIcon = {
            if(query.isNotEmpty()){
                IconButton(onClick = {onTrailingButtonClick()}) {
                    Icon(
                        painter = painterResource(R.drawable.cancel_icon),
                        contentDescription = ""
                    )
                }
            }
        },
        placeholder = {
            Text(stringResource(R.string.search_placeholder))
        },
        shape = RoundedCornerShape(16.dp),
        singleLine = true
    )
}

