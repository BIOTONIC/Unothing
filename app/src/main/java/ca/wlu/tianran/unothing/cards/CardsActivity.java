package ca.wlu.tianran.unothing.cards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import ca.wlu.tianran.unothing.R;
import ca.wlu.tianran.unothing.addtask.AddCardActivity;
import ca.wlu.tianran.unothing.data.CardsRepository;

public class CardsActivity extends AppCompatActivity implements CardsContract.View, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private final static String LAST_CARD = "last_card";
    private final static String QUES_SHOW = "ques_show";
    private final static String ANSW_SHOW = "answ_show";
    private final static int REQUEST_CODE = 0215;

    private ImageView imageView;
    private ToggleButton quesTgl;
    private TextView quesText;
    private ToggleButton answTgl;
    private TextView answText;
    private Button nextBtn;

    private CardsContract.Presenter cardPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find view
        imageView = (ImageView) this.findViewById(R.id.imageView);
        quesTgl = (ToggleButton) this.findViewById(R.id.quesTgl);
        quesText = (TextView) this.findViewById(R.id.quesText);
        answTgl = (ToggleButton) this.findViewById(R.id.answTgl);
        answText = (TextView) this.findViewById(R.id.answText);
        nextBtn = (Button) this.findViewById(R.id.nextBtn);

        // set listener
        quesTgl.setOnCheckedChangeListener(this);
        answTgl.setOnCheckedChangeListener(this);
        nextBtn.setOnClickListener(this);

        // create presenter
        cardPresenter = new CardsPresenter(CardsRepository.getInstance(getApplicationContext()), this);

        if (savedInstanceState != null) {
            // also can get instance state in onRestoreInstanceState()
            cardPresenter.setCurrIndex(savedInstanceState.getInt(LAST_CARD));
            quesTgl.setChecked(savedInstanceState.getBoolean(QUES_SHOW));
            answTgl.setChecked(savedInstanceState.getBoolean(ANSW_SHOW));
        }
        cardPresenter.getNextCard();
    }

    public void setPresenter(CardsContract.Presenter presenter) {
        cardPresenter = presenter;
    }

    // first tried to use onConfigurationChanged() to intercept orientation changing event
    // through this way we can avoid recreating activity
    // but I failed to save state and layout information
    // so I just let it recreate but save state before calling onDestroy()
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(LAST_CARD, cardPresenter.getLastIndex());
        outState.putBoolean(QUES_SHOW, quesTgl.isChecked());
        outState.putBoolean(ANSW_SHOW, answTgl.isChecked());
    }

    // inflate menu in action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
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
        switch (resultCode) {
            case RESULT_OK:
                Bundle b = data.getExtras();
                String newQues = b.getString("newQues");
                String newAnsw = b.getString("newAnsw");
                String newImage = b.getString("newImage");
                if (cardPresenter.addCard(newQues, newAnsw, newImage)) {
                    Toast.makeText(this, "Add card success.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to add card.", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    // when next button is clicked
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextBtn:
                cardPresenter.getNextCard();
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
                    quesText.setText(cardPresenter.getQues());
                } else {
                    hideQues();
                }
                break;
            case R.id.answTgl:
                if (isChecked) {
                    answText.setText(cardPresenter.getAnsw());
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
    public void showImg(String name) {
        int resourceId = getResources().getIdentifier(name, "drawable", getPackageName());
        imageView.setImageResource(resourceId);
    }

}
