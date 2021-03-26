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
                    , new DSnguoiDungFragment() ).commit();
            navigationView.setCheckedItem(R.id.nav_thu);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_thu:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_contener, new ThuFragment() ).commit();
                break;
//            case R.id.nav_chi:
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame_contener, new FragmentChi()).commit();
//                break;
//            case R.id.nav_thong_ke:
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame_contener, new FragmentThongKe()).commit();
//                break;
            case R.id.nav_list_user:
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ds_user , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ( item.getItemId() ){
            case R.id.ds_user_menu:
                addUser();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addUser() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_tk , null);

        TextInputLayout edt_ho_ten, edt_sdt, edt_tk, edt_mk, edt_lai_mk;
        Button btn_add_tk = view.findViewById(R.id.btn_add_tk);
        edt_ho_ten = view.findViewById(R.id.edt_add_ho_ten);
        edt_sdt = view.findViewById(R.id.edt_add_sdt);
        edt_tk = view.findViewById(R.id.edt_add_tk);
        edt_mk = view.findViewById(R.id.edt_add_mk);
        edt_lai_mk = view.findViewById(R.id.edt_add_nhap_lai_mk);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view).setTitle("Thêm tài khoản");

        AlertDialog dialog = builder.create();

        btn_add_tk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ho_ten, sdt, tk, mk, lai_mk;
                ho_ten = edt_ho_ten.getEditText().getText().toString();
                sdt = edt_sdt.getEditText().getText().toString();
                tk = edt_tk.getEditText().getText().toString();
                mk = edt_mk.getEditText().getText().toString();
                lai_mk = edt_lai_mk.getEditText().getText().toString();
                String regex_sdt = "[0-9]{9,10}";

                if (  ho_ten.isEmpty() ){
                    dialog_chung(0, context, "Phải nhập Họ Tên");
                } else if (  sdt.isEmpty() ){
                    dialog_chung(0, context, "Phải nhập Số Điện Thoại");
                } else if (  tk.isEmpty() ){
                    dialog_chung(0, context, "Phải nhập Tài Khoản");
                } else if (  mk.isEmpty() ){
                    dialog_chung(0, context, "Phải nhập Mật Khẩu");
                } else if (  lai_mk.isEmpty() ){
                    dialog_chung(0, context, "Hãy nhập lại Mật Khẩu !!!");
                } else if (sdt.length() > 10) {
                    dialog_chung(0, context, "Số Điện Thoại \nChỉ được nhập 10 số");
                } else if ( !mk.equals(lai_mk)) {
                    dialog_chung(0, context, "Mật khẩu không khớp. Vui lòng Nhập lại !!!");
                } else if (sdt.length() < 10){
                    dialog_chung(0 , context , "Số điện thoại phải là dạng 10 số");
                } else if ( !sdt.matches(regex_sdt) ){
                    dialog_chung(0 , context , "Số Điện Thoại phải nhập SỐ ...");
                } else {

                    try {
                        NguoiDung user = new NguoiDung(
                                tk , mk , ho_ten , "" , sdt
                        );

                        if (nguoiDungDAO.inserNguoiDung(user) > 0) {

                            dialog_chung(1 , context , "Đăng Kí Thành Công");
                            dialog.dismiss();

                            DSnguoiDungFragment fragment = new DSnguoiDungFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_contener
                                    , fragment ).commit();
                            navigationView.setCheckedItem(R.id.nav_list_user);

                        } else if ( nguoiDungDAO.checkLogin(user.getUserName() , user.getPassword()) > 0){
                            dialog_chung(0 , context , "Tài Khoản đã tồn tại !\nVui lòng chọn tài khoản khác.");
                        } else {

                            dialog_chung(1 , context , "Đăng Kí Thất Bại");
                        }

                    } catch (Exception ex) {
                        Log.e("Error Đăng Kí  : \t\t", ex.toString());
                    }
                }

                Log.e("show  : ", ho_ten + "\n" + sdt + "\n" + tk
                        + "\n" + mk + "\n" + lai_mk + "\n\t\t\t\t\t" + sdt.matches(regex_sdt) );
            }
        });

        dialog.show();
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