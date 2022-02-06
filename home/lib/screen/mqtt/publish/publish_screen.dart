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

class PublishScreen extends StatefulWidget {
  const PublishScreen({Key? key}) : super(key: key);

  @override
  _PublishScreenState createState() => _PublishScreenState();
}

class _PublishScreenState extends State<PublishScreen> {
  @override
  Widget build(BuildContext context) {
    return const Center(
      child: Text("Publish"),
    );
  }
}
