package com.example.project_1.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.project_1.SQLite.DatabaseHelper;

public class KhoanNoDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    //    ten table
    public static final String TABLE_NAME = "KhoanNo";
    //    create table
    public static final String SQL_Khoan_No = "CREATE TABLE \"KhoanNo\" (\n" +
            "\t\"maKhoanNo\"\tTEXT,\n" +
            "\t\"maNguoiDung\"\tTEXT,\n" +
            "\t\"soTienNo\"\tTEXT,\n" +
            "\t\"ngayNo\"\tTEXT,\n" +
            "\t\"ngayTra\"\tTEXT,\n" +
            "\t\"chuThich\"\tTEXT,\n" +
            "\tPRIMARY KEY(\"maKhoanNo\"),\n" +
            "\tFOREIGN KEY(\"maNguoiDung\") REFERENCES \"tkCaNhan\"(\"maNguoiDung\")\n" +
            ");" ;
    public static final String TAG = "KhoanNoDAO";

    public KhoanNoDAO(Context context) {
//        tao doi tuong va lay quyen doc ghi
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

//    //insert
//    public int inserNguoiDung(NguoiDung nd) {
////        them nguoi dung vao db
//        ContentValues values = new ContentValues();
//        values.put("username", nd.getUserName());
//        values.put("password", nd.getPassword());
//
//        values.put("hoten", nd.getHoTen());
//        values.put("gt", nd.getGioiTinh());
//        values.put("phone", nd.getPhone());
////        insert db
//        try {
//            if (db.insert(TABLE_NAME, null, values) == -1) {
//                return -1;
//            }
//        } catch (Exception ex) {
//            Log.e(TAG + "Error db  :", ex.toString());
//        }
//        return 1;
//    }
//
//    //getAll
//    public List<NguoiDung> getAllNguoiDung() {
////        get all data for to list
//        List<NguoiDung> dsNguoiDung = new ArrayList<>();
//
//        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
//        c.moveToFirst();
//        while (c.isAfterLast() == false) {
//
//            NguoiDung ee = new NguoiDung();
//            ee.setUserName(c.getString(0));
//            ee.setPassword(c.getString(1));
//
//            ee.setHoTen(c.getString(2));
//            ee.setGioiTinh(c.getString(3));
//            ee.setPhone(c.getString(4));
////            get data add list
//            dsNguoiDung.add(ee);
//            Log.d(TAG + "//=====\t\t\t\t", ee.toString());
//            c.moveToNext();
//        }
//        c.close();
//        return dsNguoiDung;
//    }
//
//    //update
//    public int updateNguoiDung(NguoiDung nd) {
////        update nguoi dung according to the username
//        ContentValues values = new ContentValues();
//        values.put("username", nd.getUserName());
//        values.put("password", nd.getPassword());
//
//        values.put("hoten", nd.getHoTen());
//        values.put("gt", nd.getGioiTinh());
//        values.put("phone", nd.getPhone());
////        update for to database
//        int result = db.update(TABLE_NAME, values, "username=?", new
//                String[]{nd.getUserName()});
//        if (result == 0) {
//            return -1;
//        }
//        return 1;
//    }
//
//    //  change Password
//    public int changePasswordNguoiDung(NguoiDung nd) {
////        update Password according to the username
//        ContentValues values = new ContentValues();
//        values.put("username", nd.getUserName());
//        values.put("password", nd.getPassword());
//        int result = db.update(TABLE_NAME, values, "username=?", new
//                String[]{nd.getUserName()});
//        if (result == 0) {
//            return -1;
//        }
//        return 1;
//    }
//
//    //  update info for to nguoi dung
//    public int updateInfoNguoiDung(NguoiDung nd) {
//        ContentValues values = new ContentValues();
//        values.put("username", nd.getUserName());
//        values.put("password", nd.getPassword());
//
//        values.put("hoten", nd.getHoTen());
//        values.put("gt", nd.getGioiTinh());
//        values.put("phone", nd.getPhone());
//
//        int result = db.update(TABLE_NAME, values, "username=?", new
//                String[]{nd.getUserName()});
//        if (result == 0) {
//            return -1;
//        }
//        return 1;
//    }
//
//    //    delete    account
//    public int deleteNguoiDungByID(String username) {
//        int result = db.delete(TABLE_NAME, "username=?", new String[]{username});
//
//        if (result == 0)
//            return -1;
//        return 1;
//    }
//
//    //check login
//    public int checkLogin(String username, String password) {
//        int result = 0;
////        get all data for to list
//        List<NguoiDung> dsNguoiDung = new ArrayList<>();
//
//        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
//        c.moveToFirst();
//        while (c.isAfterLast() == false) {
//
//            NguoiDung ee = new NguoiDung();
//            ee.setUserName(c.getString(0));
//            ee.setPassword(c.getString(1));
//
//            ee.setHoTen(c.getString(2));
//            ee.setGioiTinh(c.getString(3));
//            ee.setPhone(c.getString(4));
//
//            Log.d(TAG + "//=====\t\t\t\t", ee.toString());
//            if (username.equals(c.getString(0))
//                    && password.equals(c.getString(1))){
//                c.close();
////                Log.e("-kiem tra login -------" , "----------thanh cong roi nha");
//                return 1;
//            }
////            get data add list
//            dsNguoiDung.add(ee);
//            Log.d(TAG + "//===== \t\t","Error function checkLogin" + ee.toString());
//            c.moveToNext();
//        }
//        c.close();
//
//
////        int result = db.delete(TABLE_NAME, "username=? AND password=?", new String[]{username, password});
//        if (result == 0)
//            return -1;
//        return 1;
//    }
}
