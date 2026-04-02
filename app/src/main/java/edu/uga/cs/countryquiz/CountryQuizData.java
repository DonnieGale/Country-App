package edu.uga.cs.countryquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;

public class CountryQuizData {

    public static final String DEBUG_TAG = "CountryQuizData";

    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;

    // Countries table columns
    private static final String[] countryColumns = {
            DBHelper.COUNTRIES_COLUMN_COUNTRYID,
            DBHelper.COUNTRIES_COLUMN_COUNTRYNAME,
            DBHelper.COUNTRIES_COLUMN_CAPITALNAME,
            DBHelper.COUNTRIES_COLUMN_CONTINENTNAME,
            DBHelper.COUNTRIES_COLUMN_ABBREVIATION
    };

    // Quizzes table columns
    private static final String[] quizColumns = {
            DBHelper.QUIZZES_COLUMN_QUIZID,
            DBHelper.QUIZZES_COLUMN_QUIZDATE,
            DBHelper.QUIZZES_COLUMN_QUIZRESULT
    };

    public CountryQuizData(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    // Open database
    public void open() {
        db = dbHelper.getWritableDatabase();
        Log.d(DEBUG_TAG, "CountryQuizData: DB opened");
    }

    // Close database
    public void close() {
        if ( dbHelper != null ) {
            dbHelper.close();
            Log.d(DEBUG_TAG, "CountryQuizData: DB closed");
        }
    }

    public boolean isDBOpen() { return db.isOpen(); }



    // Retrieve all countries from the database
    public List<Country> retrieveAllCountries() {

        ArrayList<Country> countries = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            cursor = db.query(DBHelper.TABLE_COUNTRIES,
                    countryColumns,
                    null, null, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {

                    if (cursor.getColumnCount() >= 5) {

                        // get values using column indices
                        columnIndex = cursor.getColumnIndex( DBHelper.COUNTRIES_COLUMN_COUNTRYID );
                        long id = cursor.getLong( columnIndex );

                        columnIndex = cursor.getColumnIndex( DBHelper.COUNTRIES_COLUMN_COUNTRYNAME );
                        String name = cursor.getString( columnIndex );

                        columnIndex = cursor.getColumnIndex( DBHelper.COUNTRIES_COLUMN_CAPITALNAME );
                        String capital = cursor.getString( columnIndex );

                        columnIndex = cursor.getColumnIndex( DBHelper.COUNTRIES_COLUMN_CONTINENTNAME );
                        String continent = cursor.getString( columnIndex );

                        columnIndex = cursor.getColumnIndex( DBHelper.COUNTRIES_COLUMN_ABBREVIATION );
                        String abbreviation = cursor.getString( columnIndex );

                        // create Country object
                        Country country = new Country( name, capital, continent, abbreviation );
                        country.setId(id);

                        countries.add(country);

                        Log.d( DEBUG_TAG, "Retrieved Country: " + country );
                    }
                }
            }

            if ( cursor != null )
                Log.d( DEBUG_TAG, "Number of records from DB: " + cursor.getCount()) ;
            else
                Log.d( DEBUG_TAG, "Number of records from DB: 0" );

        } catch (Exception e) {
            Log.d( DEBUG_TAG, "Exception caught: " + e );
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return countries;
    }


    // Store a new quiz in the database
    public Quiz storeQuiz(Quiz quiz) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.QUIZZES_COLUMN_QUIZDATE, quiz.getQuizDate());
        values.put(DBHelper.QUIZZES_COLUMN_QUIZRESULT, quiz.getQuizResult());

        long id = db.insert(DBHelper.TABLE_QUIZZES, null, values);
        quiz.setId(id);

        Log.d(DEBUG_TAG, "Stored quiz with id: " + id);

        return quiz;
    }


    // Retrieve all quizzes from the database
    public List<Quiz> retrieveAllQuizzes() {

        ArrayList<Quiz> quizzes = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            cursor = db.query(DBHelper.TABLE_QUIZZES,
                    quizColumns,
                    null, null, null, null,
                    DBHelper.QUIZZES_COLUMN_QUIZID + " DESC");

            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {

                    if (cursor.getColumnCount() >= 3) {

                        columnIndex = cursor.getColumnIndex(DBHelper.QUIZZES_COLUMN_QUIZID);
                        long id = cursor.getLong(columnIndex);

                        columnIndex = cursor.getColumnIndex(DBHelper.QUIZZES_COLUMN_QUIZDATE);
                        String date = cursor.getString(columnIndex);

                        columnIndex = cursor.getColumnIndex(DBHelper.QUIZZES_COLUMN_QUIZRESULT);
                        int result = cursor.getInt(columnIndex);

                        Quiz quiz = new Quiz(date, result);
                        quiz.setId(id);

                        quizzes.add(quiz);

                        Log.d(DEBUG_TAG, "Retrieved Quiz: " + quiz);
                    }
                }
            }

            if (cursor != null)
                Log.d(DEBUG_TAG, "Number of quiz records: " + cursor.getCount());
            else
                Log.d(DEBUG_TAG, "Number of quiz records: 0");

        } catch (Exception e) {
            Log.d(DEBUG_TAG, "Exception caught: " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return quizzes;
    }





    public void loadCountriesFromCSV(Context context) {

        try {
            InputStream is = context.getAssets().open("countries_data.csv");
            CSVReader reader = new CSVReader(new InputStreamReader(is));

            String[] NextRow;


            while ((NextRow = reader.readNext()) != null) {

                // CSV format:
                // country, capital, continent, abbreviation

                String countryName = NextRow[0];
                String capitalName = NextRow[1];
                String continentName = NextRow[2];
                String abbreviation = NextRow[3];

                ContentValues values = new ContentValues();
                values.put(DBHelper.COUNTRIES_COLUMN_COUNTRYNAME, countryName);
                values.put(DBHelper.COUNTRIES_COLUMN_CAPITALNAME, capitalName);
                values.put(DBHelper.COUNTRIES_COLUMN_CONTINENTNAME, continentName);
                values.put(DBHelper.COUNTRIES_COLUMN_ABBREVIATION, abbreviation);

                db.insert(DBHelper.TABLE_COUNTRIES, null, values);
            }

            reader.close();

            Log.d(DEBUG_TAG, "Countries loaded from assets CSV");

        } catch (Exception e) {
            Log.e(DEBUG_TAG, "Error loading CSV from assets", e);
        }
    }
}
