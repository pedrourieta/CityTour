package mx.edu.utez.citytour

import android.os.Looper
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.click
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.shadows.ShadowMediaPlayer
import java.time.Duration

// Pruebas de la app: navegacion, carrusel y botones de audio
@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34], qualifiers = RobolectricDeviceQualifiers.Pixel7)
class PruebasTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        ShadowMediaPlayer.setMediaInfoProvider { ShadowMediaPlayer.MediaInfo(66_000, 0) }
    }

    @Test
    fun probarApp() {
        rule.onRoot().captureRoboImage("capturas/01_inicio.png")

        rule.onNodeWithTag("btn_entrar").performClick()
        rule.onNodeWithTag("estado").assertTextEquals("Estado: Detenido")
        rule.onRoot().captureRoboImage("capturas/02_catedral.png")

        rule.onNodeWithTag("btn_siguiente").performClick()
        rule.onNodeWithTag("btn_siguiente").performClick()
        rule.onNodeWithTag("contador").assertTextEquals("Imagen 3 de 5")
        rule.onRoot().captureRoboImage("capturas/03_carrusel.png")

        rule.onNodeWithTag("btn_play").performClick()
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(5))
        rule.onNodeWithTag("estado").assertTextEquals("Estado: Reproduciendo")
        rule.onRoot().captureRoboImage("capturas/04_reproduciendo.png")

        rule.onNodeWithTag("volumen").performTouchInput { click(Offset(width * 0.45f, centerY)) }
        rule.onNodeWithTag("btn_pausa").performClick()
        rule.onNodeWithTag("estado").assertTextEquals("Estado: En pausa")
        rule.onRoot().captureRoboImage("capturas/05_pausa.png")

        rule.onNodeWithTag("btn_play").performClick()
        rule.onNodeWithTag("estado").assertTextEquals("Estado: Reproduciendo")

        rule.onNodeWithTag("btn_stop").performClick()
        rule.onNodeWithTag("estado").assertTextEquals("Estado: Detenido")
        rule.onRoot().captureRoboImage("capturas/06_detenido.png")
    }
}
