import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:home/component/textfield_component.dart';
import 'package:home/mqtt/mqtt_config.dart';

class SubscribeScreen extends StatefulWidget {
  const SubscribeScreen({Key? key}) : super(key: key);

  @override
  _SubscribeScreenState createState() => _SubscribeScreenState();
}

class _SubscribeScreenState extends State<SubscribeScreen> {
  String topic = '';

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Expanded(
        child: Column(
          children: [
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: textFieldCommon(
                "Topic",
                (text) {
                  topic = text;
                },
              ),
            ),
            OutlinedButton(
              onPressed: () async {
                final MqttConfig _mqttConfig = MqttConfig();
                await _mqttConfig.connectMqtt();
                _mqttConfig.subscribe(topic);
                _mqttConfig.listenUpdate();
              },
              child: const Text("Subscribe"),
            ),
          ],
        ),
      ),
    );
  }
}
