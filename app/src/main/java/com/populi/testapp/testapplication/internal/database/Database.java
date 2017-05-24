package com.populi.testapp.testapplication.internal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Alexander Gavrikov.
 */

public class Database {

    private static Database mInstance;
    private SQLiteDatabase mDb;

    public static Database getInstance(Context context) {
        if (mInstance == null){
            synchronized (Database.class){
                if (mInstance == null){
                    mInstance = new Database(context);
                }
            }
        }
        return mInstance;
    }

    private Database(Context context) {
        mDb = DatabaseHelper.getInstance(context).getWritableDatabase();
    }
}
