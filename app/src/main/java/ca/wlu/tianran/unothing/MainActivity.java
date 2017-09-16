package ca.wlu.tianran.unothing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import ca.wlu.tianran.unothing.presenter.CardPresenterImpl;
import ca.wlu.tianran.unothing.presenter.ICardPresenter;
import ca.wlu.tianran.unothing.view.ICardView;

public class MainActivity extends AppCompatActivity implements ICardView, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private ImageView imageView;
    private ToggleButton quesTgl;
    private TextView quesText;
    private ToggleButton answTgl;
    private TextView answText;
    private Button nextBtn;
    private ICardPresenter cardPresenter;

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

        // add presenter
        cardPresenter = new CardPresenterImpl(
                this,
                getResources().getStringArray(R.array.id),
                getResources().getStringArray(R.array.ques),
                getResources().getStringArray(R.array.answ));
        cardPresenter.showNext();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextBtn:
                cardPresenter.showNext();
                break;
            default:
                break;
        }
    }

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
