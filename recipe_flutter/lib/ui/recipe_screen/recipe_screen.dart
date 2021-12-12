import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/painting.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:recipe_flutter/constant/constant.dart';
import 'package:recipe_flutter/constant/screen_route.dart';
import 'package:recipe_flutter/net/api/recipe/model/recipe_model.dart';
import 'package:recipe_flutter/ui/recipe_screen/cubit/recipe_state.dart';

import 'cubit/recipe_bloc.dart';

int page = 1;

class RecipeScreen extends StatelessWidget {
  const RecipeScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return BlocProvider(
      create: (context) => RecipeCubit()..requestNew(1),
      child: SafeArea(
          child: Scaffold(
              resizeToAvoidBottomInset: false,
              appBar: AppBar(
                title: const Text("Recipe"),
                centerTitle: true,
              ),
              drawer: Container(
                color: Colors.white,
                margin: const EdgeInsets.only(right: 64),
                child: const Center(
                  child: Text("Made by Dang Duc"),
                ),
              ),
              body: BlocBuilder<RecipeCubit, RecipeState>(
                  builder: (context, state) {
                if (state is LoadingState) {
                  return listRecipe(context, state.list, false);
                } else if (state is RequestNewState) {
                  return listRecipe(context, state.list, false);
                } else if (state is LoadingMoreState) {
                  return listRecipe(context, state.list, true);
                } else if (state is LoadMoreState) {
                  return listRecipe(context, state.list, false);
                } else {
                  return const Center(
                    child: CircularProgressIndicator(),
                  );
                }
              }))),
    );
  }
}

Widget listRecipe(BuildContext context, List<Results> list, isLoadingMore) {
  return Scrollbar(
      child: CustomScrollView(
    physics: const BouncingScrollPhysics(),
    slivers: <Widget>[
      CupertinoSliverRefreshControl(
        onRefresh: () async {
          page = 1;
          await context.read<RecipeCubit>().requestNew(page);
        },
      ),
      SliverPadding(
          padding: const EdgeInsets.symmetric(horizontal: 4),
          sliver: SliverList(
            delegate: SliverChildBuilderDelegate((context, index) {
              if (index + 1 == page * 30) {
                page++;
                context.read<RecipeCubit>().loadMore(page);
              }
              return RecipeItem(list, index);
            }, childCount: list.length),
          )),
      SliverToBoxAdapter(
        child: isLoadingMore
            ? Container(
                child: const CircularProgressIndicator(),
                alignment: Alignment.center,
                padding: const EdgeInsets.all(8),
              )
            : const SizedBox(),
      )
    ],
  ));
}

class RecipeItem extends StatefulWidget {
  final List<Results>? list;
  final int index;

  const RecipeItem(this.list, this.index, {Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return _ItemState();
  }
}

class _ItemState extends State<RecipeItem> {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return InkWell(
      onTap: () {
        Navigator.pushNamed(context, detailRecipeRoute,
            arguments: widget.list?.elementAt(widget.index));
      },
      child: Card(
          elevation: 8,
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
          child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              mainAxisSize: MainAxisSize.min,
              children: <Widget>[
                const Padding(padding: EdgeInsets.all(2)),
                Container(
                  child: ClipRRect(
                    child: Image.network(
                      widget.list?.elementAt(widget.index).featuredImage ??
                          EMPTY,
                      fit: BoxFit.fitWidth,
                      height: 220,
                    ),
                    borderRadius: BorderRadius.circular(10),
                  ),
                  padding: const EdgeInsets.symmetric(horizontal: 8),
                ),
                Container(
                  child: Text(
                    widget.list?.elementAt(widget.index).title ?? EMPTY,
                    maxLines: 2,
                    overflow: TextOverflow.ellipsis,
                    style: const TextStyle(
                        fontWeight: FontWeight.bold, color: Colors.black),
                  ),
                  padding:
                      const EdgeInsets.symmetric(horizontal: 8, vertical: 8),
                ),
              ])),
    );
  }
}
