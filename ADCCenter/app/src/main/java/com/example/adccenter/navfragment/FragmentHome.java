package com.example.adccenter.navfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adccenter.R;
import com.example.adccenter.tabadapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class FragmentHome extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View NavHome = inflater.inflate(R.layout.fragment_home, container, false);
        tabLayout = NavHome.findViewById(R.id.tab_select);
        viewPager = NavHome.findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(requireActivity()
                .getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return NavHome;
    }
}