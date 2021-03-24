import 'package:flutter/material.dart';

import 'package:firebase_core/firebase_core.dart';

// import 'package:cloud_firestore/cloud_firestore.dart';
void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return MaterialApp(
        debugShowCheckedModeBanner: false,
        title: "MPU6050",
        theme: ThemeData(primarySwatch: Colors.red),
        home: Scaffold(
          appBar: AppBar(
            title: Center(child: Text("MPU-6050")),
          ),
          body: _MyHome(),
        ));
  }
}

class _MyHome extends StatefulWidget {
  _MyHome({Key key}) : super(key: key);
  _MyHomeState createState() => _MyHomeState();
}

class _MyHomeState extends State<_MyHome> {
  final double fontTableSize = 30;
  final double iconSize = 40;
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Center(
      child: Column(
        children: [
          Container(
            margin: EdgeInsets.all(10),
            child: Table(
              border: TableBorder.all(),
              children: [
                TableRow(children: [
                  Column(
                    children: [
                      Text(
                        "AcX",
                        style: TextStyle(fontSize: fontTableSize),
                      )
                    ],
                  ),
                  Column(
                    children: [
                      Text(
                        "AcY",
                        style: TextStyle(fontSize: fontTableSize),
                      )
                    ],
                  ),
                  Column(
                    children: [
                      Text(
                        "AcZ",
                        style: TextStyle(fontSize: fontTableSize),
                      )
                    ],
                  ),
                ]),
                TableRow(children: [
                  Icon(
                    Icons.cake,
                    size: iconSize,
                  ),
                  Icon(
                    Icons.voice_chat,
                    size: iconSize,
                  ),
                  Icon(
                    Icons.add_location,
                    size: iconSize,
                  ),
                ]),
                TableRow(children: [
                  Column(
                    children: [
                      Text(
                        "GyX",
                        style: TextStyle(fontSize: fontTableSize),
                      ),
                    ],
                  ),
                  Column(
                    children: [
                      Text(
                        "GyY",
                        style: TextStyle(fontSize: fontTableSize),
                      ),
                    ],
                  ),
                  Column(
                    children: [
                      Text(
                        "GyZ",
                        style: TextStyle(fontSize: fontTableSize),
                      ),
                    ],
                  ),
                ]),
                TableRow(children: [
                  Icon(
                    Icons.add,
                    size: iconSize,
                  ),
                  Icon(
                    Icons.add_moderator,
                    size: iconSize,
                  ),
                  Icon(
                    Icons.ten_k,
                    size: iconSize,
                  ),
                ])
              ],
            ),
          )
        ],
      ),
    );
  }
}
