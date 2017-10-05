package ca.wlu.tianran.unothing.addtask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ca.wlu.tianran.unothing.R;

public class AddCardActivity extends AppCompatActivity implements AddCardContract.View, View.OnClickListener {

    private EditText quesIpt;
    private EditText answIpt;
    private EditText imageIpt;
    private Button doneBtn;
    private Intent data;

    private AddCardContract.Presenter addCardPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // find view
        quesIpt = (EditText) findViewById(R.id.quesIpt);
        answIpt = (EditText) findViewById(R.id.answIpt);
        imageIpt = (EditText) findViewById(R.id.imageIpt);
        doneBtn = (Button) findViewById(R.id.doneBtn);

        // add listener
        doneBtn.setOnClickListener(this);

        // set presenter
        addCardPresenter = new AddCardPresenter(this);
    }

    // when done button is clicked
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doneBtn:
                String newQues = quesIpt.getText().toString();
                String newAnsw = answIpt.getText().toString();
                String newImage = imageIpt.getText().toString();
                addCardPresenter.processData(newQues, newAnsw, newImage);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        data = new Intent();
        this.setResult(RESULT_CANCELED, data);
    }

    @Override
    public void sendData(Intent data) {
        this.setResult(RESULT_OK, data);
        this.finish();
    }

    @Override
    public void alertEmpty() {
        Toast.makeText(this, "Please fill the form", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void alertImgNotExist() {
        imageIpt.setText("");
        Toast.makeText(this, "Image resource doesn't exist.", Toast.LENGTH_SHORT).show();
    }
}
