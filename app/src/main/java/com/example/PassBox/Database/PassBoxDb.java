package com.example.PassBox.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.PassBox.PassList;

import java.util.ArrayList;
import java.util.List;


public class PassBoxDb extends SQLiteOpenHelper {
    public static final String TABLE_ACCOUNT="accountPass";
    public static final String COL_ID ="passwordID";
    public static final String COL_WEBSITE_LINK = "websiteLink";
    public static final String COL_USERNAME ="username";
    public static final String COL_PASSWORD ="password";
    public static final String COL_NOTE ="note";
    public static final String COL_CREATEDAT ="createdAt";

    //-----------------------------------------------------------------------------------------------------------
    //constructor
    public PassBoxDb(@Nullable Context context) {
        super(context, "PassBox.db", null, 1);
    }

    //-----------------------------------------------------------------------------------------------------------

    //overriding sql helper class methods
    @Override
    public void onCreate(SQLiteDatabase db) {
        String passBoxTable ="CREATE TABLE "+TABLE_ACCOUNT+"("+
                COL_ID+" INTEGER primary key AUTOINCREMENT," +
                COL_WEBSITE_LINK+" TEXT NOT NULL ,"+
                COL_USERNAME+" TEXT NOT NULL,"+
                COL_PASSWORD +" TEXT NOT NULL,"+
                COL_NOTE +" TEXT ,"+
                COL_CREATEDAT+" TIMESTAMP);";
        db.execSQL(passBoxTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        onCreate(db);
    }
    //-----------------------------------------------------------------------------------------------------------
    //add account password
    public long addPassword(String sitelink, String username,String password, String note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_WEBSITE_LINK,sitelink);
        values.put(COL_USERNAME,username);
        values.put(COL_PASSWORD,password);
        values.put(COL_NOTE,note);
        long res=  db.insert(TABLE_ACCOUNT,null,values);
        db.close();
        return res;
    }
    //-----------------------------------------------------------------------------------------------------------
    // read account password
    public List<PassList> getPassword(){
        List<PassList> passlist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_ACCOUNT;
        Cursor c =  db.rawQuery(query,null);
//        db.query(null,null,null,null,null,null,null,null);
        if(c.getCount()>0){
            while(c.moveToNext()){
                String id = c.getString(0);
                String site = c.getString(1);
                String username = c.getString(2);
                String password = c.getString(3);
                String note = c.getString(4);
                passlist.add(new PassList(id,site,username,password,note));
            }
        }
        db.close();
        c.close();
        return passlist;
    }
    public List<PassList> getSelectedPassword(String passId){
        List<PassList> passlist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_ACCOUNT+" WHERE passwordID = '"+passId+"'";
        Cursor c =  db.rawQuery(query,null);

        if(c.getCount()>0){
            while(c.moveToNext()){
                String id = c.getString(0);
                String site = c.getString(1);
                String username = c.getString(2);
                String password = c.getString(3);
                String note = c.getString(4);
//                Log.d("id ","fetched id is "+id);
//                Log.d("site ","fetched site is "+site);
//                Log.d("username ","fetched username is "+username);
//                Log.d("note ","fetched note is "+note);

                passlist.add(new PassList(id,site,username,password,note));
            }
        }
        db.close();
        c.close();
        return passlist;
    }
    //-----------------------------------------------------------------------------------------------------------
    //update password Overloading
    public boolean updatePasword(String id,String sitelink, String username,String password, String note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_WEBSITE_LINK,sitelink);
        values.put(COL_USERNAME,username);
        values.put(COL_PASSWORD,password);
        values.put(COL_NOTE,note);
        int result =  db.update(TABLE_ACCOUNT,values,COL_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
        return result>0;
    }
    public boolean updatePasword(String id,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PASSWORD,password);
        int result =  db.update(TABLE_ACCOUNT,values,COL_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
        return result>0;
    }
    public boolean updatePasword(int id, String note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOTE,note);
        int result =  db.update(TABLE_ACCOUNT,values,COL_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
        return result>0;
    }
    //-----------------------------------------------------------------------------------------------------------
    //delete account password
    public boolean deletePassword(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_ACCOUNT, COL_ID + "=?", new String[]{id});
        db.close();

        return result > 0;
    }
    //----------------------------------------------------------------------------------------------------------


}
