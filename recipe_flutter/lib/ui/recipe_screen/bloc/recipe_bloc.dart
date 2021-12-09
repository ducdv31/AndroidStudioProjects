

import 'package:flutter_bloc/flutter_bloc.dart';

class RecipeCubit extends Cubit<int> {
  RecipeCubit() : super(0);
  void requestNew() => emit(1);
  void loadMore() => emit(2);
}