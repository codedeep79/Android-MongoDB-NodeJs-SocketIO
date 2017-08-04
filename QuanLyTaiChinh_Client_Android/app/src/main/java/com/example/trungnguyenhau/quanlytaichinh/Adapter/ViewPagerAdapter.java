package com.example.trungnguyenhau.quanlytaichinh.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.trungnguyenhau.quanlytaichinh.View.QuanLyTaiChinhCaNhan.Fragment.FragmentBangCanDoi;
import com.example.trungnguyenhau.quanlytaichinh.View.QuanLyTaiChinhCaNhan.Fragment.FragmentChiTieu;
import com.example.trungnguyenhau.quanlytaichinh.View.QuanLyTaiChinhCaNhan.Fragment.FragmentHienTai;
import com.example.trungnguyenhau.quanlytaichinh.View.QuanLyTaiChinhCaNhan.Fragment.FragmentThongKe1;
import com.example.trungnguyenhau.quanlytaichinh.View.QuanLyTaiChinhCaNhan.Fragment.FragmentThongKe2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TRUNGNGUYENHAU on 5/28/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter{

    List<Fragment> listFragment = new ArrayList<Fragment>();
    List<String> titleFragment = new ArrayList<String>();
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        listFragment.add(new FragmentChiTieu());
        listFragment.add(new FragmentHienTai());
        listFragment.add(new FragmentThongKe1());
        listFragment.add(new FragmentThongKe2());
        listFragment.add(new FragmentBangCanDoi());

        titleFragment.add("Loại Tài Chính Cá Nhân");
        titleFragment.add("Tài Chính Cá Nhân Hiện Tại");
        titleFragment.add("Thống Kê Biểu Đồ Trờn");
        titleFragment.add("Thống Kê Biểu Đồ Đường");
        titleFragment.add("Bảng Cân Đối Gia Đình");
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleFragment.get(position);
    }
}
