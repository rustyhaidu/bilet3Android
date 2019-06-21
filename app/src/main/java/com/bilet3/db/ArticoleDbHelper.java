package com.bilet3.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by claudiu.haidu on 7/16/2015.
 */
public class ArticoleDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bilet3.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_QUERY = "CREATE TABLE " + ArticolDbInfo.NewArticolInfo.TABLE_NAME +
            " (" + ArticolDbInfo.NewArticolInfo.TITLU + " TEXT, "
            + ArticolDbInfo.NewArticolInfo.ABSTRACT + " TEXT, "
            + ArticolDbInfo.NewArticolInfo.AUTORI + " TEXT);";


    public ArticoleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("Database opened", "database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_QUERY);
        Log.e("Database", "Table created!");
    }

    public void insertInfo(String titlu, String abstractarticol, String autori, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ArticolDbInfo.NewArticolInfo.TITLU, titlu);
        contentValues.put(ArticolDbInfo.NewArticolInfo.ABSTRACT, abstractarticol);
        contentValues.put(ArticolDbInfo.NewArticolInfo.AUTORI, autori);

        db.insert(ArticolDbInfo.NewArticolInfo.TABLE_NAME, null, contentValues);
        Log.i("Database operation", "one row inserted");
    }

    public Cursor selectInfo(SQLiteDatabase db) {
        Cursor cursor;
        String[] projections = {ArticolDbInfo.NewArticolInfo.TITLU,
                                ArticolDbInfo.NewArticolInfo.ABSTRACT,
                                ArticolDbInfo.NewArticolInfo.AUTORI};

        cursor = db.query(ArticolDbInfo.NewArticolInfo.TABLE_NAME, projections, null, null, null, null, null);
        return cursor;
    }
    /*public Cursor getContact(String employeeName, SQLiteDatabase sqLiteDatabase)
    {
        String[] projections = {ArticolDbInfo.NewArticolInfo.EMPLOYEE_NAME, ArticolDbInfo.NewArticolInfo.EMPLOYEE_SURNAME, ArticolDbInfo.NewArticolInfo.EMPLOYEE_GENDER, ArticolDbInfo.NewArticolInfo.EMPLOYEE_BIRTHDATE};
        String selection = ArticolDbInfo.NewArticolInfo.EMPLOYEE_NAME +" LIKE ?";
        String[] selection_args = {employeeName};

        Cursor cursor = sqLiteDatabase.query(ArticolDbInfo.NewArticolInfo.TABLE_NAME,projections,selection,selection_args,null,null,null);
        return cursor;
    }*/

    public void deleteInformation(String articolName, SQLiteDatabase sqLiteDatabase){
        String selection = ArticolDbInfo.NewArticolInfo.TITLU +" LIKE ?";
        String[] selection_args = {articolName};

        sqLiteDatabase.delete(ArticolDbInfo.NewArticolInfo.TABLE_NAME,selection,selection_args);

    }
    public int updateInformation(String oldTitlu, String titlu, String abstractArticol, String autori, SQLiteDatabase sqLiteDatabase)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ArticolDbInfo.NewArticolInfo.TITLU,titlu);
        contentValues.put(ArticolDbInfo.NewArticolInfo.ABSTRACT,abstractArticol);
        contentValues.put(ArticolDbInfo.NewArticolInfo.AUTORI, autori);

        String selection = ArticolDbInfo.NewArticolInfo.TITLU + " LIKE ?";
        String[] selection_args = {oldTitlu};

       int count =  sqLiteDatabase.update(ArticolDbInfo.NewArticolInfo.TABLE_NAME,contentValues,selection,selection_args);
        return count;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
