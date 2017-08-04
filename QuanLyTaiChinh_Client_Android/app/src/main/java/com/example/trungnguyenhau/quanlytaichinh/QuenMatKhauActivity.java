package com.example.trungnguyenhau.quanlytaichinh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trungnguyenhau.quanlytaichinh.Presenter.QuenMatKhau.PresenterXuLyQuenMatKhau;
import com.example.trungnguyenhau.quanlytaichinh.View.QuenMatKhau.ViewXuLyQuenMatKhau;

public class QuenMatKhauActivity extends AppCompatActivity implements ViewXuLyQuenMatKhau, View.OnClickListener {
    EditText edtEmail, edtNewPassword;
    Button btnGetNewPassword, btnCancel;
    PresenterXuLyQuenMatKhau presenterXuLyQuenMatKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        addControls();
        addEvents();
    }

    private void addControls() {
        presenterXuLyQuenMatKhau = new PresenterXuLyQuenMatKhau(this, QuenMatKhauActivity.this);

        edtEmail = (EditText) findViewById(R.id.edittext_email);
        edtNewPassword = (EditText) findViewById(R.id.edittext_new_password);
        btnCancel = (Button) findViewById(R.id.button_cancel);
        btnCancel.setOnClickListener(this);
        btnGetNewPassword = (Button) findViewById(R.id.button_laylaimatkhau);
        btnGetNewPassword.setOnClickListener(this);
    }

    private void addEvents() {

    }

    @Override
    public void xuLyQuenMatKhauThanhCong() {
        Intent intent = new Intent(QuenMatKhauActivity.this, MainActivity.class);
        intent.putExtra("forgotPassword", "Cập Nhật Thành Công. Quay Về Đăng Nhập");
        startActivity(intent);
    }

    @Override
    public void xuLyQuenMatKhauThatBai() {
        Intent intent = new Intent(QuenMatKhauActivity.this, MainActivity.class);
        intent.putExtra("forgotPassword", "Cập Nhật Mật Khẩu Bị Lỗi. Quay Về Đăng Nhập");
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button_cancel:
                Intent intent = new Intent(QuenMatKhauActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.button_laylaimatkhau:
                String email = edtEmail.getText().toString().trim();
                String password = edtNewPassword.getText().toString().trim();
                presenterXuLyQuenMatKhau.xuLyQuenMatKhau(email, password);

                break;
        }
    }
}
