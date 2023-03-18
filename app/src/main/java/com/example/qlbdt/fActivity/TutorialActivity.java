package com.example.qlbdt.fActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qlbdt.R;
import com.example.qlbdt.fAdapter.ViewPagerTutorAdapter;

import me.relex.circleindicator.CircleIndicator;

public class TutorialActivity extends AppCompatActivity {

    TextView tvSkipOanh;
    ViewPager viewPagerOanh;
    RelativeLayout layoutBottomOanh;
    CircleIndicator circleIndicatorOanh;
    LinearLayout layoutNextOanh;
    ViewPagerTutorAdapter viewPagerTutorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        tvSkipOanh = findViewById(R.id.tvSkipOanh_activity);
        viewPagerOanh =  findViewById(R.id.viewPager_Oanh_activity);
        layoutBottomOanh = findViewById(R.id.layout_bottomOanh_activity);
        circleIndicatorOanh = findViewById(R.id.circleindicator_Oanh_activity);
        layoutNextOanh = findViewById(R.id.layout_nextOanh_activity);
        viewPagerTutorAdapter = new ViewPagerTutorAdapter(this.getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerOanh.setAdapter(viewPagerTutorAdapter);

        circleIndicatorOanh.setViewPager(viewPagerOanh);

        tvSkipOanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPagerOanh.setCurrentItem(7);
            }
        });
        layoutNextOanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPagerOanh.getCurrentItem()<7){
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
                if(position==7){
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
    }
}