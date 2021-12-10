import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:recipe_flutter/constant/constant.dart';
import 'package:recipe_flutter/net/api/recipe/api_client_recipe.dart';
import 'package:recipe_flutter/net/api/recipe/model/recipe_model.dart';

class RecipeCubit extends Cubit<List<Results>> {
  RecipeCubit() : super(List.empty());

  final dio = Dio();
  var page = 1;
  var query = EMPTY;
  List<Results> listResult = List.empty(growable: true);

  void requestNew() async {
    /* get data recipe */
    dio.options.contentType = CONTENT_TYPE;
    dio.options.headers[HEADER_KEY] = HEADER_VALUE;
    page = 1;
    var list = await ApiClientRecipe(dio).getListRecipe(page, query);
    listResult.clear();
    listResult.addAll(list.results ?? List.empty());
    emit(listResult);
  }

  void loadMore() async {
    dio.options.contentType = CONTENT_TYPE;
    dio.options.headers[HEADER_KEY] = HEADER_VALUE;
    page++;
    var list = await ApiClientRecipe(dio).getListRecipe(page, query);
    listResult.addAll(list.results ?? List.empty());
    List<Results> listMore = List.empty(growable: true);
    listMore.addAll(listResult);
    emit(listMore);
  }
}