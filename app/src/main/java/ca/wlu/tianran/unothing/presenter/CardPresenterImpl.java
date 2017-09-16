package ca.wlu.tianran.unothing.presenter;

import ca.wlu.tianran.unothing.model.CardModel;
import ca.wlu.tianran.unothing.model.ICard;
import ca.wlu.tianran.unothing.view.ICardView;

public class CardPresenterImpl implements ICardPresenter {
    private ICardView cardView;
    private ICard[] cards;
    private int len;
    private int cur;

    public CardPresenterImpl(ICardView iCardView, String[] ids, String[] quess, String[] answs) {
        this.cardView = iCardView;
        len = Math.min(Math.min(ids.length, quess.length), answs.length);
        cards = new ICard[len];
        cur = -1;

        for (int i = 0; i < len; i++) {
            cards[i] = new CardModel(i, "img" + ids[i], quess[i], answs[i]);
        }
    }

    @Override
    public String getQues() {
        return cards[cur].getQuestion();
    }

    @Override
    public String getAnsw() {
        return cards[cur].getAnswer();
    }

    @Override
    public void showNext() {
        cur += 1;
        if (cur >= len) {
            cur = 0;
        }
        cardView.showImg(cards[cur].getImage());
        cardView.hideQues();
        cardView.hideAnsw();
    }
}
