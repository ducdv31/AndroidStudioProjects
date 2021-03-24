package com.example.myhouse.NavFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myhouse.R;
import com.example.myhouse.tabhomeadapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class FragmentHome extends Fragment {
    private TabLayout tabLayout;
    private static ViewPager viewPager;
    private View Dht11View;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Dht11View = inflater.inflate(R.layout.fragment_home, container, false);
        tabLayout = Dht11View.findViewById(R.id.tab_select);
        viewPager = Dht11View.findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(requireActivity()
                .getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        // Inflate the layout for this fragment
        return Dht11View;
    }

    public static FragmentHome newInstance(int page) {

        Bundle args = new Bundle();
        viewPager.getCurrentItem();
        args.putInt("Page", page);
        FragmentHome fragment = new FragmentHome();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        int item = viewPager.getCurrentItem();
        int pageMargin = viewPager.getPageMargin();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}