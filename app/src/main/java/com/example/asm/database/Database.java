package com.example.asm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context, "QUANLYCHITIEU", null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tạo bảng Thu chi cho maKhoan tự tăng lên
        String sql = "CREATE TABLE thuchi(" +
                "maKhoan integer PRIMARY KEY AUTOINCREMENT," +
                "tenKhoan text," +
                "loaiKhoan integer)";
        db.execSQL(sql);
        //Thêm loại thu chi, 0 là thu, 1 là chi
//        sql = "INSERT INTO thuchi VALUES(null,'Lương',0)";
//        db.execSQL(sql);
//        sql = "INSERT INTO thuchi VALUES(null,'Tiền trọ',1)";
//        db.execSQL(sql);

        //Tạo bảng giao dịch, cho maGd tự tăng lên
        sql = "CREATE TABLE giaodich(" +
                "maGd integer PRIMARY KEY AUTOINCREMENT," +
                "moTaGd text," +
                "ngayGd date," +
                "soTien integer, " +
                "maKhoan integer REFERENCES thuchi(maKhoan))";
        db.execSQL(sql);
//        sql = "INSERT INTO giaodich VALUES(null,'Mua quần áo','7/11/2020',1500000,6)";
//        db.execSQL(sql);
//        sql = "INSERT INTO giaodich VALUES(null,'Lương tháng 11','17/11/2020',1500000,1)";
//        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xóa toàn bộ database khi version thay đổi
        db.execSQL("DROP TABLE IF EXISTS thuchi");
        db.execSQL("DROP TABLE IF EXISTS giaodich");
        //Tạo lại bảng
        onCreate(db);
    }
}
