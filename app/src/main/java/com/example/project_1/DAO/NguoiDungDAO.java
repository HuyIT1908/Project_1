package com.example.project_1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.project_1.Models.NguoiDung;
import com.example.project_1.SQLite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class NguoiDungDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    //    ten table
    public static final String TABLE_NAME = "NguoiDung";
    //    create table
    public static final String SQL_NGUOI_DUNG = "CREATE TABLE \"NguoiDung\" (\n" +
            "\t\"userName\"\tTEXT UNIQUE,\n" +
            "\t\"passWord\"\tTEXT NOT NULL,\n" +
            "\t\"tenND\"\tTEXT,\n" +
            "\t\"gioiTinh\"\tTEXT,\n" +
            "\t\"phone\"\tTEXT,\n" +
            "\t\"tongSoTien\"\tTEXT,\n" +
            "\tPRIMARY KEY(\"userName\")\n" +
            ");" ;

    public static final String TAG = "NguoiDungDAO";

    public NguoiDungDAO(Context context) {
//        tao doi tuong va lay quyen doc ghi
        try {
            dbHelper = new DatabaseHelper(context);
            db = dbHelper.getWritableDatabase();
        } catch (Exception ex){
            Log.e("\t\t line 34\t" + TAG , ex.toString() );
        }
    }

    //insert
    public int inserNguoiDung(NguoiDung nd) {
//        them nguoi dung vao db
        ContentValues values = new ContentValues();
        values.put("userName", nd.getUserName());
        values.put("passWord", nd.getPassword());

        values.put("tenND", nd.getHoTen());
        values.put("gioiTinh", nd.getGioiTinh());
        values.put("phone", nd.getPhone());
        values.put("tongSoTien", nd.getTongSoTien());
//        insert db
        try {
            if ( checkLogin(nd.getUserName() , nd.getPassword()) > 0 ){
                return -1;
            } else if ( db.insert(TABLE_NAME, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG + "Error db  :", ex.toString());
        }
        return 1;
    }

    //getAll
    public List<NguoiDung> getAllNguoiDung() {
//        get all data for to list
        List<NguoiDung> dsNguoiDung = new ArrayList<>();

        Cursor c = null;
        try {
            c = db.query(TABLE_NAME, null, null, null, null, null, null);
//            c = db.rawQuery("SELECT * FROM NguoiDung" , null);
//            Log.e("\t\t" + TAG , c.toString() );
        } catch (Exception ex){
            Log.e(TAG + "\tline 69" , ex.toString() +"\t" + c);
        }
        if (c != null){
            c.moveToFirst();
            while (c.isAfterLast() == false) {

                NguoiDung ee = new NguoiDung();
                ee.setUserName(c.getString(0));
                ee.setPassword(c.getString(1));

                ee.setHoTen(c.getString(2));
                ee.setGioiTinh(c.getString(3));
                ee.setPhone(c.getString(4));
                ee.setTongSoTien( c.getString(5) );
//            get data add list
                dsNguoiDung.add(ee);
                Log.d(TAG + "//=====\t\t\t\t", ee.toString());
                c.moveToNext();
            }
            c.close();
        }
        return dsNguoiDung;
    }

    //update
    public int updateNguoiDung(NguoiDung nd) {
//        update nguoi dung according to the username
        ContentValues values = new ContentValues();
        values.put("userName", nd.getUserName());
        values.put("passWord", nd.getPassword());

        values.put("tenND", nd.getHoTen());
        values.put("gioiTinh", nd.getGioiTinh());
        values.put("phone", nd.getPhone());
        values.put("tongSoTien", nd.getTongSoTien());
//        update for to database
        int result = db.update(TABLE_NAME, values, "userName=?", new
                String[]{nd.getUserName()});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    //  change Password
    public int changePasswordNguoiDung(NguoiDung nd) {
//        update Password according to the username
        ContentValues values = new ContentValues();
        values.put("username", nd.getUserName());
        values.put("password", nd.getPassword());
        int result = db.update(TABLE_NAME, values, "userName=?", new
                String[]{nd.getUserName()});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    //  update info for to nguoi dung
    public int updateInfoNguoiDung(NguoiDung nd) {
        ContentValues values = new ContentValues();
        values.put("userName", nd.getUserName());
        values.put("passWord", nd.getPassword());

        values.put("tenND", nd.getHoTen());
        values.put("gioiTinh", nd.getGioiTinh());
        values.put("phone", nd.getPhone());
        values.put("tongSoTien", nd.getTongSoTien());

        int result = db.update(TABLE_NAME, values, "userName=?", new
                String[]{nd.getUserName()});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    //    delete    account
    public int deleteNguoiDungByID(String username) {
        int result = db.delete(TABLE_NAME, "userName=?", new String[]{username});

        if (result == 0)
            return -1;
        return 1;
    }

    //check login
    public int checkLogin(String username, String password) {
        int result = 0;
//        get all data for to list
        List<NguoiDung> dsNguoiDung = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {

            NguoiDung ee = new NguoiDung();
            ee.setUserName(c.getString(0));
            ee.setPassword(c.getString(1));

            ee.setHoTen(c.getString(2));
            ee.setGioiTinh(c.getString(3));
            ee.setPhone(c.getString(4));

//            if ( username.equals(c.getString(0)) ){
//                c.close();
//                return 1;
//            } else
            if (  username.equals(c.getString(0))
                    && password.equals(c.getString(1)) ){
                c.close();
//                Log.e("-kiem tra login -------" , "----------thanh cong roi nha");
                return 1;
            }
//            get data add list
            dsNguoiDung.add(ee);
            Log.d(TAG + "//===== \t\t","Error function checkLogin" + ee.toString());
            c.moveToNext();
        }
        c.close();


//        int result = db.delete(TABLE_NAME, "username=? AND password=?", new String[]{username, password});
        if (result == 0)
            return -1;
        return 1;
    }
}
