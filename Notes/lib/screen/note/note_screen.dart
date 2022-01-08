import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';
import 'package:notes/data/net/api/api_service.dart';
import 'package:notes/utils/constants.dart';
import 'package:shared_preferences/shared_preferences.dart';

class NoteScreen extends StatefulWidget {
  const NoteScreen({Key? key}) : super(key: key);

  @override
  _NoteScreenState createState() => _NoteScreenState();
}

class _NoteScreenState extends State<NoteScreen> {

  @override
  void initState() async {
    // TODO: implement initState
    super.initState();
    SharedPreferences prefs = await SharedPreferences.getInstance();
    var token = prefs.getString(TOKEN);
    var dio = Dio();
    dio.options.contentType = CONTENT_TYPE;
    ApiClientNote apiClientNote = ApiClientNote(dio);
    var dataNote = await apiClientNote.getAllDataNotes(token ?? EMPTY);
    var listNote = dataNote.data?.notes;
    listNote?.forEach((element) {
      print(element);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Container();
  }
}
