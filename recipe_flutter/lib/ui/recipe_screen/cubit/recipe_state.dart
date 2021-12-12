import 'package:equatable/equatable.dart';
import 'package:recipe_flutter/net/api/recipe/model/recipe_model.dart';

abstract class RecipeState extends Equatable {}

class InitState extends RecipeState {
  @override
  // TODO: implement props
  List<Object?> get props => [];

}

class LoadingState extends RecipeState {
  final List<Results> list;

  LoadingState(this.list);

  @override
  // TODO: implement props
  List<Object?> get props => [list];
}

class RequestNewState extends RecipeState {
  final List<Results> list;

  RequestNewState(this.list);

  @override
  // TODO: implement props
  List<Object?> get props => [list];

}

class LoadMoreState extends RecipeState {
  final List<Results> list;
  LoadMoreState(this.list);

  @override
  // TODO: implement props
  List<Object?> get props => [list];

}

class LoadingMoreState extends RecipeState {
  final List<Results> list;

  LoadingMoreState(this.list);

  @override
  // TODO: implement props
  List<Object?> get props => [list];

}
