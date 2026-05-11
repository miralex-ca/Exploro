package com.muralex.myapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.muralex.myapp.R
import com.muralex.myapp.viewmodel.screens.countrydetail.CountryDetailState

@Composable
fun CountryDetailScreen(
    countryDetailState: CountryDetailState,
) {

    if (countryDetailState.isLoading) {

        //LoadingScreen()

    } else {
        val data = countryDetailState.countryInfo?.country
        Column(modifier = Modifier.padding(10.dp)) {


            data?.let {

                val placeholderRes = when (it.id) {
                    "AFG" -> R.drawable.taliban_flag
                    else -> R.drawable.flag_placeholder
                }

                AsyncImage(
                    model = it.flagPngUrl,
                    contentDescription = null,
                    error = painterResource(placeholderRes),
                    modifier = Modifier
                        .clip(RoundedCornerShape(2.dp))
                        .height(150.dp)
                        .width(250.dp),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )

            }



            DataElement("", data?.officialName ?: "")
            DataElement("region: ", data?.subregion ?: "")
            DataElement("capital: ", data?.capital ?: "")
            DataElement("population: ", data?.population.toString() ?: "")
            DataElement("currency: ", "${data?.currencySymbol} (${data?.currencyName})")

            Spacer(modifier = Modifier.size(24.dp))


        }

    }
}



@Composable
fun DataElement(label : String, value : String = "", percentage : String = "") {
    Row {
        Text(text = label, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
        if (percentage!="") {
            Text(text = " ($percentage)", style = MaterialTheme.typography.bodyLarge)
        }
    }
}