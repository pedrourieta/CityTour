package mx.edu.utez.citytour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // false = pantalla de inicio, true = seccion de la catedral
                var verCatedral by remember { mutableStateOf(false) }

                if (verCatedral) {
                    PantallaCatedral(onRegresar = { verCatedral = false })
                } else {
                    PantallaInicio(onEntrar = { verCatedral = true })
                }
            }
        }
    }
}
