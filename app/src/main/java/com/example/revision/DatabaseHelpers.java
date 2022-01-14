package com.example.revision;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseHelpers extends SQLiteOpenHelper {

    private static final String Db_name = "planets.db";
    private static final String DB_Table="Planete";

    private static final String ID = "ID";
    private static final String nom = "Nom";
    private static final String Dis = "Dis";


    private static final String Create_Table = "CREATE TABLE "+DB_Table+"("+
            ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            nom+" TEXT, "+
            Dis+" INTEGER "+")";

    public DatabaseHelpers(@Nullable Context context){
        super(context,Db_name,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DB_Table);
        onCreate(db);
    }

    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+DB_Table;
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }


    public Boolean insert(String nom1,int dis1){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom",nom1);
        contentValues.put("Dis",dis1);
        long result = DB.insert(DB_Table,null,contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public boolean deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+DB_Table+" WHERE ID = "+id, null);

        if(cursor.getCount()>0){
            long result = db.delete(DB_Table,"ID="+id,null);
            if (result ==-1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public boolean updateData(int id,String name,int dis1){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom",name);
        contentValues.put("Dis",dis1);
        Cursor cursor = db.rawQuery("SELECT * FROM "+DB_Table+" WHERE ID = "+id,null);
        if(cursor.getCount()>0){
            long result = db.update(DB_Table,contentValues,"ID="+id,null);
            if (result==-1){
                return false;
            }else{
                return true;
            }
        }else {
            return false;
        }
    }




}
