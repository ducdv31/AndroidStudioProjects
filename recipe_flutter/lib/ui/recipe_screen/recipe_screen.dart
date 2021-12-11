import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:recipe_flutter/constant/constant.dart';
import 'package:recipe_flutter/net/api/recipe/model/recipe_model.dart';
import 'package:recipe_flutter/ui/recipe_screen/cubit/recipe_state.dart';

import 'cubit/recipe_bloc.dart';

Scaffold recipeScreen() {
  return Scaffold(
      resizeToAvoidBottomInset: false,
      appBar: AppBar(
        title: const Text("Recipe"),
        centerTitle: true,
      ),
      drawer: const Text("Drawer"),
      body: BlocBuilder<RecipeCubit, RecipeState>(builder: (context, state) {
        if (state is LoadingState) {
          return const Center(
            child: CircularProgressIndicator(),
          );
        } else if (state is RequestNewState) {
          return listRecipe(state.list);
        } else if (state is LoadingMoreState) {
          return const Center(
            child: CircularProgressIndicator(),
          );
        } else if (state is LoadMoreState) {
          return listRecipe(state.list);
        } else {
          return const Center(
            child: CircularProgressIndicator(),
          );
        }
      }));
}

Widget listRecipe(List<Results> list) {
  return RefreshIndicator(
      onRefresh: () async {
        RecipeCubit().requestNew();
      },
      child: ListView.builder(
          itemCount: list.length,
          physics: const BouncingScrollPhysics(),
          shrinkWrap: true,
          padding: const EdgeInsets.all(10),
          itemBuilder: (context, index) {
            if (index + 1 == list.length) {
              context.read<RecipeCubit>().loadMore();
            }
            return Card(
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
            );
          }));
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
                fontWeight: FontWeight.bold, color: Colors.black),
          ),
          padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 8),
        ),
      ]);
}
