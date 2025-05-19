package com.example.tp2a

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.tp2a.ui.theme.*
import kotlin.random.Random
import com.example.tp1.ui.theme.Nord0
import com.example.tp1.ui.theme.Nord1
import com.example.tp1.ui.theme.Nord2
import com.example.tp1.ui.theme.Nord3
import com.example.tp1.ui.theme.Nord4
import com.example.tp1.ui.theme.White

@Composable
fun GuessGameScreen() {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("game_prefs", Context.MODE_PRIVATE)

    var currentScore by remember { mutableStateOf(0) }
    var maxScore by remember { mutableStateOf(getSavedMaxScore(sharedPref)) }
    var input by remember { mutableStateOf("") }
    var errorCount by remember { mutableStateOf(0) }
    var targetNumber by remember { mutableStateOf(Random.nextInt(1, 6)) }

    fun resetGame() {
        currentScore = 0
        errorCount = 0
        targetNumber = Random.nextInt(1, 6)
    }

    fun handleGuess() {
        val guess = input.toIntOrNull()
        if (guess == null || guess !in 1..5) {
            showToast(context, "Por favor ingresa un número entre 1 y 5")
            return
        }

        if (guess == targetNumber) {
            currentScore += 10
            if (currentScore > maxScore) {
                maxScore = currentScore
                saveMaxScore(sharedPref, maxScore)
            }
            errorCount = 0
            showToast(context, "¡Correcto!")
        } else {
            errorCount += 1
            showToast(context, "Incorrecto. Intentos fallidos: $errorCount/5")
            if (errorCount == 5) {
                showToast(context, "¡Perdiste! Puntaje reiniciado.")
                resetGame()
                return
            }
        }

        input = ""
        targetNumber = Random.nextInt(1, 6)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Nord0
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .padding(32.dp)
                    .background(color = Nord1, shape = MaterialTheme.shapes.medium)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Adivina el número (1 al 5)", color = White, style = MaterialTheme.typography.headlineSmall)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    label = { Text("Tu adivinanza", color = Nord3) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = White,
                        unfocusedTextColor = White,
                        focusedBorderColor = Nord3,
                        unfocusedBorderColor = Nord3,
                        cursorColor = White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { handleGuess() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Nord2,
                        contentColor = White
                    )
                ) {
                    Text("Adivinar")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("Puntaje actual: $currentScore", color = White)
                Text("Mejor puntaje: $maxScore", color = Nord4)
            }
        }
    }
}

private fun getSavedMaxScore(sharedPref: SharedPreferences): Int {
    return sharedPref.getInt("max_score", 0)
}

private fun saveMaxScore(sharedPref: SharedPreferences, score: Int) {
    sharedPref.edit().putInt("max_score", score).apply()
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
