package com.example.iothome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new mpu6050Fragment();
            case 2:
                return new espinfoFragment();
            case 3:
                return new MoreFragment();
            case 0:
            default:
                return new dht11Fragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position){
            case 0:
                title = "DHT11";
                break;
            case 1:
                title = "MPU6050";
                break;
            case 2:
                title = "ESP32";
                break;
            case 3:
                title = "More";
                break;
        }
        return title;
    }
}
