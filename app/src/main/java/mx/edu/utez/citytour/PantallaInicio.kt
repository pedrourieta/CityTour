package mx.edu.utez.citytour

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaInicio(onEntrar: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "CityTour", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text(text = "Museo virtual de Cuernavaca")

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Monumentos", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.catedral_atrio),
            contentDescription = "Catedral de Cuernavaca",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Catedral de Cuernavaca", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = "Convento franciscano del siglo XVI")

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onEntrar, modifier = Modifier.testTag("btn_entrar")) {
            Text(text = "Ver monumento")
        }
    }
}
