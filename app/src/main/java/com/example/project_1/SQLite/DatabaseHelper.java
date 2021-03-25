package com.example.project_1.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.project_1.DAO.ChiDAO;
import com.example.project_1.DAO.KHchiDAO;
import com.example.project_1.DAO.KhoanNoDAO;
import com.example.project_1.DAO.NguoiDungDAO;
import com.example.project_1.DAO.ThongKeDAO;
import com.example.project_1.DAO.ThuDAO;
import com.example.project_1.DAO.TkCaNhanDAO;
import com.example.project_1.DAO.TietKiemDAO;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dbChiTieuManager";
    public static final int VERSION = 7;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME + ".db", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( NguoiDungDAO.SQL_NGUOI_DUNG);
        Log.e("//=======\t\t" + NguoiDungDAO.TAG , "Đã tạo Table Thành công nha \t\t\n !!!");
        db.execSQL( TkCaNhanDAO.SQL_TK_CA_NHAN );
        Log.e("//=======\t\t" + TkCaNhanDAO.TAG , "Đã tạo Table Thành công nha \t\t\n !!!");
        db.execSQL( ThuDAO.SQL_Thu );
        Log.e("//=======\t\t" + ThuDAO.TAG , "Đã tạo Table Thành công nha \t\t\n !!!");
        db.execSQL(  ChiDAO.SQL_Chi  );
        Log.e("//=======\t\t" + ChiDAO.TAG , "Đã tạo Table Thành công nha \t\t\n !!!");
        db.execSQL(  KHchiDAO.SQL_ke_hoach_chi  );
        Log.e("//===\t\t" + KHchiDAO.TAG , "Đã tạo Table Thành công nha \t\t\n !!!");

        db.execSQL(  KhoanNoDAO.SQL_Khoan_No  );
        Log.e("//===\t\t" + KhoanNoDAO.TAG , "Đã tạo Table Thành công nha \t\t\n !!!");
        db.execSQL(  TietKiemDAO.SQL_Tiet_Kiem  );
        Log.e("//===\t\t" + TietKiemDAO.TAG , "Đã tạo Table Thành công nha \t\t\n !!!");
        db.execSQL(  ThongKeDAO.SQL_Thong_Ke  );
        Log.e("//===\t\t" + ThongKeDAO.TAG , "Đã tạo Table Thành công nha \t\t\n !!!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + NguoiDungDAO.TABLE_NAME);
        db.execSQL("Drop table if exists " + TkCaNhanDAO.TABLE_NAME );
        db.execSQL("Drop table if exists " + ThuDAO.TABLE_NAME  );
        db.execSQL("Drop table if exists " + ChiDAO.TABLE_NAME  );
        db.execSQL("Drop table if exists " + KHchiDAO.TABLE_NAME  );
        db.execSQL("Drop table if exists " + KhoanNoDAO.TABLE_NAME  );
        db.execSQL("Drop table if exists " + TietKiemDAO.TABLE_NAME  );
        db.execSQL("Drop table if exists " + ThongKeDAO.TABLE_NAME  );


        onCreate(db);
        Log.e("\nDa cap nhat DataBase " , "\t\tthanh cong . OK !!!" + "\t\t\t\t DatabaseHelper");
    }
}
