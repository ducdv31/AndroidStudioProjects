import 'package:dio/dio.dart';
import 'package:retrofit/http.dart';

import 'model/recipe_model.dart';

part 'api_client_recipe.g.dart';

const BASE_URL_RECIPE = "https://food2fork.ca/api/recipe/";
const QUERRY_PAGE = "page";
const QUERRY_TEXT = "query";
const CONTENT_TYPE = "application/json";

const HEADER_KEY = "Authorization";
const HEADER_VALUE = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48";

@RestApi(baseUrl: BASE_URL_RECIPE)
abstract class ApiClientRecipe {
  factory ApiClientRecipe(Dio dio, {String baseUrl}) = _ApiClientRecipe;

  @GET("search/")
  Future<ResponseRecipe> getListRecipe(
      @Query(QUERRY_PAGE) int page, @Query(QUERRY_TEXT) String query);
}
