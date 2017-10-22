package ca.wlu.tianran.unothing.cards;

import android.graphics.Color;
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

    // this.listView is the copy of the reference of input listView
    // so this.listView.setPresenter(this) equals listView.setPresenter(this)
    @Override
    public void setListView(CardsContract.ListView listView) {
        this.listView = listView;
        //this.listView.setPresenter(this);
    }

    @Override
    public void setDetailView(CardsContract.DetailView detailView) {
        this.detailView = detailView;
        //this.detailView.setPresenter(this);
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
    public void getCard(int i) {
        if (i < cardsRepository.getSize()) {
            curr = i;
        } else {
            curr = 0;
        }
        getCurrCard();
    }

    @Override
    public void getCurrCard() {
        detailView.showImg(cardsRepository.getImage(curr));
        detailView.hideQues();
        detailView.hideAnsw();
        detailView.setTitle(curr);
    }

    @Override
    public void getNextCard() {
        curr += 1;
        if (curr >= cardsRepository.getSize()) {
            curr = 0;
        }
        getCurrCard();
    }

    @Override
    public Boolean addCard(Intent data, String KEY) {
        Card card = data.getParcelableExtra(KEY);
        if (card.getQues() != null && card.getAnsw() != null && Pattern.matches("img[0-7]", card.getImage())) {
            cardsRepository.addCard(card);
            return true;
        }
        return false;
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
