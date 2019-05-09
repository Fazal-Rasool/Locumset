package com.adaxiom.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.adaxiom.models.ModelLogin;
import com.adaxiom.models.ModelUser;
import com.adaxiom.utils.Locumset;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "LocumsetDB.db";
    SQLiteDatabase db;
    public static DatabaseHelper database;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = getReadableDatabase();
    }

    public static DatabaseHelper getInstance() {
        if (database == null) {
            database = new DatabaseHelper(Locumset.getAppContext());
        }
        return database;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            db.execSQL("CREATE TABLE User (" +
                    "user_id text" +
                    ",user_name text" +
                    ",user_last_name text" +
                    ",user_email text" +
                    ",user_gmc text" +
                    ",user_phone text" +
                    ",user_pass text" +
                    ",user_image text" +
                    ");");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onDELETE(SQLiteDatabase db) {
        try {

            db.execSQL("DELETE FROM  User;");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXIST User");
            onCreate(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean saveUser(ModelLogin model) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put("user_id", model.user_id + "");
        values.put("user_name", model.first_name);
        values.put("user_last_name", model.last_name);
        values.put("user_email", model.email);
        values.put("user_gmc", model.gmc_number);
        values.put("user_phone", model.phone_no);
        values.put("user_pass", model.password);
        values.put("user_image", model.image_path);

        if (DataAlreadyInDBorNot("User", "user_id", model.user_id, db)) {
            db.update("User", values, "user_id=?",
                    new String[]{model.user_id + ""});
        } else {
            db.insert("User", null, values);
        }

        return true;
    }


//    public boolean updateShop(String shopID) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("isVisited", "1");
//        db.update("ShopList", values, "ShopId=?",
//                new String[]{shopID});
//
//        return true;
//    }


    public boolean updateUser(ModelLogin model) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put("user_phone", model.phone_no);
        values.put("user_image", model.password);

        if (DataAlreadyInDBorNot("User", "user_id", model.user_id, db)) {
            db.update("User", values, "user_id=?",
                    new String[]{model.user_id + ""});
        }
//        else {
//            db.insert("User", null, values);
//        }

        return true;
    }



    public boolean DataAlreadyInDBorNot(String TableName, String dbfield1, int dbfield2, SQLiteDatabase db) {

        SQLiteDatabase sqldb = db;
        String Query = "SELECT * FROM " + TableName + " WHERE " + dbfield1 + "='" + dbfield2 + "'";
        Cursor cursor = sqldb.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    public ModelUser getUserList(int sId) {

        List<ModelUser> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor maincursor = null;
        ModelUser model = new ModelUser();
        ;
        maincursor = db.rawQuery("SELECT * FROM User WHERE user_id ='" + sId + "'", null);
        if (maincursor.moveToFirst()) {

//            do {

            model.userId = maincursor.getString(maincursor.getColumnIndex("user_id"));
            model.userName = maincursor.getString(maincursor.getColumnIndex("user_name"));
            model.userLastName = maincursor.getString(maincursor.getColumnIndex("user_last_name"));
            model.userEmail = maincursor.getString(maincursor.getColumnIndex("user_email"));
            model.userGmc = maincursor.getString(maincursor.getColumnIndex("user_gmc"));
            model.userPhone = maincursor.getString(maincursor.getColumnIndex("user_phone"));
            model.userPass = maincursor.getString(maincursor.getColumnIndex("user_pass"));
            model.userImage = maincursor.getString(maincursor.getColumnIndex("user_image"));

            list.add(model);

//            } while (maincursor.moveToNext());

            db.close();
        }

        return model;
    }

    public void emptyUserTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from User");
    }


    public void deletSpecificDisplayData(int sId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete FROM User WHERE user_id ='" + sId + "'");
    }
}
