package ca.wlu.tianran.unothing.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.UUID;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "unothing.db";
    public static final String TABLE_CARD = "card";
    public static final String COL_ID = "id";
    public static final String COL_IMAGE = "image";
    public static final String COL_QUES = "ques";
    public static final String COL_ANSW = "answ";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // is called when the db file didn't exist and was just created
    @Override
    public void onCreate(SQLiteDatabase db) {
        createCardTable(db);
        initCardTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // not required as at version 1
    }

    private void createCardTable(SQLiteDatabase db) {
        String sql = "create table if not exists " + TABLE_CARD + " (" +
                COL_ID + " text primary key, " +
                COL_IMAGE + " text not null, " +
                COL_QUES + " text not null, " +
                COL_ANSW + " text not null)";
        db.execSQL(sql);
    }

    private void initCardTable(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, UUID.randomUUID().toString());
        values.put(COL_IMAGE, "img0");
        values.put(COL_QUES, "What is his real name?");
        values.put(COL_ANSW, "Aegon Targaryen");
        db.insert(TABLE_CARD, null, values);

        values = new ContentValues();
        values.put(COL_ID, UUID.randomUUID().toString());
        values.put(COL_IMAGE, "img1");
        values.put(COL_QUES, "What is his nickname?");
        values.put(COL_ANSW, "Kingslayer");
        db.insert(TABLE_CARD, null, values);

        values = new ContentValues();
        values.put(COL_ID, UUID.randomUUID().toString());
        values.put(COL_IMAGE, "img2");
        values.put(COL_QUES, "He saved whom in \nThe Spoils of War(S7E4)?");
        values.put(COL_ANSW, "Jaime Lannister");
        db.insert(TABLE_CARD, null, values);

        values = new ContentValues();
        values.put(COL_ID, UUID.randomUUID().toString());
        values.put(COL_IMAGE, "img3");
        values.put(COL_QUES, "In the destruction of \nthe Sept, " +
                "she killed all of \nHouse Tyrell except whom?");
        values.put(COL_ANSW, "Olenna Tyrell");
        db.insert(TABLE_CARD, null, values);

        values = new ContentValues();
        values.put(COL_ID, UUID.randomUUID().toString());
        values.put(COL_IMAGE, "img4");
        values.put(COL_QUES, "What is her title right now?");
        values.put(COL_ANSW, "Lady of Winterfell");
        db.insert(TABLE_CARD, null, values);

        values = new ContentValues();
        values.put(COL_ID, UUID.randomUUID().toString());
        values.put(COL_IMAGE, "img5");
        values.put(COL_QUES, "Where did he born?");
        values.put(COL_ANSW, "Flea Bottom");
        db.insert(TABLE_CARD, null, values);

        values = new ContentValues();
        values.put(COL_ID, UUID.randomUUID().toString());
        values.put(COL_IMAGE, "img6");
        values.put(COL_QUES, "What is the name of \nher sword?");
        values.put(COL_ANSW, "Oathkeeper");
        db.insert(TABLE_CARD, null, values);

        values = new ContentValues();
        values.put(COL_ID, UUID.randomUUID().toString());
        values.put(COL_IMAGE, "img7");
        values.put(COL_QUES, "After whose advice, \nshe brought Jon Snow \nback to life?");
        values.put(COL_ANSW, "Onion Knight");
        db.insert(TABLE_CARD, null, values);
    }
}
