import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Welcome to Flutter",
      theme: ThemeData(primarySwatch: Colors.red),
      home: AppHome(),
    );
  }
}

class AppHome extends StatelessWidget {
  String word;
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return ranWord();
  }
}

class ranWord extends StatefulWidget {
  @override
  _ranWordState createState() => _ranWordState();
}

class _ranWordState extends State<ranWord> {
  String eng_word = "Hello Word";
  void _setText() {
    setState(() {
      eng_word = WordPair.random().asPascalCase;
    });
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          icon: Icon(Icons.menu),
          onPressed: null,
          tooltip: "Menu",
        ),
        title: Center(child: Text("Hi Man")),
        actions: [
          IconButton(
            icon: Icon(Icons.search),
            onPressed: null,
            tooltip: "Search",
          )
        ],
      ),
      body: Center(
        child: Text(eng_word),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _setText,
        tooltip: "Change word",
        child: Icon(Icons.refresh),
      ),
    );
  }
}
