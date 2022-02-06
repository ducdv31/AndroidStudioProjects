import 'package:flutter/material.dart';

TextField textFieldCommon(String label, ValueChanged<String>? onChange) {
  return TextField(
    decoration: InputDecoration(
        label: Text(label),
        border: const OutlineInputBorder(
          borderRadius: BorderRadius.all(Radius.circular(8)),
        )),
    onChanged: onChange,
  );
}
