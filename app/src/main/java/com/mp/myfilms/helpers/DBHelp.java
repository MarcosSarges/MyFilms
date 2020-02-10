package com.mp.myfilms.helpers;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelp extends SQLiteOpenHelper {

    private final static String NAME = "Films";
    private final static int VERSION = 1;
    public final static String TABLE_FILMS = "films";

    public final static String COLUMN_ID = "id";
    public final static String COLUMN_TITLE = "title";
    public final static String COLUMN_AUTHOR = "author";
    public final static String COLUMN_CAST = "elenco";
    public final static String COLUMN_DATE = "date_laouch";
    public final static String COLUMN_THUMBNAIL = "thumbnail";


    public DBHelp(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //BLOB
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_FILMS + " " +
                "( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " vachar(50), " +
                COLUMN_AUTHOR + " varchar(50), " +
                COLUMN_CAST + " varchar(50), " +
                COLUMN_DATE + " varchar(50), " +
                COLUMN_THUMBNAIL + " BLOB" +
                ")";

        try {
            db.execSQL(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }
}
