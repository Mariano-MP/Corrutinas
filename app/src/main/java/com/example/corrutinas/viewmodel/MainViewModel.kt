package com.example.corrutinas.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var resultState by mutableStateOf("")
    var countTime by mutableStateOf(0)
        private set
    var oneCount by mutableStateOf(false)

    private var contadorJob: Job? = null

    // Algoritmo prolongado secuencial (bloquea la UI)
    private fun contadorBloqueante(tiempo: Int) {
        for (i in 1..tiempo) {
            Thread.sleep(1000) // Congela el hilo principal
            countTime = i
        }
    }

    fun iniciarContadorSecuencial() {
        resultState = "Ejecutando bloqueante..."
        countTime = 0
        contadorBloqueante(5)
        contadorBloqueante(5)
        resultState = "Fin de contadores (bloqueante)"
    }



    private suspend fun contadorSimple(tiempo: Int) {
        for (i in 1..tiempo) {
            delay(1000)
            countTime = i
        }
    }
    fun contadorDoble() {
        resultState = "Ejecutando concurrente..."
        oneCount = true
        countTime = 0
        contadorJob = viewModelScope.launch {
            contadorSimple(5)
            countTime = 0
            contadorSimple(5)
            resultState = "Fin de contadores (concurrente)"
            oneCount = false
        }
    }



    fun cancelarContador() {
        viewModelScope.coroutineContext.cancelChildren()
        countTime = 0
        oneCount = false
        resultState = "Contador cancelado"
    }
}
