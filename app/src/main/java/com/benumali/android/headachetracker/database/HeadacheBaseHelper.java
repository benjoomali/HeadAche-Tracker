package com.benumali.android.headachetracker.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.benumali.android.headachetracker.database.HeadacheDbSchema.HeadacheTable;

public class HeadacheBaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "headacheBase.db";

    public HeadacheBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + HeadacheDbSchema.HeadacheTable.NAME + "(" +
            " _id integer primary key autoincrement, " +
                        HeadacheTable.Cols.UUID + ", " +
                        HeadacheTable.Cols.TITLE + ", " +
                        HeadacheTable.Cols.DATE + ", " +
                        HeadacheTable.Cols.TIME + ", " +
                        HeadacheTable.Cols.DESC + ", " +
                        HeadacheTable.Cols.INOUT + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
