package com.arps.imean;

/**
 * Created by rishabh on 5/10/15.
 */

import android.database.sqlite.SQLiteOpenHelper;


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.DatabaseUtils;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

        import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDB.db";
    public static final String TABLE_NAME = "word_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_WORD = "word";
    public static final String COLUMN_MEANING = "meaning";
    public static final String COLUMN_LABEL = "label";

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + TABLE_NAME +
                        "(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_WORD + " text, " + COLUMN_MEANING + " text, " + COLUMN_LABEL + " text);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertWord(String word, String meaning, String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_WORD, word);
        contentValues.put(COLUMN_MEANING, meaning);
        contentValues.put(COLUMN_LABEL, label);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_ID + "=" + id + ";", null);
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    public boolean updateWord(String word, String meaning, String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_WORD, word);
        //contentValues.put(COLUMN_MEANING, meaning);
        contentValues.put(COLUMN_LABEL, label);
        db.update(TABLE_NAME, contentValues, COLUMN_WORD + " = ? ", new String[]{word});
        return true;
    }

    public Integer deleteWord(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[]{Integer.toString(id)});
    }
    public Integer deleteWord(String word) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                COLUMN_WORD + " = ? ",
                new String[]{word});
    }

    public ArrayList<String> getAllWords(String label) {
        ArrayList<String> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " WHERE " + COLUMN_LABEL + "=\"" + label + "\" ORDER BY " + COLUMN_WORD + ";", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            array_list.add(res.getString(res.getColumnIndex(COLUMN_WORD)));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public ArrayList<String> getAllLabels() {
        ArrayList<String> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select distinct " + COLUMN_LABEL + " from " + TABLE_NAME + " order by " + COLUMN_LABEL + ";", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            array_list.add(res.getString(res.getColumnIndex(COLUMN_LABEL)));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public String searchWord(String word) {
        SQLiteDatabase db = this.getReadableDatabase();
        String find = new String();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + ";", null);

        res.moveToFirst();
        while(!res.isAfterLast()){

           if( word.equals(res.getString(res.getColumnIndex(COLUMN_WORD)))){
               find = res.getString(res.getColumnIndex(COLUMN_LABEL));
               break;
           }
            res.moveToNext();
        }
        res.close();

        return find;

    }
    public String getMeaning(String word){

        SQLiteDatabase db = this.getReadableDatabase();
        String meaning = new String();
        Cursor res = db.rawQuery( "select * from " + TABLE_NAME + " WHERE " + COLUMN_WORD + " = \"" + word + "\";", null );
        //meaning = res.getString(0);
        res.moveToFirst();
        while(!res.isAfterLast()){

            meaning = res.getString(res.getColumnIndex(COLUMN_MEANING));
            res.moveToNext();
        }
        res.close();

        return meaning;
        //return res.getString(res.getColumnIndex(COLUMN_MEANING));
    }
}