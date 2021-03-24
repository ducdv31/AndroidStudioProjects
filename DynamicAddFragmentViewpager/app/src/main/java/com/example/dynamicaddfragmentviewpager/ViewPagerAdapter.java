package com.example.dynamicaddfragmentviewpager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Questions> mListQuestions;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, List<Questions> list) {
        super(fm, behavior);
        mListQuestions = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (mListQuestions == null || mListQuestions.isEmpty()) {
            return null;
        }
        Questions questions = mListQuestions.get(position);
        FragmentQuestions fragmentQuestions = new FragmentQuestions();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Question_object", questions);
        fragmentQuestions.setArguments(bundle);
        return fragmentQuestions;
    }

    @Override
    public int getCount() {
        if (mListQuestions != null) {
            return mListQuestions.size();
        }
        return 0;
    }
}
