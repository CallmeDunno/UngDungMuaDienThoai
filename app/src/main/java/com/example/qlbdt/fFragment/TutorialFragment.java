package com.example.qlbdt.fFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.qlbdt.R;
import com.example.qlbdt.fAdapter.ViewPagerTutorAdapter;

import me.relex.circleindicator.CircleIndicator;

public class TutorialFragment extends Fragment {
    TextView tvSkipOanh;
    ViewPager viewPagerOanh;
    RelativeLayout layoutBottomOanh;
    CircleIndicator circleIndicatorOanh;
    LinearLayout layoutNextOanh;
    ViewPagerTutorAdapter viewPagerTutorAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutorial, container, false);
        tvSkipOanh = view.findViewById(R.id.tvSkipOanh);
        viewPagerOanh =  view.findViewById(R.id.viewPager_Oanh);
        layoutBottomOanh = view.findViewById(R.id.layout_bottomOanh);
        circleIndicatorOanh = view.findViewById(R.id.circleindicator_Oanh);
        layoutNextOanh = view.findViewById(R.id.layout_nextOanh);
        viewPagerTutorAdapter = new ViewPagerTutorAdapter(getActivity().getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerOanh.setAdapter(viewPagerTutorAdapter);

        circleIndicatorOanh.setViewPager(viewPagerOanh);

        tvSkipOanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPagerOanh.setCurrentItem(9);
            }
        });

        layoutNextOanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPagerOanh.getCurrentItem()<9){
                    viewPagerOanh.setCurrentItem(viewPagerOanh.getCurrentItem()+1);
                }
            }
        });

        viewPagerOanh.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==8){
                    tvSkipOanh.setVisibility(View.GONE);
                    layoutBottomOanh.setVisibility(View.GONE);
                } else {
                    tvSkipOanh.setVisibility(View.VISIBLE);
                    layoutBottomOanh.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }
}