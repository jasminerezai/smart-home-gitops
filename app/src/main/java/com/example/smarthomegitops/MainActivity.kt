package com.example.smarthomegitops

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthomegitops.presentation.SmartHomeViewModel
import com.example.smarthomegitops.ui.theme.SmartHomeGitOpsTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Button

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SmartHomeGitOpsTheme {

                val viewModel: SmartHomeViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsState()

                val backgroundColor =
                    if (uiState.isSecurityAlert) {
                        Color.Red
                    } else {
                        Color.Green
                    }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(backgroundColor)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    if (uiState.isSecurityAlert) {
                        Button(
                            onClick = {
                                uiState.pullRequestNumber?.let { pullNumber ->
                                    viewModel.forceMerge(pullNumber)
                                }
                            },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text("Force Merge")
                        }

                        Text(
                            text = "SECURITY ALERT",
                            color = Color.White
                        )

                        Text(
                            text = "Confidence: ${uiState.confidenceScore}%",
                            color = Color.White
                        )

                        Text(
                            text = uiState.attackText,
                            color = Color.White,
                            modifier = Modifier.padding(top = 16.dp)
                        )

                        Button(
                            onClick = {
                                uiState.pullRequestNumber?.let { pullNumber ->
                                    viewModel.forceReject(pullNumber)
                                }
                            },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Force Reject")
                        }

                    } else {

                        Text(
                            text = "NORMAL",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}