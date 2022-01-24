package vn.dv.myhome.utils.bus

import vn.dv.myhome.view.activity.main.model.SubscribeDataModel

object EventBus {
    object SubscribeDataEvent {
        private var subscribeDataModel: SubscribeDataModel? = null

        fun setData(subscribeDataModel: SubscribeDataModel) {
            this.subscribeDataModel = subscribeDataModel
        }

        fun getData() = subscribeDataModel
    }
}