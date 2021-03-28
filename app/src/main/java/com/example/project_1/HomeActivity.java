package com.example.project_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_1.Chi.ChiFragment;
import com.example.project_1.DAO.NguoiDungDAO;
import com.example.project_1.Models.NguoiDung;
import com.example.project_1.NguoiDung.DSnguoiDungFragment;
import com.example.project_1.Thu.ThuFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    Context context = HomeActivity.this;
    NguoiDungDAO nguoiDungDAO;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nguoiDungDAO = new NguoiDungDAO(context);
//        Log.e( nguoiDungDAO.getAllNguoiDung().toString() , "\n!!!home 37 nha");
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) context);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_contener
                    , new ChiFragment() ).commit();
            navigationView.setCheckedItem(R.id.nav_chi);
            setTitle("Khoản Chi");
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_thu:
                setTitle("Khoản Thu");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_contener, new ThuFragment() ).commit();
                break;
            case R.id.nav_chi:
                setTitle("Khoản Chi");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_contener, new ChiFragment() ).commit();
                break;
//            case R.id.nav_thong_ke:
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame_contener, new FragmentThongKe()).commit();
//                break;
            case R.id.nav_list_user:
                setTitle("Quản Lí Tài Khoản");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_contener, new DSnguoiDungFragment()).commit();
                break;
            case R.id.nav_thoat:
                System.exit(0);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }


    private void dialog_chung(Integer so, Context context, String tb) {
        switch (so) {
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông Báo").setMessage(tb);
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case 1:
                Toast.makeText(context, tb, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}