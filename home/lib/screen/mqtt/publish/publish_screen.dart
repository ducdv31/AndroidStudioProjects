/*
Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Expanded(
                  child: TextField(
                    onChanged: (text) {
                      strPub = text;
                    },
                    decoration: const InputDecoration(
                      label: Text("Publish"),
                    ),
                  ),
                ),
                OutlinedButton(
                  onPressed: () {
                    mqttConfig.publish("Duc", strPub);
                  },
                  child: const Text("Publish"),
                ),
              ],
            ),
            OutlinedButton(
              onPressed: () {
                mqttConfig.listenPublish();
              },
              child: const Text("Listen Publish"),
            ),
            OutlinedButton(
              onPressed: () {
                mqttConfig.listenUpdate();
              },
              child: const Text("Listen Update"),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Expanded(
                  child: TextField(
                    onChanged: (text) {
                      topicSub = text;
                    },
                    decoration: const InputDecoration(
                      label: Text("Subscribe"),
                    ),
                  ),
                ),
                OutlinedButton(
                  onPressed: () {
                    mqttConfig.subscribe(topicSub);
                  },
                  child: const Text("Subscribe"),
                ),
              ],
            ),
          ],
        )
 */

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:home/component/textfield_component.dart';
import 'package:home/mqtt/mqtt_config.dart';

class PublishScreen extends StatefulWidget {
  const PublishScreen({Key? key}) : super(key: key);

  @override
  _PublishScreenState createState() => _PublishScreenState();
}

class _PublishScreenState extends State<PublishScreen> {
  String topic = '';
  String message = '';

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Expanded(
          child: Align(
        alignment: Alignment.bottomCenter,
        child: Column(
          children: [
            Padding(
                padding: const EdgeInsets.all(8.0),
                child: textFieldCommon("Topic", (text) {
                  topic = text;
                })),
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: textFieldCommon("Message", (value) {
                message = value;
              }),
            ),
            OutlinedButton(
              onPressed: () async {
                final MqttConfig _mqttConfig = MqttConfig();
                await _mqttConfig.connectMqtt();
                _mqttConfig.publish(topic, message);
              },
              child: const Text("Publish"),
            ),
          ],
        ),
      )),
    );
  }
}
