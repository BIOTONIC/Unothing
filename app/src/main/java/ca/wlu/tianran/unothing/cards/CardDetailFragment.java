package ca.wlu.tianran.unothing.cards;

import ca.wlu.tianran.unothing.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.*;

import java.io.Serializable;

public class CardDetailFragment extends Fragment
        implements CardsContract.DetailView, CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private ActionBar actionBar;
    private ImageView imageView;
    private ToggleButton quesTgl;
    private TextView quesText;
    private ToggleButton answTgl;
    private TextView answText;
    private Button nextBtn;

    private CardsContract.Presenter cardsPresenter;

    private final static String PRESENTER = "presenter";
    private final static String LAST_CARD = "last_card";
    private final static String QUES_SHOW = "ques_show";
    private final static String ANSW_SHOW = "answ_show";
    public static String ITEM_POSITION = "item_position";

    // system will call the public empty constructor after orientation has changed
    // use savedInstanceState to restore state
    // https://stackoverflow.com/questions/9245408/best-practice-for-instantiating-a-new-android-fragment
    public CardDetailFragment() {
    }

    // manually call static factory method to create a fragment
    // use getArguments to restore state
    public static CardDetailFragment newInstance(Serializable presenter, int position) {
        CardDetailFragment fragment = new CardDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(PRESENTER, presenter);
        args.putInt(ITEM_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_detail, container, false);

        // find view
        imageView = view.findViewById(R.id.imageView);
        quesTgl = view.findViewById(R.id.quesTgl);
        quesText = view.findViewById(R.id.quesText);
        answTgl = view.findViewById(R.id.answTgl);
        answText = view.findViewById(R.id.answText);
        nextBtn = view.findViewById(R.id.nextBtn);

        // set listener
        quesTgl.setOnCheckedChangeListener(this);
        answTgl.setOnCheckedChangeListener(this);
        nextBtn.setOnClickListener(this);

        // change action bar
        setHasOptionsMenu(true);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (!CardsActivity.isLargeScreen) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // return to last saved state
        if (savedInstanceState != null) {
            // also can get instance state in onRestoreInstanceState()
            cardsPresenter = (CardsContract.Presenter) savedInstanceState.getSerializable(PRESENTER);
            cardsPresenter.setDetailView(this);
            //cardsPresenter.setCurrIndex(savedInstanceState.getInt(LAST_CARD));
            cardsPresenter.getCurrCard();
            quesTgl.setChecked(savedInstanceState.getBoolean(QUES_SHOW));
            answTgl.setChecked(savedInstanceState.getBoolean(ANSW_SHOW));
        } else {
            int position = getArguments().getInt(ITEM_POSITION);
            cardsPresenter = (CardsContract.Presenter) getArguments().getSerializable(PRESENTER);
            cardsPresenter.setDetailView(this);
            cardsPresenter.getCard(position);
        }
        return view;
    }

    // save fragment's state in fragment's code, not in actvitity's
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(PRESENTER, (Serializable) cardsPresenter);
        outState.putInt(LAST_CARD, cardsPresenter.getLastIndex());
        outState.putBoolean(QUES_SHOW, quesTgl.isChecked());
        outState.putBoolean(ANSW_SHOW, answTgl.isChecked());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // when next button is clicked
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextBtn:
                cardsPresenter.getNextCard();
                break;
            default:
                break;
        }
    }

    // when toggle button is clicked
    @Override
    public void onCheckedChanged(CompoundButton btn, boolean isChecked) {
        switch (btn.getId()) {
            case R.id.quesTgl:
                if (isChecked) {
                    quesText.setText(cardsPresenter.getQues());
                } else {
                    hideQues();
                }
                break;
            case R.id.answTgl:
                if (isChecked) {
                    answText.setText(cardsPresenter.getAnsw());
                } else {
                    hideAnsw();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void hideQues() {
        quesTgl.setChecked(false);
        quesText.setText(R.string.hide_text);
    }

    @Override
    public void hideAnsw() {
        answTgl.setChecked(false);
        answText.setText(R.string.hide_text);
    }

    @Override
    public void setTitle(int position) {
        if(!CardsActivity.isLargeScreen){
            actionBar.setTitle("Question " + (position + 1));
        }
    }

    @Override
    public void showImg(String name) {
        int resourceId = getResources().getIdentifier(name, "drawable", imageView.getContext().getPackageName());
        imageView.setImageResource(resourceId);
    }
}
