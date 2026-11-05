package mx.edu.utez.citytour

import android.media.MediaPlayer
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Imagenes del carrusel (res/drawable-nodpi)
val imagenes = listOf(
    R.drawable.catedral_atrio,
    R.drawable.catedral_torre,
    R.drawable.catedral_interior,
    R.drawable.catedral_tercera_orden,
    R.drawable.catedral_entrada
)

val descripciones = listOf(
    "Atrio y fachada principal",
    "Torre del campanario",
    "Nave con murales y vitrales",
    "Capilla de la Tercera Orden",
    "Acceso lateral del templo"
)

@Composable
fun PantallaCatedral(onRegresar: () -> Unit) {
    val context = LocalContext.current

    // Variables de estado
    var imagenActual by remember { mutableIntStateOf(0) }
    var estado by remember { mutableStateOf("Detenido") }
    var volumen by remember { mutableFloatStateOf(0.8f) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    // Cuando se sale de la pantalla se libera el audio
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    BackHandler { onRegresar() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Button(onClick = onRegresar) {
            Text(text = "Regresar")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Catedral de Cuernavaca", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        // ---------- Carrusel de imagenes ----------
        Image(
            painter = painterResource(id = imagenes[imagenActual]),
            contentDescription = descripciones[imagenActual],
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        )
        Text(text = descripciones[imagenActual])
        Text(
            text = "Imagen ${imagenActual + 1} de ${imagenes.size}",
            modifier = Modifier.testTag("contador")
        )

        Row {
            Button(onClick = {
                if (imagenActual > 0) imagenActual--
                else imagenActual = imagenes.size - 1
            }) {
                Text(text = "Anterior")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (imagenActual < imagenes.size - 1) imagenActual++
                    else imagenActual = 0
                },
                modifier = Modifier.testTag("btn_siguiente")
            ) {
                Text(text = "Siguiente")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ---------- Audio ----------
        Text(text = "Audioguía", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = "Estado: $estado", modifier = Modifier.testTag("estado"))

        Row {
            Button(
                onClick = {
                    if (mediaPlayer == null) {
                        // se carga el audio desde res/raw
                        mediaPlayer = MediaPlayer.create(context, R.raw.narracion_catedral)
                        mediaPlayer?.setVolume(volumen, volumen)
                    }
                    mediaPlayer?.start()
                    estado = "Reproduciendo"
                },
                modifier = Modifier.testTag("btn_play")
            ) {
                Text(text = "Play")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                        mediaPlayer?.pause()
                        estado = "En pausa"
                    }
                },
                modifier = Modifier.testTag("btn_pausa")
            ) {
                Text(text = "Pausa")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                    mediaPlayer = null
                    estado = "Detenido"
                },
                modifier = Modifier.testTag("btn_stop")
            ) {
                Text(text = "Stop")
            }
        }

        Text(text = "Volumen: ${(volumen * 100).toInt()}%")
        Slider(
            value = volumen,
            onValueChange = {
                volumen = it
                mediaPlayer?.setVolume(volumen, volumen)
            },
            modifier = Modifier.testTag("volumen")
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Historia", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(
            text = "Fundada por los franciscanos hacia 1529 y dedicada a la Asunción de María, " +
                "es uno de los primeros conventos de América. Tiene aspecto de fortaleza, una " +
                "capilla abierta, la Capilla de la Tercera Orden y murales del martirio de " +
                "San Felipe de Jesús. Es catedral desde 1891 y Patrimonio de la Humanidad desde 1994."
        )
    }
}
