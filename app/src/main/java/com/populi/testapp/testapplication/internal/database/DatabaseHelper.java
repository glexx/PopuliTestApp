package com.populi.testapp.testapplication.internal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String TAG = "Database";

	private static final int DB_VERSION = 1;
	public static final String DB_NAME = "data.db";

    private static DatabaseHelper mInstance;
    private static final Object mLock = new Object();

	// Countries Table
	public static final class CountriesTable implements BaseColumns {

	    public static final String TABLE_NAME = "CountriesTable";

	    public static final String ID 	   = "id";
        public static final String NAME    = "name";

	    public static final String SQL_CREATE =
	    		"CREATE TABLE IF NOT EXISTS " + TABLE_NAME
    				+ " ("
    		  		+ _ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ID     + " TEXT, "
                    + NAME   + " TEXT"
				+");";
	}

	// Cities Table
	public static final class CitiesTable implements BaseColumns {

	    public static final String TABLE_NAME = "CitiesTable";

	    public static final String ID 	   = "id";
        public static final String NAME    = "name";

	    public static final String SQL_CREATE =
	    		"CREATE TABLE IF NOT EXISTS " + TABLE_NAME
    				+ " ("
    		  		+ _ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ID     + " TEXT, "
                    + NAME   + " TEXT"
				+");";
	}

	// Tours Table
	public static final class ToursTable implements BaseColumns {

	    public static final String TABLE_NAME = "ToursTable";

	    public static final String UID 	   = "uid";
        public static final String TITLE   = "title";
        public static final String IMAGE   = "image";
        public static final String DESC    = "desc";

	    public static final String SQL_CREATE =
	    		"CREATE TABLE IF NOT EXISTS " + TABLE_NAME
    				+ " ("
    		  		+ _ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + UID     + " TEXT, "
                    + TITLE  + " TEXT, "
                    + IMAGE  + " TEXT, "
                    + DESC   + " TEXT "
				+");";
	}

	public static DatabaseHelper getInstance(Context context){
        synchronized (mLock){
            if (mInstance == null){
                mInstance = new DatabaseHelper(context);
            }
            return mInstance;
        }
    }

	private DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase db = null;

        try {
            db = super.getWritableDatabase();
        }
        catch (Exception e){
			Log.e(TAG, "getWritableDatabase() exception", e);
        }

        return db;
    }

	@Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CountriesTable.SQL_CREATE);
        db.execSQL(CitiesTable.SQL_CREATE);
        db.execSQL(ToursTable.SQL_CREATE);
    }

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.beginTransaction();

        boolean success = true;
        try {
            for (int i = oldVersion; i < newVersion; i++) {

                switch (i) {
                    case 1:
                        success = migrateTo2(db);
                        break;
                }

                if (!success) {
                    break;
                }
            }

            if (success) {
                db.setTransactionSuccessful();
            }
        }
        catch (Exception e){
            Log.e(TAG, "onUpgrade exception", e);
        }
        db.endTransaction();

        if (!success){
			Log.e(TAG, "Unable to migrate database. Clearing.");
            clearDatabase(db);
        }
	}

    public void clearDatabase(SQLiteDatabase db){
        UtilsDatabase.dropTable(db, CountriesTable.TABLE_NAME);
        UtilsDatabase.dropTable(db, CitiesTable.TABLE_NAME);
        UtilsDatabase.dropTable(db, ToursTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        clearDatabase(db);
    }

    public boolean migrateTo2(SQLiteDatabase db){
        // TODO: add your first migration here
        return true;
    }
}
