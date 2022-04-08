package com.bytezeroone.countdowntimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bytezeroone.countdowntimer.presentation.CountDownEvent
import com.bytezeroone.countdowntimer.presentation.CountDownTimerViewModel
import com.bytezeroone.countdowntimer.ui.theme.CountDownTimerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountDownTimerTheme {
                CountDownTimerScreen()
            }
        }
    }
}

@Composable
fun CountDownTimerScreen(
    viewModel: CountDownTimerViewModel = hiltViewModel()
) {

    var buttonState = true

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Counter",
                modifier = Modifier.align(Alignment.TopCenter)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = viewModel.count.value.toString(),
                    modifier = Modifier.padding(16.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Button(
                        enabled = buttonState,
                        onClick = {
                            buttonState = false
                            viewModel.onEvent(CountDownEvent.OnStartButtonClick)
                        },
                        modifier = Modifier.width(108.dp)
                    )
                    {
                        Text(text = "Start")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            buttonState = true
                            viewModel.onEvent(CountDownEvent.OnStopButtonClick)
                        },
                        modifier = Modifier.width(108.dp)
                    ) {
                        Text(text = "Stop")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            buttonState = false
                            viewModel.onEvent(CountDownEvent.OnRestartButtonClick)
                        },
                        modifier = Modifier.width(108.dp)
                    ) {
                        Text(text = "Restart")
                    }
                }
            }
        }
    }
}
