package com.example.project_1.NguoiDung;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project_1.Adapter.DSnguoiDungAdapter;
import com.example.project_1.DAO.NguoiDungDAO;
import com.example.project_1.HomeActivity;
import com.example.project_1.LoginActivity;
import com.example.project_1.Models.NguoiDung;
import com.example.project_1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class DSnguoiDungFragment extends Fragment {

    private ListView lv_ds;
    private NguoiDungDAO nguoiDungDAO;
    private List<NguoiDung> list = new ArrayList<>();
    private FloatingActionButton fbtn_add_user;
//    private HomeActivity homeActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_d_snguoi_dung, container, false);

        lv_ds = layout.findViewById(R.id.lv_user);
        fbtn_add_user = layout.findViewById(R.id.fbtn_add_user);

        nguoiDungDAO = new NguoiDungDAO( getActivity() );
        list = nguoiDungDAO.getAllNguoiDung();
        if (list.size() == 0){
            dialog_chung(1 , getActivity() , "Bạn hiện không có tài khoản nào Đăng Nhập !!!");
            getActivity().finish();
            startActivity(new Intent(getActivity() , LoginActivity.class));
        }
        DSnguoiDungAdapter adapter = new DSnguoiDungAdapter( getActivity() , list);
        lv_ds.setAdapter(adapter);

        lv_ds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edit_User(position);
            }
        });

        fbtn_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        return layout;
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//
//        if (context instanceof HomeActivity){
//            this.homeActivity = (HomeActivity) context;
//        }
//    }
    private void edit_User(Integer i) {
    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View view = inflater.inflate(R.layout.add_tk , null);

    TextInputLayout edt_ho_ten, edt_sdt, edt_tk, edt_mk, edt_lai_mk;
    Button btn_add_tk = view.findViewById(R.id.btn_add_tk);
    btn_add_tk.setText("Chỉnh Sửa");
    edt_ho_ten = view.findViewById(R.id.edt_add_ho_ten);
    edt_sdt = view.findViewById(R.id.edt_add_sdt);
    edt_tk = view.findViewById(R.id.edt_add_tk);
    edt_mk = view.findViewById(R.id.edt_add_mk);
    edt_lai_mk = view.findViewById(R.id.edt_add_nhap_lai_mk);
    Button btn_huy = view.findViewById(R.id.btn_cancel_dk);

    list.clear();
    list = nguoiDungDAO.getAllNguoiDung();
    edt_ho_ten.getEditText().setText( list.get(i).getHoTen() );
    edt_sdt.getEditText().setText( list.get(i).getPhone() );
    edt_tk.getEditText().setText( list.get(i).getUserName() );
    edt_tk.setEnabled(false);
    edt_mk.getEditText().setText( list.get(i).getPassword() );
    edt_lai_mk.getEditText().setText( list.get(i).getPassword() );

    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
    builder.setView(view).setTitle("Cập Nhật");

    androidx.appcompat.app.AlertDialog dialog = builder.create();

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
            String regex_khoang_trang = "[ ]+";
            String regex_ky_tu_trang = "[\\s]+";

            if (  ho_ten.isEmpty() ){
                dialog_chung(0, getActivity() , "Phải nhập Họ Tên");
            } else if (  sdt.isEmpty() ){
                dialog_chung(0, getActivity() , "Phải nhập Số Điện Thoại");
            } else if (  tk.isEmpty() ){
                dialog_chung(0, getActivity() , "Phải nhập Tài Khoản");
            } else if (  mk.isEmpty() ){
                dialog_chung(0, getActivity() , "Phải nhập Mật Khẩu");
            } else if (  lai_mk.isEmpty() ){
                dialog_chung(0, getActivity() , "Hãy nhập lại Mật Khẩu !!!");
            } else if (sdt.length() > 10) {
                dialog_chung(0, getActivity() , "Số Điện Thoại \nChỉ được nhập 10 số");
            } else if (sdt.length() < 10){
                dialog_chung(0 , getActivity()  , "Số điện thoại phải là dạng 10 số");
            } else if ( !sdt.matches(regex_sdt) ){
                dialog_chung(0 , getActivity() , "Số Điện Thoại phải nhập SỐ ...");
            }
            else if ( ho_ten.matches(regex_khoang_trang) || ho_ten.matches(regex_ky_tu_trang) ){
                dialog_chung(0 , getActivity() , "Họ Tên bạn phải nhập ký tự chữ hoặc số nha !");
            }  else if ( sdt.matches(regex_khoang_trang) || sdt.matches(regex_ky_tu_trang) ){
                dialog_chung(0 , getActivity() , "Số Điện Thoại bạn phải nhập ký tự số nha !");
            }  else if ( tk.matches(regex_khoang_trang) || tk.matches(regex_ky_tu_trang) ){
                dialog_chung(0 , getActivity() , "Tài Khoản bạn phải nhập ký tự chữ hoặc số nha !");
            }  else if ( mk.matches(regex_khoang_trang) || mk.matches(regex_ky_tu_trang) ){
                dialog_chung(0 , getActivity() , "Mật Khẩu bạn phải nhập ký tự chữ hoặc số nha !");
            }  else if ( lai_mk.matches(regex_khoang_trang) || lai_mk.matches(regex_ky_tu_trang) ){
                dialog_chung(0 , getActivity() , "Nhập Lại Mật Khẩu bạn phải nhập ký tự chữ hoặc số nha !");
            }  else if ( !mk.equals(lai_mk)) {
                dialog_chung(0, getActivity(), "Mật khẩu không khớp. Vui lòng Nhập lại !!!");
            } else {

                try {
                    NguoiDung user = new NguoiDung(
                            tk , mk , ho_ten , "" , sdt
                    );

                    if (nguoiDungDAO.updateNguoiDung(user) > 0) {

                        dialog_chung(1 , getActivity() , "Cập nhật thành công");
                        dialog.dismiss();

                        list.clear();
                        list = nguoiDungDAO.getAllNguoiDung();
                        DSnguoiDungAdapter adapter = new DSnguoiDungAdapter( getActivity() , list);
                        lv_ds.setAdapter(adapter);
                    } else {

                        dialog_chung(1 , getActivity() , "Chỉnh sửa thất bại");
                    }

                } catch (Exception ex) {
                    Log.e("Error Đăng Kí  : \t\t", ex.toString());
                }
            }

//            Log.e("show  : ", ho_ten + "\n" + sdt + "\n" + tk
//                    + "\n" + mk + "\n" + lai_mk + "\n\t\t\t\t\t" + sdt.matches(regex_sdt) );
        }
    });

    btn_huy.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();

            list.clear();
            list = nguoiDungDAO.getAllNguoiDung();
            DSnguoiDungAdapter adapter = new DSnguoiDungAdapter( getActivity() , list);
            lv_ds.setAdapter(adapter);
        }
    });

    dialog.show();
}

    private void dialog_chung(Integer so, Context context, String tb) {
        switch (so) {
            case 0:
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                builder.setTitle("Thông Báo").setMessage(tb);
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                androidx.appcompat.app.AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case 1:
                Toast.makeText(context, tb, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void addUser() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_tk , null);

        TextInputLayout edt_ho_ten, edt_sdt, edt_tk, edt_mk, edt_lai_mk;
        Button btn_add_tk = view.findViewById(R.id.btn_add_tk);
        edt_ho_ten = view.findViewById(R.id.edt_add_ho_ten);
        edt_sdt = view.findViewById(R.id.edt_add_sdt);
        edt_tk = view.findViewById(R.id.edt_add_tk);
        edt_mk = view.findViewById(R.id.edt_add_mk);
        edt_lai_mk = view.findViewById(R.id.edt_add_nhap_lai_mk);
        Button btn_huy = view.findViewById(R.id.btn_cancel_dk);

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
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
                String regex_khoang_trang = "[ ]+";
                String regex_ky_tu_trang = "[\\s]+";

                if (  ho_ten.isEmpty() ){
                    dialog_chung(0, getActivity(), "Phải nhập Họ Tên");
                } else if (  sdt.isEmpty() ){
                    dialog_chung(0, getActivity(), "Phải nhập Số Điện Thoại");
                } else if (  tk.isEmpty() ){
                    dialog_chung(0, getActivity(), "Phải nhập Tài Khoản");
                } else if (  mk.isEmpty() ){
                    dialog_chung(0, getActivity(), "Phải nhập Mật Khẩu");
                } else if (  lai_mk.isEmpty() ){
                    dialog_chung(0, getActivity(), "Hãy nhập lại Mật Khẩu !!!");
                } else if (sdt.length() > 10) {
                    dialog_chung(0, getActivity(), "Số Điện Thoại \nChỉ được nhập 10 số");
                } else if (sdt.length() < 10){
                    dialog_chung(0 , getActivity() , "Số điện thoại phải là dạng 10 số");
                } else if ( !sdt.matches(regex_sdt) ){
                    dialog_chung(0 , getActivity() , "Số Điện Thoại phải nhập SỐ ...");
                }
                else if ( ho_ten.matches(regex_khoang_trang) || ho_ten.matches(regex_ky_tu_trang) ){
                    dialog_chung(0 , getActivity() , "Họ Tên bạn phải nhập ký tự chữ hoặc số nha !");
                }  else if ( sdt.matches(regex_khoang_trang) || sdt.matches(regex_ky_tu_trang) ){
                    dialog_chung(0 , getActivity() , "Số Điện Thoại bạn phải nhập ký tự số nha !");
                }  else if ( tk.matches(regex_khoang_trang) || tk.matches(regex_ky_tu_trang) ){
                    dialog_chung(0 , getActivity() , "Tài Khoản bạn phải nhập ký tự chữ hoặc số nha !");
                }  else if ( mk.matches(regex_khoang_trang) || mk.matches(regex_ky_tu_trang) ){
                    dialog_chung(0 , getActivity() , "Mật Khẩu bạn phải nhập ký tự chữ hoặc số nha !");
                }  else if ( lai_mk.matches(regex_khoang_trang) || lai_mk.matches(regex_ky_tu_trang) ){
                    dialog_chung(0 , getActivity() , "Nhập Lại Mật Khẩu bạn phải nhập ký tự chữ hoặc số nha !");
                }  else if ( !mk.equals(lai_mk)) {
                    dialog_chung(0, getActivity(), "Mật khẩu không khớp. Vui lòng Nhập lại !!!");
                } else {

                    try {
                        NguoiDung user = new NguoiDung(
                                tk , mk , ho_ten , "" , sdt
                        );
                        user.setTongSoTien("0");

                        if (nguoiDungDAO.inserNguoiDung(user) > 0) {

                            dialog_chung(1 , getActivity() , "Thêm tài khoản Thành Công");
                            dialog.dismiss();

                            list.clear();
                            list = nguoiDungDAO.getAllNguoiDung();
                            DSnguoiDungAdapter adapter = new DSnguoiDungAdapter( getActivity() , list);
                            lv_ds.setAdapter(adapter);

                        } else if ( nguoiDungDAO.checkLogin(user.getUserName() , user.getPassword()) > 0){
                            dialog_chung(0 , getActivity() , "Tài Khoản đã tồn tại !\nVui lòng chọn tài khoản khác.");
                        } else {

                            dialog_chung(1 , getActivity() , "Đăng Kí Thất Bại");
                        }

                    } catch (Exception ex) {
                        Log.e("Error Đăng Kí  : \t\t", ex.toString());
                    }
                }

//                Log.e("show  : ", ho_ten + "\n" + sdt + "\n" + tk
//                        + "\n" + mk + "\n" + lai_mk + "\n\t\t\t\t\t" + sdt.matches(regex_sdt) );
            }
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                list.clear();
                list = nguoiDungDAO.getAllNguoiDung();
                DSnguoiDungAdapter adapter = new DSnguoiDungAdapter( getActivity() , list);
                lv_ds.setAdapter(adapter);
            }
        });

        dialog.show();
    }
}