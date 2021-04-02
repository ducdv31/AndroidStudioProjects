package com.example.myhouse.tabhomeadapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myhouse.homecontent.FragmentAH10;
import com.example.myhouse.homecontent.FragmentChart;
import com.example.myhouse.homecontent.FragmentDht11;
import com.example.myhouse.homecontent.FragmentPms7003;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new FragmentPms7003();
            case 0:
            default:
                return new FragmentAH10();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "AHT10";
                break;
            case 1:
                title = "PMS7003";
                break;
        }
        return title;
    }
}
