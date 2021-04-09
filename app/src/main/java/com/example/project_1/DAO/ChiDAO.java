package com.example.project_1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.project_1.Models.Chi;
import com.example.project_1.SQLite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ChiDAO {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    //    ten table
    public static final String TABLE_NAME = "Chi";
    //    create table
    public static final String SQL_Chi = "CREATE TABLE \"Chi\" (\n" +
            "\t\"maChiTieu\"\tTEXT,\n" +
            "\t\"userName\"\tTEXT,\n" +
            "\t\"soTienChi\"\tTEXT,\n" +
            "\t\"ngayChi\"\tTEXT,\n" +
            "\t\"chuThich\"\tTEXT,\n" +
            "\tFOREIGN KEY(\"userName\") REFERENCES \"NguoiDung\"(\"userName\"),\n" +
            "\tPRIMARY KEY(\"maChiTieu\")\n" +
            ");" ;

    public static final String TAG = "ChiDAO";

    public ChiDAO(Context context) {
//        Tạo đối tượng và xin quyền đọc ghi
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // insert ( Thêm khoản chi )
    public int inser_Khoan_Chi(Chi chi) {
//        them nguoi dung vao db
        ContentValues values = new ContentValues();
        values.put("maChiTieu", chi.getMaChiTieu() );
        values.put("userName", chi.getUserName() );
        values.put("soTienChi", chi.getSoTienChi() );

        values.put("ngayChi", chi.getNgayChi() );
        values.put("chuThich", chi.getChuThich() );

//        insert db
        try {
            if ( check(chi) ){
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
    public List<Chi> getAll_Khoan_Chi() {
//        get all data for to list
        List<Chi> list_Chi = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (c != null){
            c.moveToFirst();
            while (c.isAfterLast() == false) {

                Chi chi = new Chi();
                chi.setMaChiTieu(  c.getString(0));
                chi.setUserName(  c.getString(1));

                chi.setSoTienChi(  c.getString(2));
                chi.setNgayChi(  c.getString(3));
                chi.setChuThich(  c.getString(4));
//            get data add list
                list_Chi.add(chi);
                Log.d(TAG + "//=====\t\t\t\t", list_Chi.toString());
                c.moveToNext();
            }
            c.close();
        }
        return list_Chi;
    }

    //update
    public int update_Khoan_Chi( Chi chi ) {
//        update nguoi dung according to the username
        ContentValues values = new ContentValues();
        values.put("maChiTieu", chi.getMaChiTieu() );
        values.put("userName", chi.getUserName() );
        values.put("soTienChi", chi.getSoTienChi() );

        values.put("ngayChi", chi.getNgayChi() );
        values.put("chuThich", chi.getChuThich() );
//        update for to database
        int result = db.update(TABLE_NAME, values, "maChiTieu=?", new
                String[]{ chi.getMaChiTieu() });
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    //    delete    account
    public int delete_Khoan_Chi_By_ID(String maChiTieu) {
        int result = db.delete(TABLE_NAME, "maChiTieu=?", new String[]{ maChiTieu });

        if (result == 0)
            return -1;
        return 1;
    }

    public boolean check(Chi ngoai){
        //        get all data for to list

        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (c != null){
            c.moveToFirst();
            while (c.isAfterLast() == false) {

                Chi chi = new Chi();
                chi.setMaChiTieu(  c.getString(0));
                chi.setUserName(  c.getString(1));

                chi.setSoTienChi(  c.getString(2));
                chi.setNgayChi(  c.getString(3));
                chi.setChuThich(  c.getString(4));

                if ( ngoai.getMaChiTieu().toString().equals( chi.getMaChiTieu().toString() ) ){
                    c.close();
                    return true;
                }

                Log.d(TAG + "//=====\t\t\t\t", chi.toString());
                c.moveToNext();
            }
            c.close();
        }
        return false;
    }

    public String get_GT(String sql){
        String kq = null;

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
                kq = String.valueOf(c.getInt(0));


                c.moveToNext();
            }
            c.close();
        }
        return kq;
    }
}
