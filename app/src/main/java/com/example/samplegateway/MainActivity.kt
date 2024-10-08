package com.example.samplegateway

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.easebuzz.payment.kit.PWECouponsActivity
import com.example.samplegateway.ui.theme.SampleGatewayTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SampleGatewayTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MyActivityResultScreen()
                }
            }
        }
    }
}


@Composable
fun MyActivityResultScreen() {
    val context = LocalContext.current

    // Create a launcher for the activity result
    val pweActivityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        val data = result.data
        if (data != null) {
            val resultString = data.getStringExtra("result")
            val paymentResponse = data.getStringExtra("payment_response")
            try {
                // Handle the result and payment response here
                println("Result: $resultString, Payment Response: $paymentResponse")
            } catch (e: Exception) {
                // Handle exceptions here
                e.printStackTrace()
            }
        }
    }

    // Button to trigger the activity result launcher
    Button(onClick = {
        // Define the Intent to start PWECouponsActivity
        val intentProceed = Intent(context, PWECouponsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            putExtra("access_key", "Access key generated by the Initiate Payment API")
            putExtra("pay_mode", "test") // or "production"
        }

        // Launch the activity using the launcher
        pweActivityResultLauncher.launch(intentProceed)
    }) {
        Text("Start PWECouponsActivity")
    }
}