package com.example.project_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_1.Chi.ChiFragment;
import com.example.project_1.DAO.NguoiDungDAO;
import com.example.project_1.Ke_Hoach_chi.KhChiFragment;
import com.example.project_1.KhoanNo.KhoanNoFragment;
import com.example.project_1.Models.NguoiDung;
import com.example.project_1.NguoiDung.DSnguoiDungFragment;
import com.example.project_1.ThongKe.ThongKeFragment;
import com.example.project_1.Thu.ThuFragment;
import com.example.project_1.TietKiem.TietKiemFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    Context context = HomeActivity.this;
    NguoiDungDAO nguoiDungDAO;
    NavigationView navigationView;
    List<NguoiDung> nguoiDungList;
    TextView tv_so_tien;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nguoiDungDAO = new NguoiDungDAO(context);
        nguoiDungList = nguoiDungDAO.getAllNguoiDung();

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) context);

        View mHeaderView = navigationView.getHeaderView(0);
        TextView tv_chao = mHeaderView.findViewById(R.id.tv_header_chao);
        String tk = get_remember_User("Xin chào :  ");
        user = tk;
        tv_chao.setText("Xin Chào :  " + tk);
        tv_so_tien = mHeaderView.findViewById(R.id.tv_header_2);
        tv_so_tien.setText("Số tiền :  " + nguoiDungList.get( get_value(nguoiDungList , tk)).getTongSoTien() );

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
                    , new ThongKeFragment() ).commit();
            navigationView.setCheckedItem(R.id.nav_thong_ke);
            setTitle("Thống Kê");
        }

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                nguoiDungList.clear();
                nguoiDungList = nguoiDungDAO.getAllNguoiDung();
                tv_so_tien.setText("Số tiền :  " + nguoiDungList.get( get_value(nguoiDungList , tk)).getTongSoTien() );
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
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
            case R.id.nav_ke_hoach:
                setTitle("Kế Hoạch Chi");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_contener, new KhChiFragment() ).commit();
                break;
            case R.id.nav_khoan_no:
                setTitle("Khoản Vay");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_contener, new KhoanNoFragment() ).commit();
                break;
            case R.id.nav_tiet_kiem:
                setTitle("Tiết Kiệm");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_contener, new TietKiemFragment() ).commit();
                break;
            case R.id.nav_list_user:
                setTitle("Quản Lí Tài Khoản");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_contener, new DSnguoiDungFragment() ).commit();
                break;
            case R.id.nav_thong_ke:
                setTitle("Thống Kê");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_contener, new ThongKeFragment() ).commit();
                break;
            case R.id.nav_logout:
                dialog_chung(1 , context , "Bạn đã đăng xuất !!!");
                startActivity(new Intent(context , LoginActivity.class));
                finish();
                break;

            case R.id.nav_thoat:
                finish();
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
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case 1:
                Toast.makeText(context, tb, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private String get_remember_User(String s) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String tk = pref.getString("USERNAME", null);
        String mk = pref.getString("PASSWORD", null);
        boolean nho = pref.getBoolean("REMEMBER", true);
        if (tk != null && mk != null) {
//            tv.setText("Xin Chào :  " + tk);
//            Log.e("-----------login test", String.valueOf(nho) + "\t" + tk + "\t\t" + mk);
        }
        return tk;
    }

    private Integer get_value(List<NguoiDung> list , String tk){
        for (int i = 0; i < list.size(); i++) {
            if ( list.get(i).getUserName().equals( tk ) ){
                return i;
            }
        }
        return 0;
    }
}