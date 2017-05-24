package com.populi.testapp.testapplication.internal.database;

import android.database.sqlite.SQLiteDatabase;

public final class UtilsDatabase {

    public static void dropTable(SQLiteDatabase db, String tableName){
        String sql = "DROP TABLE " + tableName;
        db.execSQL(sql);
    }

    public static void addField(SQLiteDatabase db, String tableName, String fieldName, String fieldType){
        db.execSQL("ALTER TABLE " + tableName + " ADD COLUMN " + fieldName + " " + fieldType);
    }
}
