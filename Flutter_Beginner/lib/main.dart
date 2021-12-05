import 'package:flutter/material.dart';

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
        primarySwatch: Colors.red,
      ),
      home: const MyHomePage('Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage(String title, {Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _StateHome();
  }
}

class _StateHome extends State<MyHomePage> with WidgetsBindingObserver {
  String txt = "";
  final TextEditingController _controller = TextEditingController();
  final GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey();
  List<String> listData = List.empty(growable: true);

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    WidgetsBinding.instance?.addObserver(this);
  }

  @override
  void dispose() {
    // TODO: implement dispose
    super.dispose();
    WidgetsBinding.instance?.removeObserver(this);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        key: _scaffoldKey,
        appBar: AppBar(
          title: const Text("My app"),
          centerTitle: true,
        ),
        body: SafeArea(
          minimum: const EdgeInsets.symmetric(horizontal: 5),
          child: Column(
            children: <Widget>[
              Container(
                padding:
                    const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
                child: TextField(
                  controller: _controller,
                  onChanged: (text) {
                    setState(() {
                      txt = text;
                    });
                  },
                  onSubmitted: (text) {
                    setState(() {
                      _controller.clear();
                      txt = "";
                    });
                    ScaffoldMessenger.of(context).showSnackBar(SnackBar(
                      content: Text("Search $text"),
                      duration: const Duration(seconds: 2),
                    ));
                  },
                  keyboardType: TextInputType.text,
                  textInputAction: TextInputAction.search,
                  decoration: InputDecoration(
                      border: const OutlineInputBorder(
                          borderRadius: BorderRadius.all(Radius.circular(10))),
                      labelText: "Input here",
                      prefixIcon: const Icon(
                        Icons.search,
                        color: Colors.cyan,
                      ),
                      suffixIcon: IconButton(
                        icon: const Icon(Icons.clear),
                        color: Colors.yellow,
                        onPressed: () {
                          setState(() {
                            _controller.clear();
                            txt = "";
                          });
                        },
                      )),
                ),
              ),
              const Text("Hello"),
              Text(txt),
            ],
          ),
        ));
  }
}
