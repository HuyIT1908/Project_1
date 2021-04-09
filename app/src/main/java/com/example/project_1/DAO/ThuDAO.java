package com.example.project_1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.project_1.Models.Thu;
import com.example.project_1.SQLite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ThuDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    //    ten table
    public static final String TABLE_NAME = "Thu";
    //    create table
    public static final String SQL_Thu = "CREATE TABLE \"Thu\" (\n" +
            "\t\"maThuNhap\"\tTEXT,\n" +
            "\t\"userName\"\tTEXT,\n" +
            "\t\"soTienThu\"\tTEXT,\n" +
            "\t\"ngayNhanTien\"\tTEXT,\n" +
            "\t\"chuThich\"\tTEXT,\n" +
            "\tFOREIGN KEY(\"userName\") REFERENCES \"NguoiDung\"(\"userName\"),\n" +
            "\tPRIMARY KEY(\"maThuNhap\")\n" +
            ");" ;

    public static final String TAG = "ThuDAO";

    public ThuDAO(Context context) {
//        tao doi tuong va lay quyen doc ghi
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public int inser_Khoan_Thu(Thu thu) {
//        them nguoi dung vao db
        ContentValues values = new ContentValues();
        values.put("maThuNhap", thu.getMaThuNhap() );
        values.put("userName", thu.getUserName() );
        values.put("soTienThu", thu.getSoTienThu() );
        values.put("ngayNhanTien", thu.getNgayNhanTien() );
        values.put("chuThich", thu.getChuThich() );

//        insert db
        try {
            if (check_Khoan_Thu(thu) ){
                return -1;
            } else if (db.insert( TABLE_NAME, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG + "\tError db  :", ex.toString() );
        }
        return 1;
    }

    //getAll
    public List<Thu> getAll_Khoan_Thu() {
//        get all data for to list
        List<Thu> listThu = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if ( c != null){
            c.moveToFirst();
            while (c.isAfterLast() == false) {

                Thu thu = new Thu();
                thu.setMaThuNhap( c.getString(0));
                thu.setUserName( c.getString(1));

                thu.setSoTienThu( c.getString(2));
                thu.setNgayNhanTien( c.getString(3));
                thu.setChuThich( c.getString(4));
//            get data add list
                listThu.add(thu);
                Log.d(TAG + "//=====\t\t\t\t", thu.toString());
                c.moveToNext();
            }
            c.close();
        }
        return listThu;
    }

    //update
    public int update_Khoan_Thu( Thu thu ) {
//        update nguoi dung according to the username
        ContentValues values = new ContentValues();
        values.put("maThuNhap", thu.getMaThuNhap() );
        values.put("userName", thu.getUserName() );
        values.put("soTienThu", thu.getSoTienThu() );
        values.put("ngayNhanTien", thu.getNgayNhanTien() );
        values.put("chuThich", thu.getChuThich() );

//        update for to database
        int result = db.update(TABLE_NAME, values, "maThuNhap=?", new
                String[]{ thu.getMaThuNhap() });
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    //    delete    account
    public int delete_khoan_thu_By_ID(String maThuNhap) {
        int result = -1;
        try {
            result = db.delete(TABLE_NAME, "maThuNhap=?", new String[]{ maThuNhap });
        } catch (Exception ex){
            Log.e("\t\t\t" + TAG + "Error Database" , ex.toString() );
        }

        if (result == 0)
            return -1;
        return 1;
    }

    public boolean check_Khoan_Thu(Thu thu){

        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if ( c != null){
            c.moveToFirst();
            while (c.isAfterLast() == false) {

                Thu thu_trong = new Thu();
                thu_trong.setMaThuNhap( c.getString(0));
                thu_trong.setUserName( c.getString(1));

                thu_trong.setSoTienThu( c.getString(2));
                thu_trong.setNgayNhanTien( c.getString(3));
                thu_trong.setChuThich( c.getString(4));

                if (  thu.getMaThuNhap().toString().equals( thu_trong.getMaThuNhap().toString() ) ){
                    c.close();
                    return true;
                }

                c.moveToNext();
            }
            c.close();
        }
        return false;
    }

    public Integer get_GT(String sql){
        Integer kq = null;

        Cursor c = db.rawQuery(sql , null);
        if (c != null){
            c.moveToFirst();
            while (c.isAfterLast() == false) {
//                Chi chi = new Chi();
//                chi.setMaChiTieu(  c.getString(0));
//                chi.setUserName(  c.getString(1));
//
//                chi.setSoTienChi(  c.getString(2));
//                chi.setNgayChi(  c.getString(3));
//                chi.setChuThich(  c.getString(4));
                kq = c.getInt(0);


                c.moveToNext();
            }
            c.close();
        }
        return kq;
    }
}
