import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:recipe_flutter/net/api/recipe/api_client_recipe.dart';
import 'package:recipe_flutter/net/api/recipe/model/recipe_model.dart';

import 'constant/constant.dart';

void main() {
  runApp(MyApp());
}

/*void main(List<String> args) {
  final dio = Dio(); // Provide a dio instance
  dio.options.contentType =
      "application/json"; // config your dio headers globally
  dio.options.headers["Authorization"] =
      "Token 9c8b06d329136da358c2d00e76946b0111ce2c48";
  ApiClientRecipe(dio).getListRecipe(1, "").then((it) {
    print(it.results?.elementAt(1).title);
  });
}*/

class MyApp extends StatefulWidget {
  MyApp({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _StateMyApp();
}

class _StateMyApp extends State<MyApp> {
  final dio = Dio();
  List<Results>? listRecipe = List.empty(growable: true);
  var page = 1;
  var query = EMPTY;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    /* get data recipe */
    dio.options.contentType = CONTENT_TYPE;
    dio.options.headers[HEADER_KEY] = HEADER_VALUE;
  }

  @override
  Widget build(BuildContext context) {
    ApiClientRecipe(dio).getListRecipe(page, query).then((response) => {
          setState(() {
            listRecipe = response.results;
          })
        });
    return MaterialApp(
        title: 'Flutter Demo',
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        home: SafeArea(
            child: Scaffold(
          appBar: AppBar(
            title: const Text("Recipe"),
            centerTitle: true,
          ),
          body: GridView.builder(
            gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 1, mainAxisSpacing: 10),
            itemCount: listRecipe?.length,
            physics: const BouncingScrollPhysics(),
            itemBuilder: (context, index) => Column(children: <Widget>[
              Text(listRecipe?.elementAt(index).title ?? EMPTY),
              Image.network(
                listRecipe?.elementAt(index).featuredImage ?? EMPTY,
                fit: BoxFit.fill,
                height: 200,
                width: 200,
              ),
            ]),
          ),
        )));
  }
}
