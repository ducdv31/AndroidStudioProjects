import 'package:flutter/material.dart';

void main() {
  runApp(SampleApp());
}

class SampleApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Hello Đặng Đức',
      theme: ThemeData(
        primarySwatch: Colors.red,
        shadowColor: Colors.amber
      ),
      home: SampleAppPage(),
    );
  }
}

class SampleAppPage extends StatefulWidget {
  SampleAppPage({Key key}) : super(key: key);

  @override
  _SampleAppPageState createState() => _SampleAppPageState();
}

class _SampleAppPageState extends State<SampleAppPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Hello Đặng Đức"),
      ),
        body: ListView(children: _listClick(),),
    );
  }

  List<Widget> _getListData() {
    List<Widget> widgets = [];
    for (int i = 0; i < 100; i++) {
      widgets.add(Padding(
        padding: EdgeInsets.all(10.0),
        child: Text("Row $i"),
      ));
    }
    return widgets;
  }

  List<Widget> _myList() {
    List<Widget> data = [];
    for (int i = 100; i <= 200; i++) {
      data.add(Padding(
        padding: EdgeInsets.all(8),
        child: Text("Number $i"),
      ));
    }
    return data;
  }
  List<Widget> _listClick(){
    List <Widget> _List=[];
    for (int i = 3000; i <= 4000; i+=100){
      _List.add(GestureDetector(
        child: Padding(
          padding: EdgeInsets.all(15),
          child:Center(
            child: Text("Hi man $i",
            style: TextStyle(color: Colors.red, fontSize: 25,fontWeight: FontWeight.bold),
            ),
            heightFactor: 1,
          )
        ),
        onTap: (){
          print("You clicked to Number $i");
        },
      ));
    }
    return _List;
  }
}
