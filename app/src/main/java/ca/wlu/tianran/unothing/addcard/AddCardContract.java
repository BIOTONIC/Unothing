package ca.wlu.tianran.unothing.addcard;

import android.content.Intent;

interface AddCardContract {
    interface Presenter{
        void processData(String KEY, String ques, String answ, String image);
    }
    interface View{
        void setPresenter(Presenter presenter);

        void sendData(Intent intent);

        void alertEmpty();

        void alertImgNotExist();
    }
}
