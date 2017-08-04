package com.example.trungnguyenhau.quanlytaichinh.Presenter.DangKy;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.example.trungnguyenhau.quanlytaichinh.View.DangKy.ViewXuLyDangKy;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by TRUNGNGUYENHAU on 5/28/2017.
 */

public class PresenterXuLyDangKy implements PresenterImpXuLyDangKy {
    ViewXuLyDangKy viewXuLyDangKy;
    Context context;
    private ProgressDialog progressDialog;
    private Socket _socket;

    public PresenterXuLyDangKy(ViewXuLyDangKy viewXuLyDangKy, Context context) {
        this.viewXuLyDangKy = viewXuLyDangKy;
        this.context = context;

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        try {
            _socket = IO.socket("http://192.168.0.101:3000");
            _socket.connect();
            _socket.on("register", onRegister);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void dangKyThanhCong(String ho, String ten, String email, String matKhau, String ngaySinh) {
        if (!ho.isEmpty() && !ten.isEmpty() && !email.isEmpty() &&
                !matKhau.isEmpty() && !ngaySinh.isEmpty()) {
            checkRegister(ho, ten, email, matKhau, ngaySinh);
        }
        else
        {
        Toast.makeText(context, "Đăng Ký Bị Lỗi Hoặc Bị Trùng Email. Vui Lòng Nhập Lại",
                Toast.LENGTH_SHORT).show();
        }
    }

    Emitter.Listener onRegister = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data = args[0].toString();
            if (data == "true")
            {
                viewXuLyDangKy.dangKyThanhCong();
            }
            else
            {
                viewXuLyDangKy.dangKyThatBai();
            }

            hideDialog();
        }
    };

    /*----- Socket.IO -----*/
    private void checkRegister(final String lastname, final String firstname,
                               final String email, final String password, final String dateOfBirth) {
            _socket.emit("register", lastname, firstname, email, password, dateOfBirth);
    }

    private void showDialog(){
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
