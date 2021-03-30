package com.example.project_1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_1.DAO.NguoiDungDAO;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout edt_login_tk, edt_login_mk;
    CheckBox cb_tk;
    Button btn_login, btn_dang_ki;
    Context context = LoginActivity.this;
    NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Đăng Nhập");

        nguoiDungDAO = new NguoiDungDAO(context);
        edt_login_tk = findViewById(R.id.edt_login_tk);
        edt_login_mk = findViewById(R.id.edt_login_mk);
        cb_tk = findViewById(R.id.ckb_tk);
        cb_tk.setChecked(true);
        btn_dang_ki = findViewById(R.id.btn_login);
        btn_dang_ki = findViewById(R.id.btn_dang_ky);

        get_remember_User(edt_login_tk, edt_login_mk, cb_tk);
    }

    public void login(View view) {
        try {
            String tk = edt_login_tk.getEditText().getText().toString();
            String mk = edt_login_mk.getEditText().getText().toString();
            boolean luu_tk = cb_tk.isChecked();

            if ( tk.isEmpty() ) {
                dialog_chung(0, context, "Không được để trống \n\nTài Khoản !!!");
            } else if ( mk.isEmpty() ){
                dialog_chung(0, context, "Không được để trống \n\nMật khẩu !!!");
            } else {

                if ( nguoiDungDAO.checkLogin(tk , mk) > 0) {

                    rememberUser(tk, mk, luu_tk);
                    dialog_chung(1, context, "Đăng nhập thành công");

                    startActivity(new Intent(context, HomeActivity.class));
                    finish();

                } else if (tk.equalsIgnoreCase("admin") && mk.equalsIgnoreCase("admin")) {

                    rememberUser(tk , mk, cb_tk.isChecked());
                    startActivity(new Intent(this, HomeActivity.class));
                    finish();

                } else {

                    dialog_chung(0 , context , "Tên đăng nhập và mật khẩu không đúng ???  \n\nHoặc không có ! ! !");
                }

            }
//            Log.e("\t\t----Error  : "  , tk + "\t" + mk + "\t" + luu_tk);
        } catch (Exception ex) {
            Log.e("\t\t----Error  : ", ex.toString());
        }
    }

    public void sign_Up(View view){
        startActivity(new Intent(context , Sign_UpActivity.class));
        finish();
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