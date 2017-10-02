package ca.wlu.tianran.unothing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText quesIpt;
    private EditText answIpt;
    private EditText imageIpt;
    private Button doneBtn;
    private Intent data;

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
    }

    // when done button is clicked
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doneBtn:
                String newQues = quesIpt.getText().toString();
                String newAnsw = answIpt.getText().toString();
                String newImage = imageIpt.getText().toString();
                if (newQues != null && newAnsw != null && Pattern.matches("img[0-7]", newImage)) {
                    data = new Intent();
                    data.putExtra("newQues", newQues);
                    data.putExtra("newAnsw", newAnsw);
                    data.putExtra("newImage", newImage);
                    this.setResult(RESULT_OK, data);
                    this.finish();
                } else if (newQues==null || newAnsw == null || newImage == null) {
                    Toast.makeText(this, "Please fill the form",Toast.LENGTH_SHORT).show();
                } else {
                    imageIpt.setText("");
                    Toast.makeText(this, "Image resource doesn't exist.",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("TR","Back");
        data = new Intent();
        this.setResult(RESULT_CANCELED, data);
    }
}
