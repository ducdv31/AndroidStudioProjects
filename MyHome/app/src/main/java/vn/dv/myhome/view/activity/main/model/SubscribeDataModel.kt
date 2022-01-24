package vn.dv.myhome.view.activity.main.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import vn.dv.myhome.utils.DateTimeUtils

@Parcelize
data class SubscribeDataModel(
    var topic: String? = null,
    var message: String? = null,
    var time: String = DateTimeUtils.getCurrentTime()
) : Parcelable
