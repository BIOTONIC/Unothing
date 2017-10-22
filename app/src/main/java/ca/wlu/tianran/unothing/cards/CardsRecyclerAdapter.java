package ca.wlu.tianran.unothing.cards;

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
        view.setOnClickListener((View v) -> {
            notifyItemChanged(selectedPos);
            selectedPos = viewHolder.getLayoutPosition();
            notifyItemChanged(selectedPos);
            listener.onItemClick(v, viewHolder.getLayoutPosition());
        });
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView quesView;
        private final ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            quesView = itemView.findViewById(R.id.itemQues);
            imageView = itemView.findViewById(R.id.itemImage);
        }

        public void setQues(String ques) {
            quesView.setText(ques);
        }

        public void setImage(String image) {
            int resourceId = imageView.getResources().getIdentifier(image, "drawable", imageView.getContext().getPackageName());
            imageView.setImageResource(resourceId);
        }
    }
}
