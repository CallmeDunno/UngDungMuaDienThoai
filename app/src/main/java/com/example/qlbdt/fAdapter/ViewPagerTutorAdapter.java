package com.example.qlbdt.fAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.qlbdt.fFragment.TutorialFragment2;
import com.example.qlbdt.fFragment.TutorialFragment3;
import com.example.qlbdt.fFragment.TutorialFragment4;

public class ViewPagerTutorAdapter extends FragmentStatePagerAdapter {
    public ViewPagerTutorAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TutorialFragment2();
            case 1:
                return new TutorialFragment3();
            case 2:
                return new TutorialFragment4();
            default:
                return new TutorialFragment2();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
