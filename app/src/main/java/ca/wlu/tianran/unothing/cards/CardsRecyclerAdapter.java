package ca.wlu.tianran.unothing.cards;

import android.support.v7.widget.CardView;
import android.widget.Button;
import ca.wlu.tianran.unothing.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CardsRecyclerAdapter extends RecyclerView.Adapter<CardsRecyclerAdapter.ViewHolder> {

    private final CardsContract.Presenter presenter;
    private final CardsContract.ItemClickListener listener;
    public static int selectedPos = 0;

    public CardsRecyclerAdapter(CardsContract.Presenter presenter, CardsContract.ItemClickListener listener) {
        this.presenter = presenter;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_card_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        presenter.onBindListViewHolder(holder, position, selectedPos);
    }

    @Override
    public int getItemCount() {
        return presenter.getListItemCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final CardView cardView;
        private final TextView quesView;
        private final ImageView imageView;
        public final Button swipeBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.itemCard);
            quesView = itemView.findViewById(R.id.itemQues);
            imageView = itemView.findViewById(R.id.itemImage);
            swipeBtn = itemView.findViewById(R.id.swipeBtn);

            cardView.setOnClickListener(this);
            swipeBtn.setOnClickListener(this);
        }

        public void setQues(String ques) {
            quesView.setText(ques);
        }

        public void setImage(String image) {
            int resourceId = imageView.getResources().getIdentifier(image, "drawable", imageView.getContext().getPackageName());
            imageView.setImageResource(resourceId);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.itemCard:
                    notifyItemChanged(selectedPos);
                    selectedPos = getLayoutPosition();
                    notifyItemChanged(selectedPos);
                    listener.onItemClick(v, getLayoutPosition());
                    break;

                case R.id.swipeBtn:
                    int pos = getAdapterPosition();
                    presenter.deleteCard(v,pos);
                    notifyItemRemoved(pos);
                    break;
            }
        }
    }
}
