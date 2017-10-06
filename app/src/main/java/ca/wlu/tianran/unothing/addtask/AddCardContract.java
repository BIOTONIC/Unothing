package ca.wlu.tianran.unothing.addtask;

import android.content.Intent;

public interface AddCardContract {
    interface Presenter{
        void processData(String KEY, String ques, String answ, String image);
    }
    interface View{
        void sendData(Intent data);

        void alertEmpty();

        void alertImgNotExist();
    }
}
