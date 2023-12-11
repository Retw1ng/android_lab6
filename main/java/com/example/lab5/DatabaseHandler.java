package com.example.lab5;

import static com.example.lab5.DataBaseFields.PlantsEntry.PLANTS_NAME;
import static com.example.lab5.DataBaseFields.PlantsEntry.TABLE_NAME;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Users.db";
    private String plants;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static ArrayList<String> getAllNames() {
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + DataBaseFields.UserEntry.TABLE_NAME + "("
                + DataBaseFields.UserEntry.COLUMN_NAME_KEY_ID + " INTEGER PRIMARY KEY," +
                DataBaseFields.UserEntry.COLUMN_NAME_LOGIN + " TEXT," + DataBaseFields.UserEntry.COLUMN_NAME_PASS + " TEXT" + ")";

        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_PANTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + DataBaseFields.UserEntry.COLUMN_NAME_KEY_ID + " INTEGER PRIMARY KEY," +
                DataBaseFields.PlantsEntry.PLANTS_NAME + " TEXT" + ")";

        db.execSQL(CREATE_PANTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseFields.UserEntry.TABLE_NAME);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        /*ContentValues values = new ContentValues();
        values.put(DataBaseFields.UserEntry.COLUMN_NAME_LOGIN, user.getLogin());
        values.put(DataBaseFields.UserEntry.COLUMN_NAME_PASS, user.getPass());

        db.insert(DataBaseFields.UserEntry.TABLE_NAME, null, values);*/
        Cursor query = db.rawQuery(" INSERT INTO " + DataBaseFields.UserEntry.TABLE_NAME + " VALUES (null,?,?) ", new String[]{user.getLogin(),user.getPass()});
        query.moveToFirst();
        db.close();
    }

    public void addPlant(User user, String plant) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseFields.UserEntry.COLUMN_NAME_KEY_ID, user.getID());
        values.put(DataBaseFields.PlantsEntry.PLANTS_NAME, plant);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public boolean selectUser(String login) {
        SQLiteDatabase db = this.getWritableDatabase();
        String dbLogin ="";
        Cursor query = db.rawQuery("SELECT * FROM " + DataBaseFields.UserEntry.TABLE_NAME + " WHERE "
                + DataBaseFields.UserEntry.COLUMN_NAME_LOGIN + "=?", new String[]{login});

        if (query.moveToFirst()) {
            dbLogin = query.getString(1);
        }

        query.close();
        db.close();

        if(dbLogin.equals(login)){
            return true;
        }else {
            return false;
        }
    }
    public boolean selectUserData(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String dbLogin = "";
        String dbPassword = "";
        Cursor query = db.rawQuery("SELECT * FROM " + DataBaseFields.UserEntry.TABLE_NAME + " WHERE "
                + DataBaseFields.UserEntry.COLUMN_NAME_LOGIN + "=?", new String[]{user.getLogin()});

        if (query.moveToFirst()) {
            dbLogin = query.getString(1);
            dbPassword = query.getString(2);
        }

        query.close();
        db.close();

        if(dbLogin.equals(user.getLogin()) && dbPassword.equals(user.getPass())){
            return true;
        }else {
            return false;
        }
    }
    public List<String> getAllNames(String plant) {
        List<String> datalist = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                //String name = cursor.getString(cursor.getColumnIndex());
                datalist.add(plants);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return datalist;
    }

    public void updateUserPassword(User user, String newPwd) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + DataBaseFields.UserEntry.TABLE_NAME + " SET " + DataBaseFields.UserEntry.COLUMN_NAME_PASS +
                " = " + "'" + newPwd + "'" + " WHERE " + DataBaseFields.UserEntry.COLUMN_NAME_LOGIN + " = " + "'" + user.getLogin() + "'";

        db.execSQL(query);
        db.close();
    }

    public void deleteUserFromDB(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + DataBaseFields.UserEntry.TABLE_NAME + " WHERE " + DataBaseFields.UserEntry.COLUMN_NAME_LOGIN + " = " + "'" + user.getLogin() + "'";
        db.execSQL(query);
        db.close();
    }

    public void deleteObjectFromDB(String plant) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + DataBaseFields.PlantsEntry.PLANTS_NAME + " = " + "'" + plant + "'";
        db.execSQL(query);
        db.close();
    }
}
