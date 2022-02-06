import 'package:flutter/animation.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:home/screen/mqtt/publish/publish_screen.dart';
import 'package:home/screen/mqtt/subscribe/subscribe_screen.dart';

import 'mqtt/mqtt_config.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.red,
      ),
      home: const MyHomePage(title: "MQTT"),
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
  final PageController _pageController = PageController();

  int currentBottomIndex = 0;

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
        centerTitle: true,
      ),
      body: PageView(
        controller: _pageController,
        children: const [
          PublishScreen(),
          SubscribeScreen(),
        ],
        onPageChanged: (pos) {
          setState(() {
            currentBottomIndex = pos;
          });
        },
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
          _pageController.animateToPage(
            pos,
            duration: const Duration(milliseconds: 500),
            curve: Curves.ease,
          );
          setState(() {
            currentBottomIndex = pos;
          });
        },
        currentIndex: currentBottomIndex,
      ),
    );
  }
}
