package com.example.project_1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.project_1.Models.TietKiem;
import com.example.project_1.SQLite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class TietKiemDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    //    ten table
    public static final String TABLE_NAME = "TietKiem";
    //    create table
    public static final String SQL_Tiet_Kiem = "CREATE TABLE \"TietKiem\" (\n" +
            "\t\"maTietKiem\"\tTEXT,\n" +
            "\t\"userName\"\tTEXT,\n" +
            "\t\"soTienTietKiem\"\tTEXT,\n" +
            "\t\"ngayTietKiem\"\tTEXT,\n" +
            "\t\"chuThich\"\tTEXT,\n" +
            "\t\"status\"\tTEXT,\n" +
            "\tFOREIGN KEY(\"userName\") REFERENCES \"NguoiDung\"(\"userName\"),\n" +
            "\tPRIMARY KEY(\"maTietKiem\")\n" +
            ");" ;

    public static final String TAG = "TietKiemDAO";

    public TietKiemDAO(Context context) {
//        tao doi tuong va lay quyen doc ghi
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public int inser_Tiet_Kiem( TietKiem tietKiem) {
//        them nguoi dung vao db
        ContentValues values = new ContentValues();
        values.put("maTietKiem", tietKiem.getMaTietKiem()  );
        values.put("userName", tietKiem.getUserName()  );
        values.put("soTienTietKiem", tietKiem.getSoTienTietKiem()  );
        values.put("ngayTietKiem", tietKiem.getNgayTietKiem()  );
        values.put("chuThich", tietKiem.getChuThich()  );
        values.put("status", tietKiem.getStatus()  );

//        insert db
        try {
            if ( chech_Tiet_kiem(tietKiem) ){
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
    public List<TietKiem> getAll_Tiet_Kiem() {
//        get all data for to list
        List<TietKiem> tietKiemList = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (c != null){
            c.moveToFirst();
            while (c.isAfterLast() == false) {

                TietKiem ee = new TietKiem();
                ee.setMaTietKiem(c.getString(0));
                ee.setUserName(c.getString(1));

                ee.setSoTienTietKiem(c.getString(2));
                ee.setNgayTietKiem(c.getString(3));
                ee.setChuThich(c.getString(4));
                ee.setStatus(c.getString(5) );
//            get data add list
                tietKiemList.add(ee);
                Log.d(TAG + "//=====\t\t\t\t", ee.toString());
                c.moveToNext();
            }
            c.close();
        }
        return tietKiemList;
    }

    //update
    public int update_Tiet_Kiem( TietKiem tietKiem ) {
//        update nguoi dung according to the username
        ContentValues values = new ContentValues();
        values.put("maTietKiem", tietKiem.getMaTietKiem()  );
        values.put("userName", tietKiem.getUserName()  );
        values.put("soTienTietKiem", tietKiem.getSoTienTietKiem()  );
        values.put("ngayTietKiem", tietKiem.getNgayTietKiem()  );
        values.put("chuThich", tietKiem.getChuThich()  );
        values.put("status", tietKiem.getStatus()  );

//        update for to database
        try {
            int result = db.update(TABLE_NAME, values , "maTietKiem=?",
                    new String[]{ tietKiem.getMaTietKiem() });

            if (result == 0) {
                return -1;
            }
        } catch (Exception ex){
            Log.e(TAG + "update \n\n" , ex.toString() );
        }
        return 1;
    }

    //    delete    account
    public int delete_Tiet_kiem_By_ID(String maTietKiem) {
        int result = db.delete(TABLE_NAME, "maTietKiem=?", new String[]{ maTietKiem });

        if (result == 0)
            return -1;
        return 1;
    }

    public boolean chech_Tiet_kiem(TietKiem tietKiem){

        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (c != null){
            c.moveToFirst();
            while (c.isAfterLast() == false) {

                TietKiem ee = new TietKiem();
                ee.setMaTietKiem(c.getString(0));
                ee.setUserName(c.getString(1));

                ee.setSoTienTietKiem(c.getString(2));
                ee.setNgayTietKiem(c.getString(3));
                ee.setChuThich(c.getString(4));
                ee.setStatus(c.getString(5));

                if ( tietKiem.getMaTietKiem().equals( ee.getMaTietKiem() ) ){
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
