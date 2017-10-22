package ca.wlu.tianran.unothing.cards;

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

import java.io.Serializable;

public class CardListFragment extends Fragment
        implements CardsContract.ListView, CardsContract.ItemClickListener {
    private final static String PRESENTER = "presenter";
    private final static String SELECTED_POS = "selected_pos";

    private CardsContract.Presenter cardsPresenter;
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
        RecyclerView.Adapter recyclerAdapter = new CardsRecyclerAdapter(cardsPresenter, this);
        recyclerView.setAdapter(recyclerAdapter);
        CardListDecoration decoration = new CardListDecoration(
                this.getContext(), getResources().getBoolean(R.bool.is_landscape));
        recyclerView.addItemDecoration(decoration);

        return view;
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(PRESENTER, (Serializable) cardsPresenter);
        outState.putInt(SELECTED_POS, CardsRecyclerAdapter.selectedPos);
        super.onSaveInstanceState(outState);
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
