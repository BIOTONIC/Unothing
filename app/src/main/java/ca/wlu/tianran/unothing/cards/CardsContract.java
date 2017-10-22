package ca.wlu.tianran.unothing.cards;

import android.content.Intent;
import android.view.View;

public interface CardsContract {
    interface Presenter {
        // this.listView is the copy of the reference of input listView
        // so this.listView.setPresenter(this) equals listView.setPresenter(this)
        void setListView(ListView listView);

        void setDetailView(DetailView detailView);

        String getQues();

        String getAnsw();

        int getLastIndex();

        void getCard(int i);

        void getCurrCard();

        void getNextCard();

        Boolean addCard(Intent data, String KEY);

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
    }

    interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
