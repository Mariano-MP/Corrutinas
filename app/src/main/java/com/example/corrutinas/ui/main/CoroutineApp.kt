package com.example.corrutinas.ui.main

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.corrutinas.ui.theme.CorrutinasTheme
import com.example.corrutinas.viewmodel.MainViewModel

val N = 3

@Composable
fun CoroutineApp(mainViewModel: MainViewModel, modifier : Modifier = Modifier){
    var changeColor by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(onClick = {
            changeColor = !changeColor
        },
            colors = ButtonDefaults.buttonColors(
                if(changeColor) Color.Blue else Color.Green
            )
        ){
            Text(text = "Cambio de color")
        }
        Button(onClick = {
            mainViewModel.iniciarContadorSecuencial(N)
        } ,
            enabled = !mainViewModel.oneCount,
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text(text = "Contadores Secuenciales")
        }

        Spacer(modifier = modifier.height(30.dp))
        Text (text = "${mainViewModel.countTime} [s]")
        Text(text = mainViewModel.resultState)
        Spacer(modifier = modifier.height(30.dp))
        Button(onClick = {
            if(mainViewModel.oneCount == false){
                mainViewModel.iniciarContadorConcurrente(N)
            }
        }) {
            Text(text = "Empezar Contadores")
        }
        Button(onClick = {
            mainViewModel.cancelarContador()
        } ,
            enabled = mainViewModel.oneCount,
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text(text = "Interrumpir Contadores")
        }

    }
}

