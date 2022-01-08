import 'package:json_annotation/json_annotation.dart';

part 'response_common.g.dart';

@JsonSerializable()
class ResponseLogin {
  ResponseLogin({
    this.code,
    this.data,
  });

  int? code;
  Data? data;

  factory ResponseLogin.fromJson(Map<String, dynamic> json) =>
      _$ResponseLoginFromJson(json);

  Map<String, dynamic> toJson() => _$ResponseLoginToJson(this);
}

@JsonSerializable()
class Data {
  Data({
    this.token,
  });

  String? token;

  factory Data.fromJson(Map<String, dynamic> json) => _$DataFromJson(json);

  Map<String, dynamic> toJson() => _$DataToJson(this);
}
