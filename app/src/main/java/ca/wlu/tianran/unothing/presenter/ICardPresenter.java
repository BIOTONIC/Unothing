package ca.wlu.tianran.unothing.presenter;


public interface ICardPresenter {
    String getQues();
    String getAnsw();
    int getLast();
    void setCurr(int curr);
    void showNext();
    Boolean addCard(String newQues, String newAnsw, String newImage);
}
