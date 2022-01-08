import 'package:dio/dio.dart';
import 'package:notes/data/common/response_common.dart';
import 'package:notes/screen/note/model/list_note_model.dart';
import 'package:notes/utils/constants.dart';
import 'package:retrofit/http.dart';

part 'api_service.g.dart';

const BASE_URL = "https://ktor-project-ducdv.herokuapp.com/";
const HEADER_KEY = "Authorization";
const CONTENT_TYPE = "application/json";

@RestApi(baseUrl: BASE_URL)
abstract class ApiClientNote {
  factory ApiClientNote(Dio dio) = _ApiClientNote;

  @POST("/login")
  Future<ResponseLogin> logIn(@Field(USERNAME) String username,
      @Field(PASSWORD) String password,);

  @GET("/notes")
  Future<ListNoteModel> getAllDataNotes(@Header(HEADER_KEY) String auth,);
}
