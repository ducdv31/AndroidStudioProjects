import 'package:english_words/english_words.dart';
import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MyContainer();
  }
}

class MyMaterial extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    final workpair = WordPair.random();
    return MaterialApp(
      title: "My App",
      home: Scaffold(
        appBar: AppBar(
          title: Text("Hello"),
        ),
        body: Center(
          child: Text(workpair.asString),
        ),
      ),
    );
  }
}

class MyContainer extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Container(
      decoration: BoxDecoration(
        border: Border(
            left: BorderSide(color: Colors.red, width: 5),
            right: BorderSide(color: Colors.green, width: 5),
            bottom: BorderSide(color: Colors.yellow, width: 5),
            top: BorderSide(color: Colors.blue, width: 5)),
      ),
      child: Container(
        padding: const EdgeInsets.symmetric(vertical: 2, horizontal: 20),
        decoration: BoxDecoration(
          border: Border(
              left: BorderSide(color: Colors.green, width: 5),
              right: BorderSide(color: Colors.red, width: 5),
              bottom: BorderSide(color: Colors.blueAccent, width: 5),
              top: BorderSide(color: Colors.yellow, width: 5)),
          color: Colors.grey
        ),
        child: Text("Ok", textAlign: TextAlign.center, style: TextStyle(),),
      ),
    );
  }
}
