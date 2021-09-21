package com.vehiclemaintenance.ui.fragments.base

import androidx.lifecycle.ViewModelProvider
import com.basemodule.ui.fragment.BaseFragment
import com.basemodule.viewmodel.BaseViewModel

open class VehicleBaseFragment<ViewModel: BaseViewModel> : BaseFragment<ViewModel>() {
    override fun provideViewModel(viewModelClass: Class<ViewModel>): ViewModel {
        return ViewModelProvider(this).get(viewModelClass)
    }
}