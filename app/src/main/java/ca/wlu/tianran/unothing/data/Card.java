package ca.wlu.tianran.unothing.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Parcelable{
    int id;
    String image;
    String ques;
    String answ;

    public Card(int id, String image, String ques, String answ){
        this.id = id;
        this.image = image;
        this.ques = ques;
        this.answ = answ;
    }

    public Card(String image, String ques, String answ){
        this.id = -1;
        this.image = image;
        this.ques = ques;
        this.answ = answ;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getQues() {
        return ques;
    }

    public String getAnsw() {
        return answ;
    }

    private Card(Parcel in){
        id = in.readInt();
        image = in.readString();
        ques = in.readString();
        answ = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(id);
        dest.writeString(image);
        dest.writeString(ques);
        dest.writeString(answ);
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }

        @Override
        public Card createFromParcel(Parcel parcel) {
            return new Card(parcel);
        }
    };
}
