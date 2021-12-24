import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:recipe_flutter/constant/screen_route.dart';
import 'package:recipe_flutter/ui/main_screen.dart';
import 'package:recipe_flutter/ui/recipe_screen/detail_recipe_screen.dart';

import 'generated/l10n.dart';

void main() {
  SystemChrome.setSystemUIOverlayStyle(const SystemUiOverlayStyle(
    systemNavigationBarColor: Colors.red, // navigation bar color
    statusBarColor: Colors.red, // status bar color
  ));
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
        primaryColor: Colors.redAccent,
        primarySwatch: Colors.red,
      ),
      localizationsDelegates: const [
        S.delegate,
        GlobalMaterialLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
        GlobalCupertinoLocalizations.delegate,
      ],
      supportedLocales: S.delegate.supportedLocales,
      initialRoute: firstScreen,
      routes: {
        firstScreen: (context) => const HomeScreen(),
        detailRecipeRoute: (context) => const DetailRecipeScreen()
      },
    );
  }
}
