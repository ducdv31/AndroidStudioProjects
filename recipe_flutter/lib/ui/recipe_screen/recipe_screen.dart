import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:recipe_flutter/constant/constant.dart';
import 'package:recipe_flutter/net/api/recipe/api_client_recipe.dart';
import 'package:recipe_flutter/net/api/recipe/model/recipe_model.dart';

import 'bloc/recipe_bloc.dart';

final dio = Dio();
var page = 1;
var query = EMPTY;

Scaffold recipeScreen() {
  /* get data recipe */
  dio.options.contentType = CONTENT_TYPE;
  dio.options.headers[HEADER_KEY] = HEADER_VALUE;

  return Scaffold(
      resizeToAvoidBottomInset: false,
      appBar: AppBar(
        title: const Text("Recipe"),
        centerTitle: true,
      ),
      drawer: const Text("Drawer"),
      body: BlocBuilder<RecipeCubit, int>(
        builder: (context, count) {
          print(count);
          return listRecipe();
        },
      ));
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
          itemBuilder: (context, index) =>
              Card(
                elevation: 8,
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(8)),
                child: InkWell(
                  onTap: () {
                    if (index % 2 == 0) {
                      return context.read<RecipeCubit>().requestNew();
                    } else {
                      return context.read<RecipeCubit>().loadMore();
                    }
                  },
                  child: recipeItem(list, index),
                ),
              ));
    },
  );
}

Column recipeItem(List<Results>? list, int index) {
  return Column(
      crossAxisAlignment: CrossAxisAlignment.stretch,
      mainAxisSize: MainAxisSize.min,
      children: <Widget>[
        const Padding(padding: EdgeInsets.all(2)),
        Container(
          child: ClipRRect(
            child: Image.network(
              list
                  ?.elementAt(index)
                  .featuredImage ?? EMPTY,
              fit: BoxFit.fitWidth,
              height: 220,
            ),
            borderRadius: BorderRadius.circular(10),
          ),
          padding: const EdgeInsets.symmetric(horizontal: 8),
        ),
        Container(
          child: Text(
            list
                ?.elementAt(index)
                .title ?? EMPTY,
            maxLines: 2,
            overflow: TextOverflow.ellipsis,
            style: const TextStyle(
                fontWeight: FontWeight.bold, color: Colors.black),
          ),
          padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 8),
        ),
      ]);
}
