package com.example.qlbdt.fAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.qlbdt.fFragment.TutorialFragment2;
import com.example.qlbdt.fFragment.TutorialFragment3;
import com.example.qlbdt.fFragment.TutorialFragment4;
import com.example.qlbdt.fFragment.TutorialFragment5;
import com.example.qlbdt.fFragment.TutorialFragment6;
import com.example.qlbdt.fFragment.TutorialFragment7;
import com.example.qlbdt.fFragment.TutorialFragment8;
import com.example.qlbdt.fFragment.TutorialFragment9;
import com.example.qlbdt.fFragment.TutorialFragmentLast;

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
            case 3:
                return new TutorialFragment5();
            case 4:
                return new TutorialFragment6();
            case 5:
                return new TutorialFragment7();
            case 6:
                return new TutorialFragment8();
            case 7:
                return new TutorialFragment9();
            case 8:
                return new TutorialFragmentLast();
            default:
                return new TutorialFragment2();
        }
    }

    @Override
    public int getCount() {
        return 9;
    }
}
