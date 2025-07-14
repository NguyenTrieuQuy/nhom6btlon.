package com.example.nhom6btlon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "UserData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Bảng người dùng
        db.execSQL("CREATE TABLE users(name TEXT PRIMARY KEY, phone TEXT, password TEXT)");

        // Bảng sản phẩm
        db.execSQL("CREATE TABLE IF NOT EXISTS products (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT UNIQUE," +
                "price TEXT," +
                "image INTEGER)");

        // Bảng giỏ hàng
        db.execSQL("CREATE TABLE IF NOT EXISTS cart (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "product_id INTEGER," +
                "name TEXT," +
                "price TEXT," +
                "image INTEGER)");

        //  Bảng thanh toán
        db.execSQL("CREATE TABLE IF NOT EXISTS payments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "phone TEXT, " +
                "email TEXT, " +
                "province TEXT, " +
                "district TEXT, " +
                "address TEXT, " +
                "total INTEGER)");
        //đăt lịch
        db.execSQL("CREATE TABLE IF NOT EXISTS appointments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "phone TEXT, " +
                "date TEXT, " +
                "time TEXT, " +
                "note TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS products");
        db.execSQL("DROP TABLE IF EXISTS cart");
        onCreate(db);
    }



    // PHẦN NGƯỜI DÙNG
    public boolean insertUser(String name, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);
        values.put("password", password);
        long result = db.insert("users", null, values);
        return result != -1;
    }
    //Kiểm tra người dùng có tồn tại không
    public boolean checkUser(String name, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE name=? AND password=?",
                new String[]{name, password});
        return cursor.getCount() > 0;
    }


    // PHẦN PHỤ TÙNG
    public boolean insertProduct(String name, String price, int imageResId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("price", price);
        values.put("image", imageResId);
        long result = db.insert("products", null, values);
        return result != -1;
    }
    // Lấy danh sách sản phẩm từ bảng product
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM products", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String price = cursor.getString(2);
                int image = cursor.getInt(3);
                list.add(new Product(id, name, price, image));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }
    //Xóa sản phẩm của bảng product
    public boolean deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("products", "id=?", new String[]{String.valueOf(id)}) > 0;
    }


    // PHẦN GIỎ HÀNG
    public boolean addToCart(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("product_id", product.getId());
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        values.put("image", product.getImageResId());

        long result = db.insert("cart", null, values);
        return result != -1;
    }
    //Lấy sản phẩm trong giỏ hàng
    public List<Product> getCartItems() {
        List<Product> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM cart", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0); // id trong bảng giỏ hàng (khác product_id)
                String name = cursor.getString(2);
                String price = cursor.getString(3);
                int image = cursor.getInt(4);
                list.add(new Product(id, name, price, image));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }
    //Xóa sản phẩm của giỏ hàng
    public boolean clearCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("cart", null, null) > 0;
    }
    //Xóa thành phần trong giỏ hàng
    public boolean deleteCartItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("cart", "id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }
    //PHẦN THANH TOÁN
    public boolean addPayment(String name, String phone, String email,
                              String province, String district, String address, int total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);
        values.put("email", email);
        values.put("province", province);
        values.put("district", district);
        values.put("address", address);
        values.put("total", total);

        long result = db.insert("payments", null, values);
        return result != -1;
    }
    // PHẦN ĐẶT LỊCH
    public boolean addAppointment(String name, String phone, String date, String time, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);
        values.put("date", date);
        values.put("time", time);
        values.put("note", note);
        return db.insert("appointments", null, values) != -1;
    }
}
