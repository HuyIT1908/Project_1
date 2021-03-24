package com.example.project_1.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.project_1.DAO.NguoiDungDAO;
import com.example.project_1.DAO.TkCaNhanDAO;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dbChiTieuManager";
    public static final int VERSION = 3;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME + ".db", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NguoiDungDAO.SQL_NGUOI_DUNG);
        Log.e("//=======\t\t" + NguoiDungDAO.TAG , "Đã tạo Table Thành công nha \t\t\n !!!");
        db.execSQL( TkCaNhanDAO.SQL_TK_CA_NHAN );
        Log.e("//=======\t\t" + TkCaNhanDAO.TAG , "Đã tạo Table Thành công nha \t\t\n !!!");
//        db.execSQL(SachDAO.SQL_SACH);
//        db.execSQL(HoaDonDAO.SQL_HOA_DON);
//        db.execSQL(HoaDonChiTietDAO.SQL_HOA_DON_CHI_TIET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + NguoiDungDAO.TABLE_NAME);
        db.execSQL("Drop table if exists " + TkCaNhanDAO.TABLE_NAME );
//        db.execSQL("Drop table if exists " + SachDAO.TABLE_NAME);
//        db.execSQL("Drop table if exists " + HoaDonDAO.TABLE_NAME);
//        db.execSQL("Drop table if exists "+HoaDonChiTietDAO.TABLE_NAME);
        onCreate(db);
    }
}
