import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:recipe_flutter/constant/constant.dart';
import 'package:recipe_flutter/net/api/recipe/api_client_recipe.dart';
import 'package:recipe_flutter/net/api/recipe/model/recipe_model.dart';
import 'package:recipe_flutter/ui/recipe_screen/cubit/recipe_state.dart';

class RecipeCubit extends Cubit<RecipeState> {
  RecipeCubit() : super(InitState());

  final dio = Dio();
  var query = EMPTY;

  List<Results> _list = List.empty(growable: true);

  Future<void> requestNew(int page) async {
    /* get data recipe */
    emit(LoadingState(_list));
    dio.options.contentType = CONTENT_TYPE;
    dio.options.headers[HEADER_KEY] = HEADER_VALUE;
    var list = (await ApiClientRecipe(dio).getListRecipe(page, query));
    _list = list.results ?? List.empty(growable: true);
    emit(RequestNewState(list.results ?? List.empty()));
  }

  void loadMore(int page) async {
    dio.options.contentType = CONTENT_TYPE;
    dio.options.headers[HEADER_KEY] = HEADER_VALUE;
    emit(LoadingMoreState(_list));
    var list = (await ApiClientRecipe(dio).getListRecipe(page, query));
    _list.addAll(list.results!);
    emit(LoadMoreState(_list));
  }
}
