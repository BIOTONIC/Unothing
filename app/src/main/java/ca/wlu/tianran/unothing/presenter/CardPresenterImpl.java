package ca.wlu.tianran.unothing.presenter;

import ca.wlu.tianran.unothing.model.CardModel;
import ca.wlu.tianran.unothing.model.ICard;
import ca.wlu.tianran.unothing.view.ICardView;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class CardPresenterImpl implements ICardPresenter {
    private ICardView cardView;
    private ArrayList<ICard> cards;
    private int len;
    private int curr;

    public CardPresenterImpl(ICardView iCardView, String[] ids, String[] quess, String[] answs) {
        this.cardView = iCardView;
        len = Math.min(Math.min(ids.length, quess.length), answs.length);
        cards = new ArrayList<ICard>(len);
        curr = -1;

        for (int i = 0; i < len; i++) {
            cards.add(new CardModel(i, "img" + ids[i], quess[i], answs[i]));
        }
    }

    @Override
    public String getQues() {
        return cards.get(curr).getQues();
    }

    @Override
    public String getAnsw() {
        return cards.get(curr).getAnsw();
    }

    @Override
    public int getLast() {
        int last = curr - 1;
        if (last < 0) {
            last = len - 1;
        }
        return last;
    }

    @Override
    public void setCurr(int curr) {
        this.curr = curr;
    }

    @Override
    public void showNext() {
        curr += 1;
        if (curr >= len) {
            curr = 0;
        }
        cardView.showImg(cards.get(curr).getImage());
        cardView.hideQues();
        cardView.hideAnsw();
    }

    @Override
    public Boolean addCard(String newQues, String newAnsw, String newImage) {
        if (newQues != null && newAnsw != null && Pattern.matches("img[0-7]", newImage)) {
            ICard newCard = new CardModel(len, newImage, newQues, newAnsw);
            cards.add(newCard);
            len = cards.size();
            return true;
        } else {
            return false;
        }
    }
}
