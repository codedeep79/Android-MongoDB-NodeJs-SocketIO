package com.example.trungnguyenhau.quanlytaichinh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.trungnguyenhau.quanlytaichinh.Presenter.DangKy.PresenterImpXuLyDangKy;
import com.example.trungnguyenhau.quanlytaichinh.Presenter.DangKy.PresenterXuLyDangKy;
import com.example.trungnguyenhau.quanlytaichinh.View.DangKy.ViewXuLyDangKy;

import java.util.ArrayList;
import java.util.List;

public class DangKyActivity extends AppCompatActivity implements ViewXuLyDangKy, View.OnClickListener{

    EditText edtHo, edtTen, edtEmail, edtMatKhau;
    Spinner spinnerNgay, spinnerThang, spinnerNam;
    Button btnDangKy;

    private List<String> listNgay, listThang, listNam;
    String ngay, thang, nam;
    ArrayAdapter<String> adapterNgay, adapterThang, adapterNam;
    PresenterXuLyDangKy presenterXuLyDangKy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        addControls();
        addEvents();
    }

    private void addControls() {
        presenterXuLyDangKy = new PresenterXuLyDangKy(this, DangKyActivity.this);

        btnDangKy = (Button) findViewById(R.id.button_dangky);
        btnDangKy.setOnClickListener(this);

        edtHo = (EditText) findViewById(R.id.edittext_ho);
        edtTen = (EditText) findViewById(R.id.edittext_ten);
        edtEmail = (EditText) findViewById(R.id.edittext_email);
        edtMatKhau = (EditText) findViewById(R.id.edittext_matkhau);

        spinnerNgay = (Spinner) findViewById(R.id.spinner_ngay);
        spinnerThang = (Spinner) findViewById(R.id.spinner_thang);
        spinnerNam = (Spinner) findViewById(R.id.spinner_nam);

        listNgay = new ArrayList<>();
        for(int i = 1;i <= 31; ++i)
        {
            listNgay.add(i + "");
        }

        listThang = new ArrayList<>();
        for(int i = 1;i <= 12; ++i)
        {
            listThang.add(i + "");
        }

        listNam = new ArrayList<>();
        for(int i = 1910;i <= 2017; ++i)
        {
            listNam.add(i + "");
        }


    }

    private void addEvents() {
        adapterNgay = new ArrayAdapter<String>(DangKyActivity.this, android.R.layout.simple_spinner_item, listNgay);
        adapterNgay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNgay.setAdapter(adapterNgay);
        spinnerNgay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ngay = spinnerNgay.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapterThang = new ArrayAdapter<String>(DangKyActivity.this, android.R.layout.simple_spinner_item, listThang);
        adapterThang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerThang.setAdapter(adapterThang);
        spinnerThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                thang = spinnerThang.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapterNam = new ArrayAdapter<String>(DangKyActivity.this, android.R.layout.simple_spinner_item, listNam);
        adapterNam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNam.setAdapter(adapterNam);
        spinnerNam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nam = spinnerNam.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_dangky:
                String lastname = edtHo.getText().toString();
                String firstname = edtTen.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtMatKhau.getText().toString();
                String ngaySinh = ngay + "/" + thang + "/" + nam;
                presenterXuLyDangKy.dangKyThanhCong(lastname, firstname, email, password, ngaySinh);

                break;
        }
    }

    @Override
    public void dangKyThanhCong() {
        Intent intent = new Intent(DangKyActivity.this, MainActivity.class);
        intent.putExtra("register", "Đăng Ký Thành Công. Hãy Đăng Nhập Email và Mật Khẩu Của Bạn");
        startActivity(intent);
    }

    @Override
    public void dangKyThatBai() {

    }
}
