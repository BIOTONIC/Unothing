package ca.wlu.tianran.unothing.cards;

import android.content.Intent;

public interface CardsContract {
    interface Presenter{
        String getQues();

        String getAnsw();

        int getLastIndex();

        void setCurrIndex(int curr);

        void getNextCard();

        Boolean addCard(Intent data, String KEY);
    }

    interface View{
        void hideQues();

        void hideAnsw();

        void showImg(String name);
    }
}
