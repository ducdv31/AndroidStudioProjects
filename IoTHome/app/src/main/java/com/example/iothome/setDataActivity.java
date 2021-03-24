package com.example.iothome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class setDataActivity extends AppCompatActivity {
    private Button submitbt;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_data);
        submitbt = (Button) findViewById(R.id.submitBt);
        editText = (EditText) findViewById(R.id.editText);
        submitbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                SetDatabase setText = new SetDatabase("TextInput/line1",text);
                setText.setText();
                editText.setText("");
            }
        });
    }
}