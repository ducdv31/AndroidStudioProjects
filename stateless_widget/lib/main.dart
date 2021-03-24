import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';
main()=>runApp(MyApp());
class MyApp extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return MaterialApp(
      title: "DD App",
      theme: ThemeData(primarySwatch: Colors.red),
      home: AppHome(),
    );
  }

}

class AppHome extends StatefulWidget{
_AppHomeState  createState()=> _AppHomeState();
}
class _AppHomeState extends State<AppHome>{
  String School = "Dang Duc Master";
  void _Action(){
    setState(() {
      School="BKHN";
    });

}
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      appBar: AppBar( title:Text("Hello Dang Duc")),
      body: Center(
        child: Text(School),
      ),
      
      floatingActionButton: FloatingActionButton(
        onPressed: _Action,
        tooltip: "Information",
        child: Icon(Icons.info),
      ),
    );
  }
}
