package com.example.mymqtt.mqttconfig;

import android.content.Context;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class MyMqtt {
    private final Context context;
    MqttAndroidClient client;
    boolean status_connected = false;
    boolean status_subscribe = false;

    public interface MyMqttListener {
        void ServerConnected(boolean status);

        void NotifyConnected(boolean notify);  // Requires connect

        void OnSubscribe(boolean subscribe);

        void MessageArrived(String topic, MqttMessage mqttMessage);

        void DeliveryComplete(IMqttDeliveryToken iMqttDeliveryToken);

        void OnUnsubscribe(boolean unsubscribe);

        void OnDisconnect(boolean disconnected);
    }

    private final MyMqttListener myMqttListener;

    public MyMqtt(Context context, MyMqttListener listener) {
        this.context = context;
        myMqttListener = listener;
    }

    public void connectServerUri(String url, int port) {
        String clientId = MqttClient.generateClientId();
        String uri = url + ":" + port;
        /* Connect MQTT version 3.1 */
        MqttConnectOptions options = new MqttConnectOptions();
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
        client =
                new MqttAndroidClient(context,
                        uri,
                        clientId);
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                myMqttListener.ServerConnected(false);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                myMqttListener.MessageArrived(topic, message);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                myMqttListener.DeliveryComplete(token);
            }
        });
        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    myMqttListener.ServerConnected(true);
                    status_connected = true;
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    myMqttListener.ServerConnected(false);
                    status_connected = false;

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    public void connectServerUriSecure(String url, int port, String username, String pass) {
        String clientId = MqttClient.generateClientId();

        String uri = url + ":" + port;
        /* Connect MQTT version 3.1 */
        MqttConnectOptions options = new MqttConnectOptions();
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
        options.setUserName(username);
        options.setPassword(pass.toCharArray());
        client =
                new MqttAndroidClient(context,
                        uri,
                        clientId);
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                myMqttListener.ServerConnected(false);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                myMqttListener.MessageArrived(topic, message);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                myMqttListener.DeliveryComplete(token);
            }
        });
        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    myMqttListener.ServerConnected(true);
                    status_connected = true;
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    myMqttListener.ServerConnected(false);
                    status_connected = false;

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    public void publish(String topic, String context, boolean remain) {
        if (status_connected) {
            byte[] encodedPayload = new byte[0];
            try {
                encodedPayload = context.getBytes("UTF-8");
                MqttMessage message = new MqttMessage(encodedPayload);
                message.setRetained(remain);
                client.publish(topic, message);
            } catch (UnsupportedEncodingException | MqttException e) {
                e.printStackTrace();
            }
        } else {
            myMqttListener.NotifyConnected(false);
        }
    }

    public void subscribe(String Topic, int qos) {
        if (status_connected) {
            /* Subscribe */
            try {
                IMqttToken subToken = client.subscribe(Topic, qos);
                subToken.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        // The message was published
                        status_subscribe = true;
                        myMqttListener.OnSubscribe(true);
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken,
                                          Throwable exception) {
                        // The subscription could not be performed, maybe the user was not
                        // authorized to subscribe on the specified topic e.g. using wildcards
                        status_subscribe = false;
                        myMqttListener.OnSubscribe(false);
                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        } else {
            myMqttListener.NotifyConnected(false);
        }
    }

    public void unSubscribe(String topic) {
        if (status_subscribe) {
            try {
                IMqttToken unsubToken = client.unsubscribe(topic);
                unsubToken.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        // The subscription could successfully be removed from the client
                        myMqttListener.OnUnsubscribe(true);
                        status_subscribe = false;
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken,
                                          Throwable exception) {
                        // some error occurred, this is very unlikely as even if the client
                        // did not had a subscription to the topic the unsubscribe action
                        // will be successfully
                        myMqttListener.OnUnsubscribe(false);
                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        } else {
            myMqttListener.OnSubscribe(false);
        }
    }

    public void disconnect() {
        if (status_connected) {
            try {
                IMqttToken disconnectToken = client.disconnect();
                disconnectToken.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        // we are now successfully disconnected
                        myMqttListener.OnDisconnect(true);
                        status_connected = false;
                        status_subscribe = false;
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken,
                                          Throwable exception) {
                        // something went wrong, but probably we are disconnected anyway
                        myMqttListener.OnDisconnect(false);
                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        } else {
            myMqttListener.ServerConnected(false);
        }
    }

}
