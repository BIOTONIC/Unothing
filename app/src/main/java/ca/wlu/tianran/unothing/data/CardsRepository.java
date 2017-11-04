package ca.wlu.tianran.unothing.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import java.util.ArrayList;


public class CardsRepository {
    private static CardsRepository INSTANCE = null;

    private DbHelper dbHelper;
    private ArrayList<Card> cards;

    private CardsRepository(Context context) {
        dbHelper = new DbHelper(context);
    }

    public static synchronized CardsRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CardsRepository(context);
        }
        return INSTANCE;
    }

    public String getIdByPos(int i) {
        return cards.get(i).getId();
    }

    public String getQues(int i) {
        return cards.get(i).getQues();
    }

    public String getAnsw(int i) {
        return cards.get(i).getAnsw();
    }

    public String getImage(int i) {
        return cards.get(i).getImage();
    }

    public int getSize() {
        return cards.size();
    }

    public void loadCards() {
        cards = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DbHelper.COL_ID, DbHelper.COL_IMAGE,
                DbHelper.COL_QUES, DbHelper.COL_ANSW};
        Cursor c = db.query(DbHelper.TABLE_CARD, projection,
                null, null, null, null, null);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String id = c.getString(c.getColumnIndexOrThrow(DbHelper.COL_ID));
                String image = c.getString(c.getColumnIndexOrThrow(DbHelper.COL_IMAGE));
                String ques = c.getString(c.getColumnIndexOrThrow(DbHelper.COL_QUES));
                String answ = c.getString(c.getColumnIndexOrThrow(DbHelper.COL_ANSW));
                Card card = new Card(id, image, ques, answ);
                cards.add(card);
            }
        }
        if (c != null) {
            c.close();
        }
        db.close();
    }

    public Card getCard(int pos) {
        return cards.get(pos);
    }

    public boolean addCard(Card card) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.COL_ID, card.getId());
        values.put(DbHelper.COL_IMAGE, card.getImage());
        values.put(DbHelper.COL_QUES, card.getQues());
        values.put(DbHelper.COL_ANSW, card.getAnsw());
        long result = db.insert(DbHelper.TABLE_CARD, null, values);
        db.close();

        loadCards();

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteCard(int pos) {
        String id = cards.get(pos).getId();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = DbHelper.COL_ID + " like ?";
        String[] selectionArgs = {id};
        int result = db.delete(dbHelper.TABLE_CARD, selection, selectionArgs);
        db.close();

        loadCards();

        if (result == 0) {
            return false;
        } else {
            return true;
        }
    }
}
