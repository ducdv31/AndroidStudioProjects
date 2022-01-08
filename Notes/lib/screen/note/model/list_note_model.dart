import 'package:json_annotation/json_annotation.dart';
part 'list_note_model.g.dart';

@JsonSerializable()
class ListNoteModel {
  ListNoteModel({
      this.code, 
      this.data,});

  int? code;
  Data? data;
  factory ListNoteModel.fromJson(Map<String, dynamic> json) => _$ListNoteModelFromJson(json);
  Map<String, dynamic> toJson() => _$ListNoteModelToJson(this);

}

@JsonSerializable()
class Data {
  Data({
      this.notes,});

  List<Notes>? notes;
  factory Data.fromJson(Map<String, dynamic> json) => _$DataFromJson(json);
  Map<String, dynamic> toJson() => _$DataToJson(this);

}

@JsonSerializable()
class Notes {
  Notes({
      this.id, 
      this.note, 
      this.date,});

  int? id;
  String? note;
  int? date;
  factory Notes.fromJson(Map<String, dynamic> json) => _$NotesFromJson(json);
  Map<String, dynamic> toJson() => _$NotesToJson(this);

}