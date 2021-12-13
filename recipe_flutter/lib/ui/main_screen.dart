import 'package:flutter/animation.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:recipe_flutter/ui/recipe_screen/recipe_screen.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  late Widget recipeScreen;
  late List<Widget> _listHomeScreen;
  late List<String> _listNameScreen;

  _HomeScreenState() {
    recipeScreen = const RecipeScreen();
    _listHomeScreen = <Widget>[
      recipeScreen,
      const Center(
        child: Text("Hello movie"),
      )
    ];
    _listNameScreen = ["Recipe", "Movie"];
  }

  final PageController _pageController = PageController(initialPage: 0);
  int currentIndex = 0;

  @override
  Widget build(BuildContext context) {
    return SafeArea(
        child: Scaffold(
      appBar: AppBar(
          title: Text(_listNameScreen.elementAt(currentIndex)),
          centerTitle: true),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: currentIndex,
        onTap: (index) {
          setState(() {
            currentIndex = index;
          });
          _pageController.animateToPage(index,
              duration: const Duration(milliseconds: 500),
              curve: Curves.fastOutSlowIn);
        },
        items: const [
          BottomNavigationBarItem(
              label: "Recipe", icon: Icon(Icons.emoji_food_beverage_outlined)),
          BottomNavigationBarItem(
              label: "Movie", icon: Icon(Icons.local_movies_outlined)),
        ],
        showSelectedLabels: false,
        showUnselectedLabels: false,
      ),
      body: PageView.builder(
        controller: _pageController,
        physics: const BouncingScrollPhysics(),
        itemBuilder: (context, index) {
          return _listHomeScreen.elementAt(index);
        },
        itemCount: _listHomeScreen.length,
        onPageChanged: (index) {
          setState(() {
            currentIndex = index;
          });
        },
      ),
    ));
  }
}
