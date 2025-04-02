package com.example.cluster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "myDb";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String q = "CREATE TABLE myTb(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";
        sqLiteDatabase.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public boolean add(String name){
        Log.i("myTag", "adding name: " + name);

        ContentValues cv = new ContentValues();
        cv.put("name", name);

        SQLiteDatabase db = getWritableDatabase();
        long res = db.insert("myTb", null, cv);
        return res != -1;
    }

    public Cursor getList(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM myTb", null);
    }

    public boolean update(String id, String name){
        ContentValues cv = new ContentValues();
        cv.put("name", name);

        SQLiteDatabase db = getWritableDatabase();

        int result = db.update("myTb", cv, "id = ?", new String[]{id});
        return result > 0;
    }

    public boolean delete(String id){
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete("myTb", "id = ?", new String[]{id});
        return result > 0;
    }

}
