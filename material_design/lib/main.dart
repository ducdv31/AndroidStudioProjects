import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class AppHome extends StatelessWidget {
  AppHome({Key key}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
            icon: Icon(Icons.menu),
            tooltip: "Navigation menu",
            onPressed: null),
        title: Center(child: Text("Hello D.Duc")),
        actions: [
          IconButton(
              icon: Icon(Icons.search), tooltip: "Search", onPressed: null)
        ],
      ),
      body: Center(
        child: Text("Hello World"),
      ),
      floatingActionButton: FloatingActionButton(
        tooltip: "Add",
        child: Icon(Icons.add),
        onPressed: null,
      ),
    );
  }
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: "Material App",
      theme: ThemeData(primarySwatch: Colors.red),
      home: AppHome(),
    );
  }
}
