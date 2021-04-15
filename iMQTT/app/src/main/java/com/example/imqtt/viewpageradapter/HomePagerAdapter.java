package com.example.imqtt.viewpageradapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.imqtt.navigation.tabinmain.ConfigMqttFragment;
import com.example.imqtt.navigation.tabinmain.PublishFragment;
import com.example.imqtt.navigation.tabinmain.SubscribeFragment;

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    public HomePagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new SubscribeFragment();
            case 2:
                return new ConfigMqttFragment();
            case 0:
            default:
                return new PublishFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Publish";
                break;
            case 1:
                title = "Subscribe";
                break;
            case 2:
                title = "Config";
                break;
        }
        return title;
    }
}
