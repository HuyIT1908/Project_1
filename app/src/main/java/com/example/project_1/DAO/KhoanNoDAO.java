package com.example.project_1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.project_1.Models.KhoanNo;
import com.example.project_1.SQLite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class KhoanNoDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    //    ten table
    public static final String TABLE_NAME = "KhoanNo";
    //    create table
    public static final String SQL_Khoan_No = "CREATE TABLE \"KhoanNo\" (\n" +
            "\t\"maKhoanNo\"\tTEXT,\n" +
            "\t\"userName\"\tTEXT,\n" +
            "\t\"soTienNo\"\tTEXT,\n" +
            "\t\"ngayNo\"\tTEXT,\n" +
            "\t\"ngayTra\"\tTEXT,\n" +
            "\t\"chuThich\"\tTEXT,\n" +
            "\t\"status\"\tTEXT,\n" +
            "\tFOREIGN KEY(\"userName\") REFERENCES \"NguoiDung\"(\"userName\"),\n" +
            "\tPRIMARY KEY(\"maKhoanNo\")\n" +
            ");" ;

    public static final String TAG = "KhoanNoDAO";

    public KhoanNoDAO(Context context) {
//        tao doi tuong va lay quyen doc ghi
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public int insert_Khoan_no( KhoanNo khoanNo ) {
//        them nguoi dung vao db
        ContentValues values = new ContentValues();
        values.put("maKhoanNo", khoanNo.getMaKhoanNo());
        values.put("userName", khoanNo.getUserName());
        values.put("soTienNo", khoanNo.getSoTienNo());
        values.put("ngayNo", khoanNo.getNgayNo());
        values.put("ngayTra", khoanNo.getNgayTra());
        values.put("chuThich", khoanNo.getChuThich());
        values.put("status", khoanNo.getStatus() );

//        insert db
        try {
            if ( check_Khoan_No(khoanNo) ){
                return -1;
            } else if (db.insert(TABLE_NAME, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG + "Error db  :", ex.toString());
        }
        return 1;
    }

    //getAll
    public List<KhoanNo> getAll_Khoan_No() {
//        get all data for to list
        List<KhoanNo> list_Khoan_No = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (c != null){
            c.moveToFirst();
            while (c.isAfterLast() == false) {

                KhoanNo ee = new KhoanNo();
                ee.setMaKhoanNo(c.getString(0));
                ee.setUserName(c.getString(1));

                ee.setSoTienNo(c.getString(2));
                ee.setNgayNo(c.getString(3));
                ee.setNgayTra(c.getString(4));
                ee.setChuThich(c.getString(5));
                ee.setStatus(c.getString(6) );
//            get data add list
                list_Khoan_No.add(ee);
                Log.d(TAG + "//=====\t\t\t\t", ee.toString());
                c.moveToNext();
            }
            c.close();
        }
        return list_Khoan_No;
    }

    //update
    public int update_Khoan_NO( KhoanNo khoanNo ) {
//        update nguoi dung according to the username
        ContentValues values = new ContentValues();
        values.put("maKhoanNo", khoanNo.getMaKhoanNo());
        values.put("userName", khoanNo.getUserName());
        values.put("soTienNo", khoanNo.getSoTienNo());
        values.put("ngayNo", khoanNo.getNgayNo());
        values.put("ngayTra", khoanNo.getNgayTra());
        values.put("chuThich", khoanNo.getChuThich());
        values.put("status", khoanNo.getStatus() );

//        update for to database
        int result = db.update(TABLE_NAME, values, "maKhoanNo=?", new
                String[]{ khoanNo.getMaKhoanNo() });
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    //    delete    account
    public int delete_Khoan_No_By_ID(String maKhoanNo) {
        int result = db.delete(TABLE_NAME, "maKhoanNo=?", new String[]{ maKhoanNo });

        if (result == 0)
            return -1;
        return 1;
    }

    public boolean check_Khoan_No(KhoanNo khoanNo){
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (c != null){
            c.moveToFirst();
            while (c.isAfterLast() == false) {

                KhoanNo ee = new KhoanNo();
                ee.setMaKhoanNo(c.getString(0));
                ee.setUserName(c.getString(1));

                ee.setSoTienNo(c.getString(2));
                ee.setNgayNo(c.getString(3));
                ee.setNgayTra(c.getString(4));
                ee.setChuThich(c.getString(5));
                ee.setStatus(c.getString(6));

                if ( khoanNo.getMaKhoanNo().equals( ee.getMaKhoanNo() ) ){
                    c.close();
                    return true;
                }
                Log.d(TAG + "//=====\t\t\t\t", ee.toString());
                c.moveToNext();
            }
            c.close();
        }
        return false;
    }
}
