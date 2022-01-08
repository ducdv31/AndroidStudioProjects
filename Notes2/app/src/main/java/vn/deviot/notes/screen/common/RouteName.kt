package vn.deviot.notes.screen.common

import androidx.annotation.StringRes
import vn.deviot.notes.R

enum class Route(@StringRes val title: Int) {
    Login(R.string.login),
    Notes(R.string.notes)
}