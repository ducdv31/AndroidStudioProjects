package vn.dv.myhome.service.mqtt

enum class MqttActionKey {
    ConnectSuccess,
    ConnectLost,
    ArrivedMessage,
    DeliveryCompleted,
    SubscribeSuccess,
    UnSubscribeSuccess,
    Error,
    NoConnected,
    Disconnected
}