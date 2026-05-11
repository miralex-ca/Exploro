package com.muralex.myapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muralex.myapp.viewmodel.screens.countrydetail.CountryDetailState

@Composable
fun CountryDetailScreen(
    countryDetailState: CountryDetailState,
) {

    if (countryDetailState.isLoading) {

        //LoadingScreen()

    } else {
        val data = countryDetailState.countryInfo
        Column(modifier = Modifier.padding(10.dp)) {

            DataElement("capital", data?.capital ?: "")
            DataElement(" population", data?.population.toString() ?: "")
            DataElement(" region", data?.region ?: "")
            DataElement(" flag", data?.flagPngUrl ?: "null")

            Spacer(modifier = Modifier.size(24.dp))


        }

    }
}



@Composable
fun DataElement(label : String, value : String = "", percentage : String = "") {
    Row {
        Text(text = "$label: ", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
        if (percentage!="") {
            Text(text = " ($percentage)", style = MaterialTheme.typography.bodyLarge)
        }
    }
}