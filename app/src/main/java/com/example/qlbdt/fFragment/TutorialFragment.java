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
    //Khai báo các biến được sử dụng trong Fragment.
    TextView tvSkipOanh;
    ViewPager viewPagerOanh;
    RelativeLayout layoutBottomOanh;
    CircleIndicator circleIndicatorOanh;
    LinearLayout layoutNextOanh;
    ViewPagerTutorAdapter viewPagerTutorAdapter;

    //Phương thức onCreateView được gọi khi Fragment được tạo ra để hiển thị trên màn hình.
    // Trong phương thức này, chúng ta ánh xạ các view bên trong Fragment từ file XML bằng cách sử dụng LayoutInflater và ViewGroup container.
    // Sau đó, chúng ta thiết lập các thuộc tính cho các view, bao gồm:
    //tvSkipOanh: TextView để bỏ qua trang hướng dẫn
    //viewPagerOanh: ViewPager để hiển thị các trang hướng dẫn
    //layoutBottomOanh: RelativeLayout chứa các nút điều hướng trang hướng dẫn (bên dưới ViewPager)
    //circleIndicatorOanh: CircleIndicator để hiển thị số trang và chỉ số của trang hiện tại
    //layoutNextOanh: LinearLayout chứa nút "Next" để chuyển đến trang tiếp theo
    //Sau đó, chúng ta tạo một ViewPagerTutorAdapter (lớp tự định nghĩa) và thiết lập ViewPager bằng adapter.

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
        //Chúng ta sử dụng CircleIndicator để hiển thị số trang và chỉ số của trang hiện tại.
        // Nếu người dùng nhấn vào nút "Skip", chúng ta sẽ chuyển đến trang cuối cùng trong viewpager
        tvSkipOanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPagerOanh.setCurrentItem(9);
            }
        });
        //Nếu trang hiện tại chưa phải trang cuối cùng (trang thứ 8), thì nó sẽ di chuyển đến trang tiếp theo(trang tiếp theo trong danh sách các trang của ViewPager)
        //sử dụng phương thức getCurrentItem() để lấy chỉ mục của trang hiện tại, sau đó tăng giá trị chỉ mục đó lên 1
        // và sử dụng phương thức setCurrentItem() để chuyển đến trang tiếp theo.
        // Nếu trang hiện tại đã là trang cuối cùng thì không có hành động gì xảy ra.
        layoutNextOanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPagerOanh.getCurrentItem()<9){
                    viewPagerOanh.setCurrentItem(viewPagerOanh.getCurrentItem()+1);
                }
            }
        });

        //Ở đây, chúng ta thêm một ViewPager.OnPageChangeListener() để theo dõi thay đổi trang trên ViewPager.
        // Nếu trang hiện tại là trang cuối cùng (vị trí 8), chúng ta sẽ ẩn TextView và RelativeLayout bằng cách đặt kiểu hiển thị của chúng thành View.GONE.
        // Ngược lại, nếu trang hiện tại không phải là trang cuối cùng, chúng ta hiển thị TextView và RelativeLayout
        // bằng cách đặt kiểu hiển thị của chúng thành View.VISIBLE.
        //Cuối cùng, hàm onCreateView() trả về đối tượng view.
        viewPagerOanh.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==9){
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