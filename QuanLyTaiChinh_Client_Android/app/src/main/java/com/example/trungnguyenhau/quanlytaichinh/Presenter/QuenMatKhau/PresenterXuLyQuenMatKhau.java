package com.example.trungnguyenhau.quanlytaichinh.Presenter.QuenMatKhau;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.example.trungnguyenhau.quanlytaichinh.View.QuenMatKhau.ViewXuLyQuenMatKhau;

import java.net.Socket;
import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.emitter.Emitter;

/**
 * Created by TRUNGNGUYENHAU on 6/15/2017.
 */

public class PresenterXuLyQuenMatKhau implements PresenterImpXuLyQuenMatKhau {
    io.socket.client.Socket socket;
    ViewXuLyQuenMatKhau viewXuLyQuenMatKhau;
    Context context;

    private ProgressDialog progressDialog;

    public PresenterXuLyQuenMatKhau(ViewXuLyQuenMatKhau viewXuLyQuenMatKhau, Context context) {
        this.viewXuLyQuenMatKhau = viewXuLyQuenMatKhau;
        this.context = context;

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        try {
            socket = IO.socket("http://192.168.0.101:3000");
            socket.connect();
            socket.on("forgotPassword", onForgotPassword);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void xuLyQuenMatKhau(String email, String password) {
        if (!email.trim().isEmpty() && !password.trim().isEmpty())
        {
            checkLogin(email,password);
        }
        else
        {
            Toast.makeText(context,
                    "Vui Lòng Nhập Đầy Đủ Thông Tin Cập Nhật", Toast.LENGTH_SHORT).show();
        }
    }

    Emitter.Listener onForgotPassword = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data = args[0].toString();
            if (data == "true")
            {
                viewXuLyQuenMatKhau.xuLyQuenMatKhauThanhCong();
            }
            else
            {
                viewXuLyQuenMatKhau.xuLyQuenMatKhauThatBai();
            }

            hideDialog();
        }
    };

    /*----- Socket.IO -----*/
    private void checkLogin(final String email, final String password) {
        socket.emit("forgotPassword", email, password);
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
