package com.benumali.android.headachetracker.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.benumali.android.headachetracker.Headache;
import com.benumali.android.headachetracker.database.HeadacheDbSchema.HeadacheTable;

import java.util.Date;
import java.util.UUID;

public class HeadacheCursorWrapper extends CursorWrapper {
    public HeadacheCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Headache getHeadache() {
        String uuidString = getString(getColumnIndex(HeadacheTable.Cols.UUID));
        String title = getString(getColumnIndex(HeadacheTable.Cols.TITLE));
        long date = getLong(getColumnIndex(HeadacheTable.Cols.DATE));
        long time = getLong(getColumnIndex(HeadacheTable.Cols.TIME));
        String desc = getString(getColumnIndex(HeadacheTable.Cols.DESC));
        int isInout = getInt(getColumnIndex(HeadacheTable.Cols.INOUT));

        Headache headache = new Headache(UUID.fromString(uuidString));
        headache.setTitle(title);
        headache.setDate(new Date(date));
        headache.setTime(new Date(time));
        headache.setDescription(desc);
        headache.setInout(isInout != 0);

        return headache;
    }
}
