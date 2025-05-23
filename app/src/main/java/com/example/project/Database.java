package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry1= "create table users(username text, email text, password text)";
        db.execSQL(qry1);

        String qry2= "create table cart(username text, product text, price float,otype text)";
        db.execSQL(qry2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void register(String username, String email, String password){
        ContentValues cv= new ContentValues();
        cv.put("username",username);
        cv.put("email",email);
        cv.put("password",password);

        SQLiteDatabase db = getWritableDatabase();
        db.insert("users",null,cv);
        db.close();
    }

    public int login(String username,String password){
        int result =0;
        String str[]= new String[2];
        str[0]= username;
        str[1]=password;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c= db.rawQuery("select * from users where username=? and password=?",str);
        if(c.moveToFirst()){
            result=1;
        }

        return result;

    }

    public void addCart(String username, String product, float price, String otype) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("product", product);
        cv.put("price", price);
        cv.put("otype", otype);

        SQLiteDatabase db = getWritableDatabase();
        db.insert("cart", null, cv);
        db.close();
    }
    public int checkCart(String username, String product) {
        int result = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM cart WHERE username = ? AND product = ?",
                new String[]{username, product});

        if (cursor.moveToFirst()) {
            result = 1;
        }

        cursor.close();
        db.close();
        return result;
    }

    public void removeCart(String username, String otype) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(
                "cart",
                "username=? and otype=?",
                new String[]{username, otype});
        db.close();
    }

    public ArrayList<String> getCartData(String username, String otype) {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] str = new String[]{username, otype};

        Cursor c = db.rawQuery("SELECT * FROM cart WHERE username = ? AND otype = ?", str);

        if (c.moveToFirst()) {
            do {
                String product = c.getString(1);
                String price = c.getString(2);
                arr.add(product + "$" + price);
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return arr;
    }
}
