package com.arps.imean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rishabh on 3/10/15.
 */
public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "imean1.db";

    public static final String TABLE_WORDSANDLABELS = "imean1";

    public static final String COLUMN_ID = "_id";

    public static final String COLUMN_WORDS = "_word";

    public static final String COLUMN_LABELS = "_label";

    public static final String COLUMN_MEANINGS = "_meaning";

    public static final String  TAG = "MyDBHandler";


    public MyDBHandler(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){

        super(context,DATABASE_NAME,factory,DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(TAG,"onCreate of MyDbhandler started");

        String query = "CREATE TABLE " + TABLE_WORDSANDLABELS + "(" +
                COLUMN_ID + " integer primary key autoincrement," +
                COLUMN_LABELS + " text," +
                COLUMN_WORDS + " text," +
                COLUMN_MEANINGS + " text " + ");" ;

        db.execSQL(query);

        Log.d(TAG, "onCreate of MyDbhandler completed");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d(TAG,"onupgrade of MyDbhandler started");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDSANDLABELS + ";");

        //now since an old redundant table with same name dropped, we can create new table

        onCreate(db);

        Log.d(TAG, "onUPgrade of MyDbhandler completed");
    }


    //ADD A NEW ROW TO DATABASE
    public void addRow(WordsAndLabels word){

        ContentValues values = new ContentValues();
        values.put(COLUMN_WORDS,word.get_word());
        values.put(COLUMN_LABELS, word.get_label());
        values.put(COLUMN_MEANINGS, word.get_meaning());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_WORDSANDLABELS, null, values);

        db.close();


    }


    //DELTE A ROW ROM DATABASE
    public void deleteRow(String word){

        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_WORDSANDLABELS + " WHERE " + COLUMN_WORDS
                + "=\"" + word + "\";");
    }


    //PRINT OUT THE DATABSE AS A STRING
    public String databaseToString(){

        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT "+COLUMN_WORDS+" FROM " + TABLE_WORDSANDLABELS + " WHERE 1 ;";


        //cursor to point to a location in results
        Cursor c = db.rawQuery(query,null);

        //move to first line in results

        c.moveToFirst();

        //DOUBT
        while(!c.isAfterLast()){

            if(c.getString(c.getColumnIndex(COLUMN_WORDS))!=null){

                dbString += c.getString(c.getColumnIndex(COLUMN_WORDS));

                dbString += "\n";

            }
        }

        db.close();

        return dbString;

    }




}
