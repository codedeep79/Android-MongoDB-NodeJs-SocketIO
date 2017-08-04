package com.example.trungnguyenhau.quanlytaichinh;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trungnguyenhau.quanlytaichinh.Presenter.DangNhap.PresenterXuLyDangNhap;
import com.example.trungnguyenhau.quanlytaichinh.View.DangNhap.ViewXuLyDangNhap;
import com.example.trungnguyenhau.quanlytaichinh.View.QuanLyTaiChinhCaNhan.Fragment.FragmentHienTai;
import com.example.trungnguyenhau.quanlytaichinh.View.QuenMatKhau.ViewXuLyQuenMatKhau;

public class MainActivity extends AppCompatActivity implements ViewXuLyDangNhap, View.OnClickListener{
    LinearLayout linearLayout;
    EditText edtUsername, edtPassword;
    CheckBox cbRememberPassword;
    Button btnLogin;
    TextView txtRegister, txtForgotPassword;

    PresenterXuLyDangNhap presenterXuLyDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();

    }


    private void addControls() {
        if (getIntent().getStringExtra("forgotPassword") != null)
        {
            Toast.makeText(MainActivity.this, getIntent().getStringExtra("forgotPassword").toString(), Toast.LENGTH_SHORT).show();
        }

        if (getIntent().getStringExtra("register") != null)
        {
            Toast.makeText(MainActivity.this, getIntent().getStringExtra("register").toString(), Toast.LENGTH_SHORT).show();
        }

        presenterXuLyDangNhap = new PresenterXuLyDangNhap(this, MainActivity.this);

        linearLayout = (LinearLayout) findViewById(R.id.layout_root);
        edtPassword = (EditText) findViewById(R.id.edittext_password);
        edtPassword.setText("admin");
        edtUsername = (EditText) findViewById(R.id.edittext_username);
        edtUsername.setText("admin@gmail.com");

        cbRememberPassword = (CheckBox) findViewById(R.id.checkbox_rememberpassword);

        btnLogin = (Button) findViewById(R.id.button_login);
        btnLogin.setOnClickListener(this);

        txtRegister = (TextView) findViewById(R.id.textview_register);
        txtRegister.setOnClickListener(this);
        txtForgotPassword = (TextView) findViewById(R.id.textview_forgotpassword);
        txtForgotPassword.setOnClickListener(this);

        float density = getResources().getDisplayMetrics().density;

        Drawable drawableUsername = getResources().getDrawable(R.drawable.user_icon);
        Drawable drawablePassword = getResources().getDrawable(R.drawable.user_password_icon);
        int width  = Math.round(24 * density);
        int height = Math.round(24 * density);

        drawableUsername.setBounds(0, 0, width, height);
        drawablePassword.setBounds(0, 0, width, height);
        edtUsername.setCompoundDrawables(drawableUsername, null, null, null);
        edtPassword.setCompoundDrawables(drawablePassword, null, null, null);
    }


    private void addEvents() {

    }


    @Override
    public void dangNhapThanhCong() {
        //Toast.makeText(MainActivity.this, "Đăng Nhập Thành Công", Toast.LENGTH_LONG).show();
        Intent intentQuanLyTaiChinhCaNhan  = new Intent(MainActivity.this, QuanLyTaiChinhCaNhan.class);
        startActivity(intentQuanLyTaiChinhCaNhan);
    }

    @Override
    public void dangNhapThatBai() {
        //Toast.makeText(MainActivity.this, "Đăng Nhập Thất Bại", Toast.LENGTH_LONG).show();
    }

    @Override
    public void quenMatKhau(String email) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_login:
                String tenNguoiDung = edtUsername.getText().toString();
                String matKhau = edtPassword.getText().toString();
                presenterXuLyDangNhap.kiemTraDangNhap(tenNguoiDung, matKhau);
                break;
            case R.id.textview_register:
                Intent intentDangKy = new Intent(MainActivity.this, DangKyActivity.class);
                startActivity(intentDangKy);
                break;
            case R.id.textview_forgotpassword:
                Intent intentForgotPassword = new Intent(MainActivity.this, QuenMatKhauActivity.class);
                startActivity(intentForgotPassword);
                break;
        }
    }


}
