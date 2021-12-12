import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/painting.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:recipe_flutter/constant/constant.dart';
import 'package:recipe_flutter/constant/screen_route.dart';
import 'package:recipe_flutter/net/api/recipe/model/recipe_model.dart';
import 'package:recipe_flutter/ui/recipe_screen/cubit/recipe_state.dart';

import 'cubit/recipe_bloc.dart';

int _page = 1;
final TextEditingController _textEditingController =
    TextEditingController(text: EMPTY);
bool _isSearching = false;
final ScrollController _scrollController = ScrollController();

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
                  return _listRecipe(context, state.list, false);
                } else if (state is RequestNewState) {
                  return _listRecipe(context, state.list, false);
                } else if (state is LoadingMoreState) {
                  return _listRecipe(context, state.list, true);
                } else if (state is LoadMoreState) {
                  return _listRecipe(context, state.list, false);
                } else {
                  return const Center(
                    child: CircularProgressIndicator(),
                  );
                }
              }))),
    );
  }
}

Widget _listRecipe(
    BuildContext context, List<Results> list, bool isLoadingMore) {
  return Column(
    children: [
      const SearchBarRecipe(),
      if (_isSearching)
        Container(
          padding: const EdgeInsets.symmetric(vertical: 16),
          child: const CircularProgressIndicator(),
        ),
      Expanded(
          child: Scrollbar(
              controller: _scrollController,
              notificationPredicate: (scrollNoti) {
                if (scrollNoti is ScrollStartNotification) {
                  FocusScope.of(context).unfocus();
                }
                return true;
              },
              child: CustomScrollView(
                physics: const BouncingScrollPhysics(),
                slivers: <Widget>[
                  CupertinoSliverRefreshControl(
                    onRefresh: () async {
                      _page = 1;
                      await context.read<RecipeCubit>().requestNew(_page);
                    },
                  ),
                  SliverPadding(
                      padding: const EdgeInsets.symmetric(horizontal: 4),
                      sliver: SliverList(
                        delegate: SliverChildBuilderDelegate((context, index) {
                          if (index + 1 == _page * 30) {
                            _page++;
                            context.read<RecipeCubit>().loadMore(_page);
                          }
                          return _RecipeItem(list, index);
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
              )))
    ],
  );
}

class _RecipeItem extends StatefulWidget {
  final List<Results>? list;
  final int index;

  const _RecipeItem(this.list, this.index, {Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return _ItemState();
  }
}

class SearchBarRecipe extends StatefulWidget {
  const SearchBarRecipe({Key? key}) : super(key: key);

  @override
  _SearchBarRecipeState createState() => _SearchBarRecipeState();
}

class _SearchBarRecipeState extends State<SearchBarRecipe> {
  String strSearch = EMPTY;

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
      child: TextField(
        controller: _textEditingController,
        onChanged: (text) {
          setState(() {
            strSearch = text;
          });
        },
        onSubmitted: (String text) async {
          _page = 1;
          _isSearching = true;
          await context.read<RecipeCubit>().requestNew(_page, strSearch: text);
          _isSearching = false;
        },
        maxLines: 1,
        textInputAction: TextInputAction.search,
        keyboardType: TextInputType.text,
        decoration: InputDecoration(
            border: const OutlineInputBorder(
                borderRadius: BorderRadius.all(Radius.circular(16))),
            labelText: "Search recipe",
            prefixIcon: const Icon(Icons.search),
            suffixIcon: strSearch.isNotEmpty
                ? IconButton(
                    onPressed: () async {
                      FocusScope.of(context).unfocus();
                      _textEditingController.clear();
                      setState(() {
                        strSearch = EMPTY;
                      });
                      _page = 1;
                      _isSearching = true;
                      await context.read<RecipeCubit>().requestNew(_page);
                      _isSearching = false;
                    },
                    icon: const Icon(Icons.clear))
                : const SizedBox()),
      ),
    );
  }
}

class _ItemState extends State<_RecipeItem> {
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
