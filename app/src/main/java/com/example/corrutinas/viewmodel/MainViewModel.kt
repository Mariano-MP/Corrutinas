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

    private fun contadorBloqueante(tiempo: Int, repeticiones: Int) {
        for (r in 1..repeticiones) {
            for (i in 1..tiempo) {
                Thread.sleep(1000) // Congela el hilo principal
                countTime = i
            }
        }
    }

    fun iniciarContadorSecuencial(n: Int) {
        resultState = "Ejecutando bloqueante con $n repeticiones..."
        countTime = 0
        contadorBloqueante(5, n)
        resultState = "Fin bloqueante con $n repeticiones"
    }


    private suspend fun contadorConcurrente(tiempo: Int, repeticiones: Int) {
        for(r in 1..repeticiones) {
            for (i in 1..tiempo) {
                delay(1000)
                countTime = i
            }
        }
    }

    fun iniciarContadorConcurrente(n: Int) {
        resultState = "Ejecutando concurrente con $n repeticiones..."
        oneCount = true
        countTime = 0
        contadorJob = viewModelScope.launch {
            contadorConcurrente(5, n)
            resultState = "Fin concurrente con $n repeticiones"
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
