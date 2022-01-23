package vn.dv.myhome.view.activity.main.adapter

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import vn.dv.myhome.R
import vn.dv.myhome.view.activity.main.fragment.PublishMqttFragment
import vn.dv.myhome.view.activity.main.fragment.SubscribeMqttFragment

enum class EBottomTabHome(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    @IdRes val bottomId: Int,
    val fragment: Fragment
) {
    Publish(
        R.string.publish,
        R.drawable.ic_baseline_public_24,
        R.id.publish_tab,
        PublishMqttFragment()
    ),
    Subscribe(
        R.string.subscribe,
        R.drawable.ic_baseline_call_received_24,
        R.id.subscribe_tab,
        SubscribeMqttFragment()
    )
}