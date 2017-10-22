package ca.wlu.tianran.unothing.addcard;

import ca.wlu.tianran.unothing.data.Card;
import android.content.Intent;
import android.os.Parcelable;

import java.util.regex.Pattern;

public class AddCardPresenter implements AddCardContract.Presenter {
    private final AddCardContract.View addCardView;

    public AddCardPresenter(AddCardContract.View addCardView) {
        this.addCardView = addCardView;
        this.addCardView.setPresenter(this);
    }

    @Override
    public void processData(String KEY, String ques, String answ, String image) {
        if (ques != null && answ != null && Pattern.matches("img[0-7]", image)) {
            Card card = new Card(image, ques, answ);
            Intent data = new Intent();
            data.putExtra(KEY, (Parcelable) card);
            addCardView.sendData(data);
        } else if (ques == null || answ == null || image == null) {
            addCardView.alertEmpty();
        } else {
            addCardView.alertImgNotExist();
        }
    }
}
