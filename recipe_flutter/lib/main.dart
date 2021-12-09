import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:recipe_flutter/ui/recipe_screen/bloc/recipe_bloc.dart';
import 'package:recipe_flutter/ui/recipe_screen/recipe_screen.dart';

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
        home: BlocProvider(
          create: (context) => RecipeCubit(),
          child: SafeArea(child: recipeScreen()),
        ));
  }
}
