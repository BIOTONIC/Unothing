package ca.wlu.tianran.unothing.cards;

public interface CardsContract {
    interface Presenter{
        String getQues();

        String getAnsw();

        int getLastIndex();

        void setCurrIndex(int curr);

        void getNextCard();

        Boolean addCard(String newQues, String newAnsw, String newImage);
    }

    interface View{
        void hideQues();

        void hideAnsw();

        void showImg(String name);
    }
}
