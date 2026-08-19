package com.ehealthinformatics.prognocare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.ehealthinformatics.prognocare.designsystem.theme.AppearanceMode
import com.ehealthinformatics.prognocare.designsystem.theme.PrognoCareTheme
import com.ehealthinformatics.prognocare.designsystem.theme.ThemeViewModel
import com.ehealthinformatics.prognocare.navigation.PrognoCareNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val themeSettings by themeViewModel.themeSettings.collectAsState()
            val isSystemDark = isSystemInDarkTheme()
            
            PrognoCareTheme(
                themeSettings = themeSettings,
                isSystemDark = isSystemDark
            ) {
                PrognoCareNavGraph()
            }
        }
    }
}
