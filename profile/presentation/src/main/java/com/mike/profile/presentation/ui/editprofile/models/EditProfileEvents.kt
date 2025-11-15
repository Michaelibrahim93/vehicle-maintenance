package com.mike.profile.presentation.ui.editprofile.models

sealed class EditProfileEvents {
    data class NameChanged(val name: String) : EditProfileEvents()
    data class BirthDateChanged(val birthDate: String) : EditProfileEvents()
    object SaveProfile : EditProfileEvents()
}

sealed class UiActions {
    object ProfileSaved : UiActions()
}