package ca.wlu.tianran.unothing.cards;

import android.content.Intent;
import android.view.View;
import ca.wlu.tianran.unothing.data.Card;

public interface CardsContract {
    interface Presenter {
        void setListView(ListView listView);

        void setDetailView(DetailView detailView);

        String getQues();

        String getAnsw();

        int getLastIndex();

        void loadCard(int i);

        void loadCurrCard();

        void loadNextCard();

        Boolean addCard(Card card);

        void deleteCard(View view, int pos);

        void onBindListViewHolder(CardsRecyclerAdapter.ViewHolder holder, int position, int selectedPos);

        int getListItemCount();
    }

    interface DetailView {
        void hideQues();

        void hideAnsw();

        void showImg(String name);

        void setTitle(int curr);
    }

    interface ListView {
        void onDeleteCard(View view, Card card);
    }

    interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
