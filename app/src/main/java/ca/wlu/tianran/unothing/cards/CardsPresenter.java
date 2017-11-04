package ca.wlu.tianran.unothing.cards;

import android.graphics.Color;
import android.view.View;
import ca.wlu.tianran.unothing.data.Card;
import ca.wlu.tianran.unothing.data.CardsRepository;
import android.content.Intent;

import java.io.Serializable;
import java.util.regex.Pattern;

public class CardsPresenter implements CardsContract.Presenter, Serializable {
    private transient CardsRepository cardsRepository;
    private transient CardsContract.ListView listView;
    private transient CardsContract.DetailView detailView;

    private int curr;

    public CardsPresenter(CardsRepository cardsRepository) {
        this.cardsRepository = cardsRepository;
        this.cardsRepository.loadCards();
    }

    @Override
    public void setListView(CardsContract.ListView listView) {
        this.listView = listView;
    }

    @Override
    public void setDetailView(CardsContract.DetailView detailView) {
        this.detailView = detailView;
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
    public void loadCard(int i) {
        if (i < cardsRepository.getSize()) {
            curr = i;
        } else {
            curr = 0;
        }
        loadCurrCard();
    }

    @Override
    public void loadCurrCard() {
        detailView.showImg(cardsRepository.getImage(curr));
        detailView.hideQues();
        detailView.hideAnsw();
        detailView.setTitle(curr);
    }

    @Override
    public void loadNextCard() {
        curr += 1;
        if (curr >= cardsRepository.getSize()) {
            curr = 0;
        }
        loadCurrCard();
    }

    public Boolean addCard(Card card) {
        if (card.getQues() != null && card.getAnsw() != null && Pattern.matches("img[0-7]", card.getImage())) {
            return cardsRepository.addCard(card);
        }
        return false;
    }

    @Override
    public void deleteCard(View view, int pos) {
        Card card = cardsRepository.getCard(pos);
        boolean result = cardsRepository.deleteCard(pos);
        if (card != null && result) {
            listView.onDeleteCard(view, card);
        }
    }

    @Override
    public void onBindListViewHolder(CardsRecyclerAdapter.ViewHolder holder, int position, int selectedPos) {
        if (CardsActivity.isLargeScreen) {
            if (selectedPos == position) {
                holder.itemView.setSelected(true);
                holder.itemView.setBackgroundColor(Color.GRAY);
            } else {
                holder.itemView.setSelected(false);
                holder.itemView.setBackgroundColor(Color.WHITE);
            }
        }
        holder.setQues(cardsRepository.getQues(position));
        holder.setImage(cardsRepository.getImage(position));
    }

    @Override
    public int getListItemCount() {
        return cardsRepository.getSize();
    }
}
