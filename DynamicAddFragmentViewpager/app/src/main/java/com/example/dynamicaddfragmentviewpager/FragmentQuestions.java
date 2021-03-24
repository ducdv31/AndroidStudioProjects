package com.example.dynamicaddfragmentviewpager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentQuestions extends Fragment {

    private TextView tvQuestions;

    public FragmentQuestions() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View mView = inflater.inflate(R.layout.fragment_questions, container, false);
        tvQuestions = mView.findViewById(R.id.tv_question);

        Bundle bundleReceive = getArguments();
        if (bundleReceive != null) {
            Questions questions = (Questions) bundleReceive.get("Question_object");
            if (questions != null) {
                tvQuestions.setText(questions.getName());
            }
        }

        return mView;
    }
}