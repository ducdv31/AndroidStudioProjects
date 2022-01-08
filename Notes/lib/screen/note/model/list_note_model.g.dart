// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'list_note_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ListNoteModel _$ListNoteModelFromJson(Map<String, dynamic> json) =>
    ListNoteModel(
      code: json['code'] as int?,
      data: json['data'] == null
          ? null
          : Data.fromJson(json['data'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$ListNoteModelToJson(ListNoteModel instance) =>
    <String, dynamic>{
      'code': instance.code,
      'data': instance.data,
    };

Data _$DataFromJson(Map<String, dynamic> json) => Data(
      notes: (json['notes'] as List<dynamic>?)
          ?.map((e) => Notes.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$DataToJson(Data instance) => <String, dynamic>{
      'notes': instance.notes,
    };

Notes _$NotesFromJson(Map<String, dynamic> json) => Notes(
      id: json['id'] as int?,
      note: json['note'] as String?,
      date: json['date'] as int?,
    );

Map<String, dynamic> _$NotesToJson(Notes instance) => <String, dynamic>{
      'id': instance.id,
      'note': instance.note,
      'date': instance.date,
    };
