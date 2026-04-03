package edu.uga.cs.countryquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {


    private static final String DEBUG_TAG = "DBHelper";

    private static final String DB_NAME = "countriesandquizzes.db";
    private static final int DB_VERSION = 1;


    public static final String TABLE_COUNTRIES = "countries";
    public static final String COUNTRIES_COLUMN_COUNTRYID = "countryId";
    public static final String COUNTRIES_COLUMN_COUNTRYNAME = "countryName";
    public static final String COUNTRIES_COLUMN_CAPITALNAME = "capitalName";
    public static final String COUNTRIES_COLUMN_CONTINENTNAME = "continentName";
    public static final String COUNTRIES_COLUMN_ABBREVIATION = "abbreviation";



    public static final String TABLE_QUIZZES = "quizzes";
    public static final String QUIZZES_COLUMN_QUIZID = "quizId";
    public static final String QUIZZES_COLUMN_QUIZDATE = "quizDate";
    public static final String QUIZZES_COLUMN_QUIZRESULT = "quizResult";


    private static DBHelper helperInstance;


    private static final String CREATE_COUNTRIES =
            "create table " + TABLE_COUNTRIES + " ("
                    + COUNTRIES_COLUMN_COUNTRYID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COUNTRIES_COLUMN_COUNTRYNAME + " TEXT, "
                    + COUNTRIES_COLUMN_CAPITALNAME + " TEXT, "
                    + COUNTRIES_COLUMN_CONTINENTNAME + " TEXT, "
                    + COUNTRIES_COLUMN_ABBREVIATION + " TEXT"
                    + ")";



    private static final String CREATE_QUIZZES =
            "create table " + TABLE_QUIZZES + " ("
                    + QUIZZES_COLUMN_QUIZID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUIZZES_COLUMN_QUIZDATE + " TEXT, "
                    + QUIZZES_COLUMN_QUIZRESULT + " INTEGER"
                    + ")";


    private DBHelper( Context context ) {
        super( context, DB_NAME, null, DB_VERSION );
    }


    public synchronized static DBHelper getInstance( Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new DBHelper( context.getApplicationContext() );
        }
        return helperInstance;
    }


    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL( CREATE_COUNTRIES );
        Log.d( DEBUG_TAG, "Table " + TABLE_COUNTRIES + " created" );
        db.execSQL( CREATE_QUIZZES );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZZES + " created" );
    }


    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "drop table if exists " + TABLE_COUNTRIES );
        db.execSQL( "drop table if exists " + TABLE_QUIZZES );
        onCreate( db );
        Log.d( DEBUG_TAG, "Table " + TABLE_COUNTRIES + " upgraded" );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZZES + " upgraded" );
    }
}
