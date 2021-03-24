package com.example.dynamicaddfragmentviewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView Back, Current, Total, Next;
    private ViewPager viewPager2;

    private List<Questions> questionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        questionsList = getQuestionsList();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                questionsList);
        viewPager2.setAdapter(viewPagerAdapter);

        Current.setText("1");
        Total.setText(String.valueOf(questionsList.size()));

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        });

        viewPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Current.setText(String.valueOf(position + 1));
                if (position == 0) {
                    Back.setVisibility(View.GONE);
                    Next.setVisibility(View.VISIBLE);
                } else if (position == questionsList.size() - 1) {
                    Back.setVisibility(View.VISIBLE);
                    Next.setVisibility(View.GONE);
                } else {
                    Back.setVisibility(View.VISIBLE);
                    Next.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initUI() {
        Back = findViewById(R.id.tv_back);
        Current = findViewById(R.id.tv_current);
        Total = findViewById(R.id.tv_total);
        Next = findViewById(R.id.tv_next);
        viewPager2 = findViewById(R.id.view_pager);

    }

    private List<Questions> getQuestionsList() {
        List<Questions> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            list.add(new Questions("This questions " + i));
        }

        return list;
    }
}