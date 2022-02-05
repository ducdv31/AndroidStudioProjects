import 'package:flutter/material.dart';

import 'mqtt/mqtt_config.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);
  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  String strPub = '';
  String topicSub = '';
  late MqttConfig mqttConfig;

  int currentIndex = 0;

  @override
  void initState() {
    super.initState();
    mqttConfig = MqttConfig();
    mqttConfig.connectMqtt();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
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
        ),
      ),
      bottomNavigationBar: BottomNavigationBar(
        items: const [
          BottomNavigationBarItem(
            icon: Icon(Icons.publish),
            label: "Publish",
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.subscriptions_outlined),
            label: "Subscribe",
          ),
        ],
        onTap: (pos) {
          setState(() {
            currentIndex = pos;
          });
        },
        currentIndex: currentIndex,
      ),
    );
  }
}
