package com.example.trungnguyenhau.quanlytaichinh.View.QuanLyTaiChinhCaNhan.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.trungnguyenhau.quanlytaichinh.R;

/**
 * Created by TRUNGNGUYENHAU on 5/28/2017.
 */

public class FragmentThongKe1 extends Fragment implements View.OnClickListener{
    ImageButton imgBtnAdd, imgBtnEdit, imgBtnDelete;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_thongkebieudotron, container, false);

        imgBtnAdd = (ImageButton) view.findViewById(R.id.imagebutton_add);
        imgBtnAdd.setOnClickListener(this);
        imgBtnEdit = (ImageButton) view.findViewById(R.id.imagebutton_edit);
        imgBtnEdit.setOnClickListener(this);
        imgBtnDelete = (ImageButton) view.findViewById(R.id.imagebutton_delete);
        imgBtnDelete.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imagebutton_add:
                Toast.makeText(getActivity(), "Thêm Chi Tiêu Cá Nhân", Toast.LENGTH_LONG).show();
                break;
            case R.id.imagebutton_edit:
                Toast.makeText(getActivity(), "Sửa Chi Tiêu Cá Nhân", Toast.LENGTH_LONG).show();
                break;
            case R.id.imagebutton_delete:
                Toast.makeText(getActivity(), "Xóa Chi Tiêu Cá Nhân", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
