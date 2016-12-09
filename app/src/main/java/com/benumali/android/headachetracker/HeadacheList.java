package com.benumali.android.headachetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.benumali.android.headachetracker.database.HeadacheBaseHelper;
import com.benumali.android.headachetracker.database.HeadacheCursorWrapper;
import com.benumali.android.headachetracker.database.HeadacheDbSchema;
import com.benumali.android.headachetracker.database.HeadacheDbSchema.HeadacheTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HeadacheList {
    private static HeadacheList sHeadacheList; //s convention means static variable

    //private List<Headache> mHeadaches;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static HeadacheList get(Context context) {
        if (sHeadacheList == null) {
            sHeadacheList = new HeadacheList(context);
        }
        return sHeadacheList;
    }

    private HeadacheList(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new HeadacheBaseHelper(mContext).getWritableDatabase();//create db
        //mHeadaches = new ArrayList<>();
    }

    public void addHeadache(Headache h) {
        ContentValues values = getContentValues(h);

        mDatabase.insert(HeadacheTable.NAME, null, values);
        //mHeadaches.add(h);
    }

    public void deleteHeadache(UUID headacheId) {
        String uuidString = headacheId.toString();
        mDatabase.delete(HeadacheTable.NAME, HeadacheTable.Cols.UUID + " = ?", new String[] {uuidString});
    }

    public List<Headache> getHeadaches(){
        List<Headache> headaches = new ArrayList<>();

        HeadacheCursorWrapper cursor = queryHeadaches(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                headaches.add(cursor.getHeadache());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return headaches;
        //return new ArrayList<>();
        //return mHeadaches;
    }

    public Headache getHeadache(UUID id) {
        HeadacheCursorWrapper cursor = queryHeadaches(
                HeadacheTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getHeadache();
        } finally {
            cursor.close();
        }
    }

    public void updateHeadache(Headache headache) { //method that updates rows in database
        String uuidString = headache.getId().toString();
        ContentValues values = getContentValues(headache);

        mDatabase.update(HeadacheTable.NAME, values, HeadacheTable.Cols.UUID + " = ?", //prevents sql inject
                new String[]{uuidString});
    }

    private static ContentValues getContentValues(Headache headache) {
        ContentValues values = new ContentValues();
        values.put(HeadacheTable.Cols.UUID, headache.getId().toString());
        values.put(HeadacheTable.Cols.TITLE, headache.getTitle());
        values.put(HeadacheTable.Cols.DATE, headache.getDate().getTime());
        values.put(HeadacheTable.Cols.TIME, headache.getTime().getTime());
        values.put(HeadacheTable.Cols.DESC, headache.getDescription());
        values.put(HeadacheTable.Cols.INOUT, headache.isInout() ? 1 : 0);

        return values;

    }

    private HeadacheCursorWrapper queryHeadaches(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                HeadacheTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new HeadacheCursorWrapper(cursor);
    }
}
