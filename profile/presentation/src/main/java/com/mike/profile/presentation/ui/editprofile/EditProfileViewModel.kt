package com.mike.profile.presentation.ui.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.core.domain.utils.IoDispatcher
import com.mike.core.domain.utils.validator.ValidationInput
import com.mike.core.domain.utils.validator.Validator
import com.mike.core.domain.utils.validator.rules.NotBlankRule
import com.mike.core.domain.utils.validator.rules.NotNullRule
import com.mike.core.presentation.viewmodels.InitLoaderImpl
import com.mike.core.presentation.viewmodels.InitialLoader
import com.mike.profile.domain.repos.ProfileRepository
import com.mike.profile.presentation.ui.editprofile.models.EditProfileUiState
import com.mike.profile.presentation.ui.editprofile.models.setBirthDate
import com.mike.profile.presentation.ui.editprofile.models.setName
import com.mike.resources.R
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.Date

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    private val profileRepository: ProfileRepository,
) : ViewModel(), InitialLoader by InitLoaderImpl() {
    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.onStart {
        loadInitValues()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = EditProfileUiState()
    )

    private val validator: Validator = initValidator()

    private fun initValidator(): Validator {
        return Validator.Builder()
            .addRule(EditProfileUiState.KEY_NAME, NotBlankRule(), R.string.required_field)
            .addRule(EditProfileUiState.KEY_BIRTH_DATE, NotNullRule(), R.string.required_field)
            .build()
    }

    private fun loadInitValues() = loadInitValues {
        Timber.d("loadInitValues")
        viewModelScope.launch {
            val profile = withContext(dispatcher) {
                profileRepository.loadProfile()
            }
            profile?.let {
                updateName(it.name)
                updateBirthDate(it.birthDate)
            }
        }
    }

    fun updateName(name: String) {
        _uiState.value = _uiState.value.setName(name)
    }

    fun updateBirthDate(birthDate: Date) {
        _uiState.value = _uiState.value.setBirthDate(birthDate)
    }

    fun saveProfile() = viewModelScope.launch {
        val profile = _uiState.value.toProfile()

        withContext(dispatcher) {
            profileRepository.updateProfile()
        }
    }
}