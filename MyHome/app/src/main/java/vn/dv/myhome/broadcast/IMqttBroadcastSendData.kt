package vn.dv.myhome.broadcast

interface IMqttBroadcastSendData {
    fun onConnectSuccess(asyncActionToken: String?)

    fun onConnectLost(message: String?)

    fun onArrivedMessage(topic: String?, message: String?)

    fun onDeliveryCompleted(token: String?)

    fun onSubscribeSuccess()

    fun onUnSubscribeSuccess()

    fun onError(message: String?)

    fun onNoConnected()

    fun onDisconnected()
}