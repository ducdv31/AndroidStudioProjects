import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:recipe_flutter/constant/constant.dart';
import 'package:recipe_flutter/net/api/recipe/model/recipe_model.dart';

class DetailRecipeScreen extends StatelessWidget {
  const DetailRecipeScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final args = ModalRoute.of(context)!.settings.arguments as Results?;
    if (args != null) {
      return _DetailRecipe(args);
    } else {
      return const SizedBox();
    }
  }
}

class _DetailRecipe extends StatelessWidget {
  final Results results;

  const _DetailRecipe(this.results);

  @override
  Widget build(BuildContext context) {
    return SafeArea(
        child: Scaffold(
      appBar: AppBar(
        title: Text(results.title ?? EMPTY),
        centerTitle: true,
      ),
      body: Column(
        children: [
          SizedBox(
            child: ClipRRect(
              child: Image.network(
                results.featuredImage ?? EMPTY,
                fit: BoxFit.cover,
              ),
              borderRadius: const BorderRadius.only(
                  bottomRight: Radius.circular(16),
                  bottomLeft: Radius.circular(16)),
            ),
            width: double.maxFinite,
            height: 300,
          ),
          Container(
            margin: const EdgeInsets.symmetric(vertical: 4),
            child: Text(
              results.title ?? EMPTY,
              style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
              maxLines: 2,
              overflow: TextOverflow.ellipsis,
            ),
          ),
          Expanded(
            child: Container(
                margin: const EdgeInsets.symmetric(horizontal: 8, vertical: 8),
                child: Scrollbar(
                  child: ListView.builder(
                      physics: const BouncingScrollPhysics(),
                      itemCount: results.ingredients?.length,
                      itemBuilder: (context, index) {
                        return Text(
                          "Step ${index + 1} : ${results.ingredients?.elementAt(index)}",
                          style: const TextStyle(fontSize: 16),
                        );
                      }),
                )),
          ),
          Container(
            padding: const EdgeInsets.symmetric(vertical: 4),
            child: Text("Updated: ${results.dateUpdated}"),
          )
        ],
      ),
    ));
  }
}
