package com.example.project_1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.project_1.DAO.NguoiDungDAO;
import com.example.project_1.Models.NguoiDung;
import com.google.android.material.textfield.TextInputLayout;

public class Sign_UpActivity extends AppCompatActivity {
    TextInputLayout edt_ho_ten, edt_sdt, edt_tk, edt_mk, edt_lai_mk;
    Button btn_SignUp , btn_cancel_sihnUp;
    Context context = Sign_UpActivity.this;
    NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        setTitle("Đăng Kí");

        nguoiDungDAO = new NguoiDungDAO(context);
        edt_ho_ten = findViewById(R.id.edt_signup_ho_ten);
        edt_sdt = findViewById(R.id.edt_signup_sdt);
        edt_tk = findViewById(R.id.edt_signup_tk);
        edt_mk = findViewById(R.id.edt_signup_mk);
        edt_lai_mk = findViewById(R.id.edt_signup_nhap_lai_mk);
        btn_SignUp = findViewById(R.id.btn_signup);
        btn_cancel_sihnUp = findViewById(R.id.btn_cancel_sihnUp);

        btn_cancel_sihnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void sign_UP(View view) {
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
                    rememberUser(tk , mk , true);
                    startActivity(new Intent(context , LoginActivity.class));
                    finish();
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

    private void rememberUser(String tk, String mk, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();

        if (!status) {
            //xoa tinh trang luu tru truoc do
            edit.clear();
        } else {
            //luu du lieu
            edit.putString("USERNAME", tk);
            edit.putString("PASSWORD", mk);
            edit.putBoolean("REMEMBER", status);
//            Toast.makeText(getApplicationContext(), "Đã nhớ tài khoản",
//                    Toast.LENGTH_SHORT).show();

        }
        String thu = null;
        //luu lai toan bo
        if (edit.commit()) {
            thu = "Lưu tài khoản thành công";
//            Toast.makeText(getApplicationContext(), "Lưu tài khoản thành công",
//                    Toast.LENGTH_SHORT).show();
        }
        Log.e("-----------------------", String.valueOf(status) + "\n\t" + tk + "\n\t"
                + mk + "\n\t\t\t" + thu);
    }

    private void get_remember_User(TextInputLayout edt_tk, TextInputLayout edt_mk, CheckBox chkRememberPass) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String tk = pref.getString("USERNAME", null);
        String mk = pref.getString("PASSWORD", null);
        boolean nho = pref.getBoolean("REMEMBER", true);
        if (tk != null && mk != null) {
            edt_tk.getEditText().setText(String.valueOf(tk));
            edt_mk.getEditText().setText(String.valueOf(mk));
            chkRememberPass.setChecked(true);
//            Log.e("-----------login test", String.valueOf(nho) + "\t" + tk + "\t\t" + mk);
        }
    }
}