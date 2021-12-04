package vn.deviot.mymqtt.net.mqtt.action

enum class MqttActionKey(val action: String) {
    ActionConnectLost(ACTION_CONNECT_LOST),
    ActionReceivedMessage(ACTION_RECEIVED_MESSAGE),
    ActionDeliveryComplete(ACTION_DELIVERY_COMPLETE),
    ActionError(ACTION_ERROR),
    ActionNoConnect(ACTION_NO_CONNECT),
    ActionSubscribeSuccess(ACTION_SUBSCRIBE_SUCCESS),
    ActionUnSubscribeSuccess(ACTION_UN_SUBSCRIBE_SUCCESS),
    ActionDisconnected(ACTION_DISCONNECTED)
}

private const val ACTION_CONNECT_LOST = "vn.connect.mqtt.ACTION_LOST"
private const val ACTION_RECEIVED_MESSAGE = "vn.connect.mqtt.ACTION_RECEIVED_MESSAGE"
private const val ACTION_DELIVERY_COMPLETE = "vn.connect.mqtt.ACTION_DELIVERY_COMPLETE"
private const val ACTION_ERROR = "vn.connect.mqtt.ACTION_ERROR"
private const val ACTION_NO_CONNECT = "vn.connect.mqtt.ACTION_NO_CONNECT"
private const val ACTION_SUBSCRIBE_SUCCESS = "vn.connect.mqtt.ACTION_SUBSCRIBE_SUCCESS"
private const val ACTION_UN_SUBSCRIBE_SUCCESS = "vn.connect.mqtt.ACTION_UN_SUBSCRIBE_SUCCESS"
private const val ACTION_DISCONNECTED = "vn.connect.mqtt.ACTION_DISCONNECTED"