import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/painting.dart';
import 'package:flutter/rendering.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:recipe_flutter/net/api/recipe/api_client_recipe.dart';
import 'package:recipe_flutter/net/api/recipe/model/recipe_model.dart';

import 'constant/constant.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  MyApp({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _StateMyApp();
}

class _StateMyApp extends State<MyApp> {
  final dio = Dio();
  var page = 1;
  var query = EMPTY;

  final RefreshController _refreshController =
      RefreshController(initialRefresh: false);

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
    return MaterialApp(
        title: 'Flutter Demo',
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        home: SafeArea(
            child: Scaffold(
                resizeToAvoidBottomInset: false,
                appBar: AppBar(
                  title: const Text("Recipe"),
                  centerTitle: true,
                ),
                body: listRecipe()
            )));
  }

  FutureBuilder<ResponseRecipe> listRecipe() {
    return FutureBuilder<ResponseRecipe>(
      future: ApiClientRecipe(dio).getListRecipe(page, query),
      builder: (context, snapshot) {
        var list = snapshot.data?.results;
        return ListView.builder(
            itemCount: list?.length,
            physics: const BouncingScrollPhysics(),
            shrinkWrap: true,
            padding: const EdgeInsets.all(10),
            itemBuilder: (context, index) => Card(
                  elevation: 8,
                  shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(8)),
                  child: Column(
                      crossAxisAlignment: CrossAxisAlignment.stretch,
                      mainAxisSize: MainAxisSize.min,
                      children: <Widget>[
                        const Padding(padding: EdgeInsets.all(2)),
                        Container(
                          child: ClipRRect(
                            child: Image.network(
                              list?.elementAt(index).featuredImage ?? EMPTY,
                              fit: BoxFit.fitWidth,
                              height: 220,
                            ),
                            borderRadius: BorderRadius.circular(10),
                          ),
                          padding: const EdgeInsets.symmetric(horizontal: 8),
                        ),
                        Container(
                          child: Text(
                            list?.elementAt(index).title ?? EMPTY,
                            maxLines: 2,
                            overflow: TextOverflow.ellipsis,
                            style: const TextStyle(
                                fontWeight: FontWeight.bold,
                                color: Colors.black),
                          ),
                          padding: const EdgeInsets.symmetric(
                              horizontal: 8, vertical: 8),
                        ),
                      ]),
                ));
      },
    );
  }
}
