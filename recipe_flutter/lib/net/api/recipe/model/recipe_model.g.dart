// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'recipe_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ResponseRecipe _$ResponseRecipeFromJson(Map<String, dynamic> json) =>
    ResponseRecipe(
      count: json['count'] as int?,
      next: json['next'] as String?,
      previous: json['previous'] as String?,
      results: (json['results'] as List<dynamic>?)
          ?.map((e) => Results.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$ResponseRecipeToJson(ResponseRecipe instance) =>
    <String, dynamic>{
      'count': instance.count,
      'next': instance.next,
      'previous': instance.previous,
      'results': instance.results,
    };

Results _$ResultsFromJson(Map<String, dynamic> json) => Results(
      pk: json['pk'] as int?,
      title: json['title'] as String?,
      publisher: json['publisher'] as String?,
      featuredImage: json['featured_image'] as String?,
      rating: json['rating'] as int?,
      sourceUrl: json['source_url'] as String?,
      description: json['description'] as String?,
      cookingInstructions: json['cooking_instructions'],
      ingredients: (json['ingredients'] as List<dynamic>?)
          ?.map((e) => e as String)
          .toList(),
      dateAdded: json['date_added'] as String?,
      dateUpdated: json['date_updated'] as String?,
      longDateAdded: (json['long_date_added'] as num?)?.toDouble(),
      longDateUpdated: (json['long_date_updated'] as num?)?.toDouble(),
    );

Map<String, dynamic> _$ResultsToJson(Results instance) => <String, dynamic>{
      'pk': instance.pk,
      'title': instance.title,
      'publisher': instance.publisher,
      'featured_image': instance.featuredImage,
      'rating': instance.rating,
      'source_url': instance.sourceUrl,
      'description': instance.description,
      'cooking_instructions': instance.cookingInstructions,
      'ingredients': instance.ingredients,
      'date_added': instance.dateAdded,
      'date_updated': instance.dateUpdated,
      'long_date_added': instance.longDateAdded,
      'long_date_updated': instance.longDateUpdated,
    };
