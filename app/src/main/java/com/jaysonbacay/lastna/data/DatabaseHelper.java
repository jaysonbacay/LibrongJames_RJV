package com.jaysonbacay.lastna.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.jaysonbacay.lebrongjames/databases/";
    private static String DB_NAME = "librongjames.db";

    private SQLiteDatabase myDatabase;

    private final Context myContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist) {
            Log.i("DatabaseHelper", "DB is existing. NOT COPYING");
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e("DatabaseHelper", e.getMessage());
                throw new Error("Error copying database");

            }
        }

    }

    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                File dbFile = myContext.getDatabasePath(DB_NAME);
                myPath = dbFile.getPath();
                Log.i("DatabaseHelper", "here1: " + myPath);

            } else {
                myPath = DB_PATH + DB_NAME;
                Log.i("DatabaseHelper", "here2: " + myPath);
            }
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS);

        } catch (SQLiteException e) {

            Log.e("DatabaseHelper", e.getLocalizedMessage());

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        Log.i("DatabaseHelper", "DB is not existing. COPYING");
        InputStream myInput = myContext.getAssets().open(DB_NAME);


        String outFileName = DB_PATH + DB_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }
    public void addBookFromHome(BookModel book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("libBookName", book.bookName);
        values.put("libBookAuthor", book.bookAuthor);
        values.put("libBookGenre", book.bookGenre);
        values.put("libBookPublishDate", book.bookPublishDate);
        values.put("libImage", book.image);


        db.insert("tblLibrary", null, values);
        db.close();
    }

    public List<BookModel> getAllBooks() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<BookModel> data = new ArrayList<>();

        Cursor cursor;

        try {
            cursor = db.query("tblBooks", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                BookModel book = new BookModel(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
                data.add(book);
            }
            Log.i("DatabaseHelper", "" + data);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }

        return data;
    }
    public List<BookModel> searchBooks(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<BookModel> data = new ArrayList<>();

        Cursor cursor = null;

        try {
            cursor = db.query("tblBooks", null, "bookName LIKE ? OR bookAuthor LIKE ?", new String[]{"%" + query + "%", "%" + query + "%"}, null, null, null);

            while (cursor.moveToNext()) {
                BookModel book = new BookModel(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
                data.add(book);
            }

            Log.i("DatabaseHelper", "Query" + query);

        } catch (Exception e) {
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return data;

    }
    public List<LibraryModel> getAllAddedBooks() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<LibraryModel> data = new ArrayList<>();

        Cursor cursor;



        try {
            cursor = db.query("tblLibrary", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                LibraryModel book = new LibraryModel(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6));
                data.add(book);
            }
            Log.i("DatabaseHelper", "" + data);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }

        return data;
    }
    public void deleteBookFromLibrary(String bookName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tblLibrary", "libBookName=?", new String[]{bookName});
        db.close();
    }}