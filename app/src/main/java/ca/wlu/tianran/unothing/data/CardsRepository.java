package ca.wlu.tianran.unothing.data;

import android.content.Context;
import ca.wlu.tianran.unothing.R;

import java.util.ArrayList;

public class CardsRepository {
    private static CardsRepository INSTANCE = null;

    private ArrayList<Card> cards;
    private String[] ids;
    private String[] quess;
    private String[] answs;

    private CardsRepository(Context context) {
        ids = context.getResources().getStringArray(R.array.id);
        quess = context.getResources().getStringArray(R.array.ques);
        answs = context.getResources().getStringArray(R.array.answ);
    }

    public static synchronized CardsRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CardsRepository(context);
        }
        return INSTANCE;
    }

    public void loadCards() {
        if (cards == null) {
            int len = Math.min(Math.min(ids.length, quess.length), answs.length);
            cards = new ArrayList<>(len);
            for (int i = 0; i < len; i++) {
                cards.add(new Card(i, "img" + ids[i], quess[i], answs[i]));
            }
        }
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

    public int getSize(){
        return cards.size();
    }
    public void addCard(Card card) {
        cards.add(card);
    }
}
