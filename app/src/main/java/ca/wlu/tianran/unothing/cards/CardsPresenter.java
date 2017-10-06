package ca.wlu.tianran.unothing.cards;

import android.content.Intent;
import ca.wlu.tianran.unothing.data.Card;
import ca.wlu.tianran.unothing.data.CardsRepository;

import java.util.regex.Pattern;

public class CardsPresenter implements CardsContract.Presenter {
    private CardsRepository cardsRepository;
    private CardsContract.View cardsView;

    private int curr;

    public CardsPresenter(CardsRepository cardsRepository, CardsContract.View cardsView) {
        this.cardsRepository = cardsRepository;
        cardsRepository.loadCards();
        this.cardsView = cardsView;
    }

    @Override
    public String getQues() {
        return cardsRepository.getQues(curr);
    }

    @Override
    public String getAnsw() {
        return cardsRepository.getAnsw(curr);
    }

    @Override
    public int getLastIndex() {
        int last = curr - 1;
        if (last < 0) {
            last = cardsRepository.getSize() - 1;
        }
        return last;
    }

    @Override
    public void setCurrIndex(int curr) {
        this.curr = curr;
    }

    @Override
    public void getNextCard() {
        curr += 1;
        if (curr >= cardsRepository.getSize()) {
            curr = 0;
        }
        cardsView.showImg(cardsRepository.getImage(curr));
        cardsView.hideQues();
        cardsView.hideAnsw();
    }

    @Override
    public Boolean addCard(Intent data, String KEY) {
        Card card = data.getParcelableExtra(KEY);
        if (card.getQues() != null && card.getAnsw() != null && Pattern.matches("img[0-7]", card.getImage())) {
            cardsRepository.addCard(card);
            return true;
        } else {
            return false;
        }
    }
}
