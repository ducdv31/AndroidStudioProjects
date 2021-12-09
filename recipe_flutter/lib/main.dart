import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/painting.dart';
import 'package:flutter/rendering.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:recipe_flutter/net/api/recipe/api_client_recipe.dart';
import 'package:recipe_flutter/net/api/recipe/model/recipe_model.dart';
import 'package:recipe_flutter/ui/recipe_screen/recipe_screen.dart';

import 'constant/constant.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _StateMyApp();
}

class _StateMyApp extends State<MyApp> {

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Flutter Demo',
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        home: SafeArea(
            child: recipeScreen()));
  }
}
