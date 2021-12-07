import 'package:json_annotation/json_annotation.dart';
part 'recipe_model.g.dart';

@JsonSerializable()
class ResponseRecipe {
  ResponseRecipe({
      this.count,
      this.next,
      this.previous,
      this.results,});
  int? count;
  String? next;
  String? previous;
  List<Results>? results;

  factory ResponseRecipe.fromJson(Map<String, dynamic> json) => _$ResponseRecipeFromJson(json);
  Map<String, dynamic> toJson() => _$ResponseRecipeToJson(this);
}

@JsonSerializable()
class Results {
  Results({
      this.pk,
      this.title,
      this.publisher,
      this.featuredImage,
      this.rating,
      this.sourceUrl,
      this.description,
      this.cookingInstructions,
      this.ingredients,
      this.dateAdded,
      this.dateUpdated,
      this.longDateAdded,
      this.longDateUpdated,});
  @JsonKey(name: "pk")
  int? pk;
  @JsonKey(name: "title")
  String? title;
  @JsonKey(name: "publisher")
  String? publisher;
  @JsonKey(name: "featured_image")
  String? featuredImage;
  @JsonKey(name: "rating")
  int? rating;
  @JsonKey(name: "source_url")
  String? sourceUrl;
  @JsonKey(name: "description")
  String? description;
  @JsonKey(name: "cooking_instructions")
  dynamic cookingInstructions;
  @JsonKey(name: "ingredients")
  List<String>? ingredients;
  @JsonKey(name: "date_added")
  String? dateAdded;
  @JsonKey(name: "date_updated")
  String? dateUpdated;
  @JsonKey(name: "long_date_added")
  double? longDateAdded;
  @JsonKey(name: "long_date_updated")
  double? longDateUpdated;
  factory Results.fromJson(Map<String, dynamic> json) => _$ResultsFromJson(json);
  Map<String, dynamic> toJson() => _$ResultsToJson(this);

}