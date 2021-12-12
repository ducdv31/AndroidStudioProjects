import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:recipe_flutter/constant/constant.dart';
import 'package:recipe_flutter/net/api/recipe/api_client_recipe.dart';
import 'package:recipe_flutter/net/api/recipe/model/recipe_model.dart';
import 'package:recipe_flutter/ui/recipe_screen/cubit/recipe_state.dart';

class RecipeCubit extends Cubit<RecipeState> {
  RecipeCubit() : super(InitState());

  final _dio = Dio();
  var _query = EMPTY;

  List<Results> _list = List.empty(growable: true);

  Future<void> requestNew(int page, {String strSearch = EMPTY}) async {
    /* get data recipe */
    _query = strSearch;
    emit(LoadingState(_list));
    _dio.options.contentType = CONTENT_TYPE;
    _dio.options.headers[HEADER_KEY] = HEADER_VALUE;
    var list = (await ApiClientRecipe(_dio).getListRecipe(page, _query));
    _list = list.results ?? List.empty(growable: true);
    emit(RequestNewState(list.results ?? List.empty()));
  }

  void loadMore(int page) async {
    _dio.options.contentType = CONTENT_TYPE;
    _dio.options.headers[HEADER_KEY] = HEADER_VALUE;
    emit(LoadingMoreState(_list));
    var list = (await ApiClientRecipe(_dio).getListRecipe(page, _query));
    _list.addAll(list.results!);
    emit(LoadMoreState(_list));
  }
}
