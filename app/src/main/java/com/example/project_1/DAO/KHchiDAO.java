package com.example.project_1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.project_1.Models.KHchi;
import com.example.project_1.SQLite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class KHchiDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    //    ten table
    public static final String TABLE_NAME = "KeHoachChi";
    //    create table
    public static final String SQL_ke_hoach_chi = "CREATE TABLE \"KeHoachChi\" (\n" +
            "\t\"maDuChi\"\tTEXT,\n" +
            "\t\"userName\"\tTEXT,\n" +
            "\t\"soTienDuChi\"\tTEXT,\n" +
            "\t\"ngayDuChi\"\tTEXT,\n" +
            "\t\"chuThich\"\tTEXT,\n" +
            "\tFOREIGN KEY(\"userName\") REFERENCES \"NguoiDung\"(\"userName\"),\n" +
            "\tPRIMARY KEY(\"maDuChi\")\n" +
            ")" ;

    public static final String TAG = "KeHoachChiDAO";

    public KHchiDAO(Context context) {
//        tao doi tuong va lay quyen doc ghi
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public int inser_ke_hoach_chi(KHchi kHchi) {
//        them nguoi dung vao db
        ContentValues values = new ContentValues();
        values.put("maDuChi", kHchi.getMaDuChi() );
        values.put("userName", kHchi.getUserName() );
        values.put("soTienDuChi", kHchi.getSoTienDuChi() );
        values.put("ngayDuChi", kHchi.getNgayDuChi() );
        values.put("chuThich", kHchi.getChuThich() );
//        insert db
        try {
            if ( check_ID_KhChi(kHchi) ){
                return -1;
            } else if (  db.insert(TABLE_NAME, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG + " Error db " , ex.toString());
        }
        return 1;
    }

    //getAll
    public List<KHchi> getAll_Ke_hoach_chi() {
//        get all data for to list
        List<KHchi> list_ke_hoach_chi = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (c != null){
            c.moveToFirst();
            while (c.isAfterLast() == false) {

                KHchi kHchi = new KHchi();
                kHchi.setMaDuChi(  c.getString(0));
                kHchi.setUserName(  c.getString(1));

                kHchi.setSoTienDuChi(  c.getString(2));
                kHchi.setNgayDuChi(  c.getString(3));
                kHchi.setChuThich(  c.getString(4));
//            get data add list
                list_ke_hoach_chi.add(kHchi);
                Log.d(TAG + "//=====\t\t", kHchi.toString());
                c.moveToNext();
            }
            c.close();
        }
        return list_ke_hoach_chi;
    }

    //update
    public int update_ke_hoach_chi( KHchi kHchi) {
//        update nguoi dung according to the username
        ContentValues values = new ContentValues();
        values.put("maDuChi", kHchi.getMaDuChi() );
        values.put("userName", kHchi.getUserName() );
        values.put("soTienDuChi", kHchi.getSoTienDuChi() );
        values.put("ngayDuChi", kHchi.getNgayDuChi() );
        values.put("chuThich", kHchi.getChuThich() );

//        update for to database
        int result = db.update(TABLE_NAME, values, "maDuChi=?", new
                String[]{ kHchi.getMaDuChi() });
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    //    delete    account
    public int delete_Ke_hoach_Chi_By_ID(String maDuChi) {
        int result = db.delete(TABLE_NAME, "maDuChi=?", new String[]{  maDuChi  });

        if (result == 0)
            return -1;
        return 1;
    }

    public boolean check_ID_KhChi(KHchi ngoai){
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (c != null){
            c.moveToFirst();
            while (c.isAfterLast() == false) {

                KHchi kHchi = new KHchi();
                kHchi.setMaDuChi(  c.getString(0));
                kHchi.setUserName(  c.getString(1));

                kHchi.setSoTienDuChi(  c.getString(2));
                kHchi.setNgayDuChi(  c.getString(3));
                kHchi.setChuThich(  c.getString(4));

                if ( ngoai.getMaDuChi().toString().equals( kHchi.getMaDuChi().toString() ) ){
                    return true;
                }
                Log.d(TAG + "//=====\t\t", kHchi.toString());
                c.moveToNext();
            }
            c.close();
        }
        return false;
    }
}
