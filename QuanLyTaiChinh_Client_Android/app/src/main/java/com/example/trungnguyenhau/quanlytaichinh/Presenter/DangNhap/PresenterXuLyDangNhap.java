package com.example.trungnguyenhau.quanlytaichinh.Presenter.DangNhap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.trungnguyenhau.quanlytaichinh.MainActivity;
import com.example.trungnguyenhau.quanlytaichinh.QuanLyTaiChinhCaNhan;
import com.example.trungnguyenhau.quanlytaichinh.View.DangNhap.ViewXuLyDangNhap;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by TRUNGNGUYENHAU on 5/28/2017.
 */

public class PresenterXuLyDangNhap extends AppCompatActivity implements PresenterImpXuLyDangNhap {

    private ViewXuLyDangNhap viewXuLyDangNhap;
    private Context context;

    private ProgressDialog progressDialog;
    private Socket _socket;

    String notify = "Information of your connect";

    public PresenterXuLyDangNhap(ViewXuLyDangNhap viewXuLyDangNhap, Context context) {
        this.viewXuLyDangNhap = viewXuLyDangNhap;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        try {
            _socket = IO.socket("http://192.168.0.101:3000");
            _socket.connect();
            _socket.on("login", onLogin);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void kiemTraDangNhap(String tenDangNhap, String matKhau) {
        // Gọi tới model để lấy dữ liệu

        if (!tenDangNhap.trim().isEmpty() && !matKhau.trim().isEmpty()) {
            showDialog();
            checkLogin(tenDangNhap, matKhau);

            // if wait about 8s, not response you can check null
            if (progressDialog.isShowing() ) {
                checkLogin(null, null);
            }

        }


    }

    Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data = args[0].toString();
            if (data == "true")
            {
                viewXuLyDangNhap.dangNhapThanhCong();
            }
            else
            {
               viewXuLyDangNhap.dangNhapThatBai();
            }

            hideDialog();
        }
    };

    /*----- Socket.IO -----*/
    private void checkLogin(final String email, final String password) {
        _socket.emit("login", email, password);
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
