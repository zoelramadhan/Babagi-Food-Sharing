package com.example.babagi.config;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DbConfig extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_babagii";
    private static final int DATABASE_VERSION = 1;
    public static final String USERS_TABLE_NAME = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String FOODS_TABLE_NAME = "saves";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_FOOD_ID = "food_id";

    public DbConfig(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USERS_TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT UNIQUE,"
                + COLUMN_PASSWORD + " TEXT)");

        db.execSQL("CREATE TABLE " + FOODS_TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_ID + " INTEGER,"
                + COLUMN_FOOD_ID + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public Cursor insertUser(String name, String email, String password) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO " + USERS_TABLE_NAME + " (" + COLUMN_NAME + ", " + COLUMN_EMAIL + ", " + COLUMN_PASSWORD + ") VALUES ('" + name + "', '" + email + "', '" + password + "')");
        return db.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = '" + email + "'", null);
    }

    public Cursor login(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = '" + email + "' AND " + COLUMN_PASSWORD + " = '" + password + "'", null);
    }

    public Cursor getUserDataById(int userId) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + COLUMN_ID + " = " + userId, null);
    }

    public void updateProfile(int userId, String name, String email, String password) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + USERS_TABLE_NAME + " SET " + COLUMN_NAME + " = '" + name + "', " + COLUMN_EMAIL + " = '" + email + "', " + COLUMN_PASSWORD + " = '" + password + "' WHERE " + COLUMN_ID + " = " + userId);
        db.close();
    }

    public void insertSave(int userId, int foodId) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO " + FOODS_TABLE_NAME + " (" + COLUMN_USER_ID + ", " + COLUMN_FOOD_ID + ") VALUES (" + userId + ", " + foodId + ")");
        db.close();
    }

    public Cursor getSavesByUserId(int userId) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + FOODS_TABLE_NAME + " WHERE " + COLUMN_USER_ID + " = " + userId, null);
    }

    public boolean isSave(int userId, int foodId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + FOODS_TABLE_NAME + " WHERE " + COLUMN_USER_ID + " = " + userId + " AND " + COLUMN_FOOD_ID + " = " + foodId, null);
        return cursor.getCount() > 0;
    }

    public void deleteSave(int userId, int foodId) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + FOODS_TABLE_NAME + " WHERE " + COLUMN_USER_ID + " = " + userId + " AND " + COLUMN_FOOD_ID + " = " + foodId);
        db.close();
    }

}
