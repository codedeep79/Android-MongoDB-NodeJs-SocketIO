package com.example.trungnguyenhau.quanlytaichinh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trungnguyenhau.quanlytaichinh.Adapter.ViewPagerAdapter;
import com.example.trungnguyenhau.quanlytaichinh.R;

import static android.view.KeyEvent.KEYCODE_BACK;

public class QuanLyTaiChinhCaNhan extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Let's display the progress in the activity title bar, like the
        // browser app does.
        getWindow().requestFeature(Window.FEATURE_PROGRESS);

        setContentView(R.layout.activity_quan_ly_tai_chinh_ca_nhan);
        addControls();
        addEvents();
    }

    private void addControls() {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Tài Chính Cá Nhân");
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                WebView webview = new WebView(QuanLyTaiChinhCaNhan.this);
                setContentView(webview);

                webview.getSettings().setJavaScriptEnabled(true);



                webview.setWebChromeClient(new WebChromeClient() {

                    public void onProgressChanged(WebView view, int progress) {
                        // Activities and WebViews measure progress with different scales.
                        // The progress meter will automatically disappear when we reach 100%
                        (QuanLyTaiChinhCaNhan.this).setTitle("Loading...");
                        (QuanLyTaiChinhCaNhan.this).setProgress(progress * 100);

                    }
                });


                webview.setWebViewClient(new WebViewClient() {
                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                        Toast.makeText(QuanLyTaiChinhCaNhan.this,
                                "Oh no! " + description, Toast.LENGTH_SHORT).show();
                    }
                });


                switch (item.getItemId())
                {
                    case R.id.itemTinTucTaiChinh:

                        webview.loadUrl("http://vneconomy.vn");
                        return true;
                    case R.id.itemTinTucThiTruong:
                        webview.loadUrl("http://vietstock.vn/");
                        return true;
                    case R.id.itemDuBaoThoiTiet:
                        webview.loadUrl("https://weather.com/weather/today/l/10.91,106.59?temp=c&par=google");
                        return true;
                }
                return true;
            }
        });


        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

    }



    private void addEvents() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);

        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*int countPress = 0;
        Intent intent = null;
        if (countPress >= 2)
        {
            intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            countPress = 0;
        }
        if (countPress < 2)
        {
            intent = new Intent(QuanLyTaiChinhCaNhan.this, QuanLyTaiChinhCaNhan.class);
            startActivity(intent);
            countPress++;
        }*/
    }
}
