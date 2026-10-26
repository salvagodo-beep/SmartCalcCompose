package com.example.smartcalccompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcalccompose.ui.theme.SmartCalcComposeTheme
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.TextButton
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import android.view.SoundEffectConstants
import androidx.compose.ui.platform.LocalView
import android.content.res.Configuration
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState

private val AzulSmartCalc = Color(0xFF3264E8)
private val AzulOscuro = Color(0xFF19365D)
private val Fondo = Color(0xFFF4F7FC)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartCalcComposeTheme {
                var pantallaActual by remember {
                    mutableStateOf("inicio")
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Fondo
                ) {
                    when (pantallaActual) {
                        "inicio" -> PantallaInicio(
                            onInicio = {
                                pantallaActual = "calculadora"
                            }
                        )

                        "calculadora" -> PantallaCalculadora(
                            onSalir = {
                                pantallaActual = "despedida"
                            }
                        )

                        "despedida" -> PantallaDespedida(
                            onVolver = {
                                pantallaActual = "inicio"
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PantallaInicio(onInicio: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "CALCULADORA",
            fontSize = 22.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(28.dp))

        Icon(
            imageVector = Icons.Default.Calculate,
            contentDescription = "Logo de SmartCalc",
            tint = Color(0xFF20AEB3),
            modifier = Modifier.size(72.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "SmartCalc",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = AzulOscuro
        )

        Spacer(modifier = Modifier.height(56.dp))

        Text(
            text = "Aplicación desarrollada por:",
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Salvador Godo Serrano",
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = onInicio,
            modifier = Modifier.width(220.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AzulSmartCalc
            )
        ) {
            Text(
                text = "INICIO",
                color = Color.White
            )
        }
    }
}



@Composable
fun PantallaCalculadora(onSalir: () -> Unit) {
    val vista = LocalView.current
    val configuracion = LocalConfiguration.current
    val esHorizontal =
        configuracion.orientation == Configuration.ORIENTATION_LANDSCAPE
    var modoNocturno by remember { mutableStateOf(false) }
    var menuTrigonometria by remember { mutableStateOf(false) }
    var menuFunciones by remember { mutableStateOf(false) }
    var teclaSeleccionada by remember { mutableStateOf("") }
    var mostrarAyuda by remember { mutableStateOf(false) }
    val filas = listOf(
        listOf("2nd", "π", "e", "C", "⌫"),
        listOf("x²", "1/x", "|x|", "exp", "mod"),
        listOf("√x", "(", ")", "n!", "÷"),
        listOf("xʸ", "7", "8", "9", "×"),
        listOf("10ˣ", "4", "5", "6", "−"),
        listOf("log", "1", "2", "3", "+"),
        listOf("ln", "+/−", "0", ",", "=")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (modoNocturno) Color(0xFF121212)
                else Fondo
            )

            .padding(
                horizontal = 12.dp,
                vertical = 100.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "SmartCalc",
            fontSize = 45.sp,

            fontWeight = FontWeight.Bold,
            color = if (modoNocturno) Color.White else AzulOscuro
        )

        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (esHorizontal) 48.dp else 80.dp),
            color = if (modoNocturno) Color(0xFF252525) else Color.White,
            shape = RoundedCornerShape(4.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {

            Text(
                    text = if (teclaSeleccionada.isEmpty()) "0" else teclaSeleccionada,
                    fontSize = 34.sp,
                    color = if (modoNocturno) Color.White else AzulOscuro
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Indicadores de la calculadora
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("DEG", fontSize = 11.sp, color = AzulOscuro)
            Text("F-E", fontSize = 11.sp, color = AzulOscuro)
            Text("↶", fontSize = 14.sp, color = AzulOscuro)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = { modoNocturno = !modoNocturno }
            ) {
                Text(
                    text = if (modoNocturno) "☀ Modo diurno" else "☾ Modo nocturno",
                    fontSize = 11.sp,
                    color = if (modoNocturno) Color.White else AzulOscuro
                )
            }
        }

        // Teclas de memoria
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("MC", "MR", "M+", "M−", "MS", "M∨").forEach {
                Text(
                    text = it,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

        // Categorías científicas

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box {
                TextButton(onClick = { menuTrigonometria = true }) {
                    Text("△ Trigonometría ▾", fontSize = 11.sp, color = AzulOscuro)
                }

                DropdownMenu(
                    expanded = menuTrigonometria,
                    onDismissRequest = { menuTrigonometria = false }
                ) {
                    listOf("sin", "cos", "tan", "sen⁻¹", "cos⁻¹", "tan⁻¹").forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                teclaSeleccionada = opcion
                                menuTrigonometria = false
                            }
                        )
                    }
                }
            }

            Box {
                TextButton(onClick = { menuFunciones = true }) {
                    Text("ƒ Función ▾", fontSize = 11.sp, color = AzulOscuro)
                }

                DropdownMenu(
                    expanded = menuFunciones,
                    onDismissRequest = { menuFunciones = false }
                ) {
                    listOf("sinh", "cosh", "tanh", "n!", "xʸ", "10ˣ", "log", "ln").forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                teclaSeleccionada = opcion
                                menuFunciones = false
                            }
                        )
                    }
                }
            }
        }
        TextButton(
            onClick = {
                mostrarAyuda = true
                android.util.Log.d("SMARTCALC", "Botón Ayuda pulsado")
            }
        ) {
            Text(
                text = "Ayuda",
                fontSize = 12.sp,
                color = AzulOscuro
            )
        }



        if (mostrarAyuda) {
            androidx.compose.material3.AlertDialog(
                onDismissRequest = {
                    mostrarAyuda = false
                },
                title = {
                    Text("AYUDA DE SMARTCALC")
                },
                text = {
                    Text("¡La ventana de ayuda funciona correctamente!")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            mostrarAyuda = false
                        }
                    ) {
                        Text("CERRAR")
                    }
                }
            )
        }





        // Teclado científico
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            filas.forEachIndexed { indice, fila ->
                androidx.compose.foundation.layout.Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    fila.forEach { tecla ->
                        val esOperador =
                            tecla in listOf("÷", "×", "−", "+", "=")

                        val esCientifica =
                            indice < 3 || tecla in listOf("xʸ", "10ˣ", "log", "ln")

                        Button(
                            onClick = {
                                if (tecla in listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")) {
                                    vista.playSoundEffect(SoundEffectConstants.CLICK)
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                                horizontal = 0.dp,
                                vertical = 0.dp
                            ),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(

                                containerColor = if (modoNocturno) {
                                    when {
                                        esOperador || esCientifica -> Color(0xFF37474F)
                                        else -> Color(0xFF252525)
                                    }
                                } else {
                                    when {
                                        esOperador || esCientifica -> Color(0xFFE5F0E6)
                                        else -> Color.White
                                    }
                                },

                                contentColor = if (modoNocturno) Color.White else AzulOscuro
                            )
                        ) {
                            Text(
                                text = tecla,
                                fontSize = if (tecla.length > 3) 10.sp else 12.sp,
                                textAlign = TextAlign.Center,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onSalir,
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AzulOscuro
            )
        ) {
            Text("SALIR", color = Color.White)
        }
    }
}



@Composable
fun PantallaDespedida(onVolver: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7FC)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "SmartCalc",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = AzulOscuro
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "¡Gracias por utilizar nuestra calculadora!",
            textAlign = TextAlign.Center,
            color = AzulOscuro
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Hasta pronto",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onVolver,
            modifier = Modifier.width(240.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AzulSmartCalc
            )
        ) {
            Text("VOLVER AL INICIO", color = Color.White)
        }
    }
}

