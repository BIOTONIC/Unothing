package ca.wlu.tianran.unothing.cards;

import android.support.design.widget.Snackbar;
import ca.wlu.tianran.unothing.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ca.wlu.tianran.unothing.data.Card;
import ca.wlu.tianran.unothing.util.SimpleDividerItemDecoration;
import ca.wlu.tianran.unothing.util.SwipeItemLayout;

import java.io.Serializable;

public class CardListFragment extends Fragment
        implements CardsContract.ListView, CardsContract.ItemClickListener {
    private final static String PRESENTER = "presenter";
    private final static String SELECTED_POS = "selected_pos";

    private CardsContract.Presenter cardsPresenter;
    private RecyclerView.Adapter recyclerAdapter;
    private ActionBar actionBar;

    // system will call the public empty constructor after orientation has changed
    // use savedInstanceState to restore state
    // https://stackoverflow.com/questions/9245408/best-practice-for-instantiating-a-new-android-fragment
    public CardListFragment() {
    }

    // manually call static factory method to create a fragment
    // use getArguments to restore state
    public static CardListFragment newInstance(Serializable presenter) {
        CardListFragment fragment = new CardListFragment();
        Bundle args = new Bundle();
        args.putSerializable(PRESENTER, presenter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_list, container, false);

        // get presenter
        if (savedInstanceState != null) {
            cardsPresenter = (CardsContract.Presenter) savedInstanceState.getSerializable(PRESENTER);
            cardsPresenter.setListView(this);
            // restore selected position of last time
            CardsRecyclerAdapter.selectedPos = savedInstanceState.getInt(SELECTED_POS);
        } else {
            cardsPresenter = (CardsContract.Presenter) getArguments().getSerializable(PRESENTER);
            cardsPresenter.setListView(this);
        }

        // change action bar
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(R.string.app_name);

        // RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.cardRecycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(recyclerLayoutManager);

        // RecyclerView Adapter & Divider
        recyclerAdapter = new CardsRecyclerAdapter(cardsPresenter, this);
        recyclerView.setAdapter(recyclerAdapter);
        SimpleDividerItemDecoration decoration = new SimpleDividerItemDecoration(getContext());
        recyclerView.addItemDecoration(decoration);

        // set item touch listener for swiping
        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));

        return view;
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(PRESENTER, (Serializable) cardsPresenter);
        outState.putInt(SELECTED_POS, CardsRecyclerAdapter.selectedPos);
        super.onSaveInstanceState(outState);
    }

    // when one card is deleted
    @Override
    public void onDeleteCard(View view, Card card) {
        Snackbar snackbar = Snackbar.make(view, "Delete a card success.", Snackbar.LENGTH_SHORT);
        snackbar.setAction("UNDO", new View.OnClickListener() {
            public void onClick(View v) {
                if(cardsPresenter.addCard(card)){
                    Snackbar.make(v, "Add a card success.", Snackbar.LENGTH_SHORT).show();
                    recyclerAdapter.notifyDataSetChanged();
                }else{
                    Snackbar.make(v, "Failed to add a card.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        snackbar.show();
    }

    // when item of recycler view is clicked
    @Override
    public void onItemClick(View view, int position) {
        CardDetailFragment cardDetailFragment = CardDetailFragment.newInstance((Serializable) cardsPresenter, position);
        if (CardsActivity.isLargeScreen) {
            // large screen
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.right_container, cardDetailFragment).commit();
        } else {
            // normal screen
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, cardDetailFragment).addToBackStack(null).commit();
        }
    }
}
