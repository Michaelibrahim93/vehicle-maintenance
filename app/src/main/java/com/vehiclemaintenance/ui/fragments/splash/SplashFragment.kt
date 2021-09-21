package com.vehiclemaintenance.ui.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.vehiclemaintenance.databinding.FragmentComposeBinding
import com.vehiclemaintenance.ui.fragments.base.VehicleBaseFragment
import com.vehiclemaintenance.ui.theme.VehicleMaintenanceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : VehicleBaseFragment<SplashViewModel>() {

    private lateinit var binding: FragmentComposeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentComposeBinding.inflate(inflater)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                VehicleMaintenanceTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        SplashView()
                    }
                }
            }
        }
        return binding.root
    }

    companion object {
        internal const val ACTION_TO_HOME = "action.to.home"
    }
}