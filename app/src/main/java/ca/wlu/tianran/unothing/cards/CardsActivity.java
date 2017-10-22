package ca.wlu.tianran.unothing.cards;

import ca.wlu.tianran.unothing.R;
import ca.wlu.tianran.unothing.addcard.AddCardActivity;
import ca.wlu.tianran.unothing.data.CardsRepository;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.*;

import java.io.Serializable;

public class CardsActivity extends AppCompatActivity {
    private final static int REQUEST_CODE = 1120;
    public static boolean isLargeScreen = false;

    private CardsContract.Presenter cardsPresenter;
    private CardListFragment cardListFragment;
    private CardDetailFragment cardDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        // presenter has the the same life cycle with activity
        cardsPresenter = new CardsPresenter(CardsRepository.getInstance(getApplicationContext()));

        // orientation changed event will be handled by fragments, not the activity
        if (savedInstanceState != null) {
            return;
        }

        // create CardListFragment
        cardListFragment = (CardListFragment) getSupportFragmentManager().findFragmentById(R.id.card_list_fragment);
        if (cardListFragment == null) {
            cardListFragment = CardListFragment.newInstance((Serializable) cardsPresenter);
        }
        // normal screen, use fragment container
        if (findViewById(R.id.fragment_container) != null) {
            // use replace instead of add, otherwise each time rotated, one more CardListFragment will be add
            // https://stackoverflow.com/questions/20401778/fragment-not-destroyed-on-rotation
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cardListFragment).commit();
        }
        // large screen, use two containers for two fragments
        else {
            isLargeScreen = true;
            getSupportFragmentManager().beginTransaction().replace(R.id.left_container, cardListFragment).commit();
            cardDetailFragment = (CardDetailFragment) getSupportFragmentManager().findFragmentById(R.id.card_detail_fragment);
            if (cardDetailFragment == null) {
                cardDetailFragment = CardDetailFragment.newInstance((Serializable) cardsPresenter, 0);
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.right_container, cardDetailFragment).commit();
        }
    }

    // inflate menu in action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // when item is menu is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                // CardsActivity is parent of AddCardActivity
                // plus CardsActivity's launch mode is singleTask
                // when returning from AddCardActivity, CardsActivity haven't changed
                startActivityForResult(new Intent(this, AddCardActivity.class), REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    if (cardsPresenter.addCard(data, AddCardActivity.PARCEL_KEY)) {
                        Toast.makeText(this, "Add card success.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to add card.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
