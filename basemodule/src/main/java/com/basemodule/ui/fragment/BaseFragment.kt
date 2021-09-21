package com.basemodule.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.basemodule.model.LoadingState
import com.basemodule.model.UiError
import com.basemodule.model.VMNotification
import com.basemodule.ui.LoadingViewOwner
import com.basemodule.utils.GenericsUtils
import com.basemodule.viewmodel.BaseViewModel

abstract class BaseFragment<ViewModel : BaseViewModel> : Fragment(), LoadingViewOwner {
    protected lateinit var viewModel: ViewModel
    private var loadingModes: Set<Int>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelClass: Class<ViewModel> = GenericsUtils.getViewModelClass(javaClass)
        viewModel = provideViewModel(viewModelClass)
    }

    inline fun<reified FragmentContainer> findFragmentContainer(): FragmentContainer? {
        var fragment = parentFragment
        while (fragment != null) {
            if (fragment is FragmentContainer)
                return fragment
            fragment = fragment.parentFragment
        }

        return if (activity is FragmentContainer) activity as FragmentContainer
        else null
    }

    //this function should be implemented in project base fragment.
    abstract fun provideViewModel(viewModelClass: Class<ViewModel>): ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.ldLoadingState.observe(this.viewLifecycleOwner, { onLoadingStateUpdated(it) })
        viewModel.ldActions.observe(this.viewLifecycleOwner, { onActionsUpdated(it) })
        viewModel.ldUiError.observe(this.viewLifecycleOwner, { onReceiveError(it) })
    }

    private fun onReceiveError(uiError: UiError?) {
        showUiError(uiError)
        viewModel.clearUiError()
    }

    private fun onActionsUpdated(vmNotifications: MutableSet<VMNotification>) {
        for (itr in vmNotifications) doAction(itr)
        viewModel.clearActions()
    }

    private fun onLoadingStateUpdated(loadingState: LoadingState) {
        val newLoadingModes: Set<Int> = loadingState.getLoadingModes()
        for (loadingMode in newLoadingModes) showLoading(loadingMode)

        if (loadingModes != null) {

            for (loadingMode in loadingModes!!) if (!newLoadingModes.contains(loadingMode)) hideLoading(
                loadingMode
            )
        }

        loadingModes = newLoadingModes
    }

    override fun showLoading(loadingMode: Int) {

    }

    override fun hideLoading(loadingMode: Int) {

    }

    protected open fun showUiError(uiError: UiError?) {

    }

    protected open fun doAction(vmNotification: VMNotification) {

    }
}