package ca.wlu.tianran.unothing.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class Card implements Parcelable {
    private UUID id;
    private final String image;
    private final String ques;
    private final String answ;

    public Card(String image, String ques, String answ) {
        this.id = UUID.randomUUID();
        this.image = image;
        this.ques = ques;
        this.answ = answ;
    }

    public Card(String id, String image, String ques, String answ) {
        this.id = UUID.fromString(id);
        this.image = image;
        this.ques = ques;
        this.answ = answ;
    }

    public String getId() {
        return id.toString();
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

    private Card(Parcel in) {
        id = (UUID) in.readSerializable();
        image = in.readString();
        ques = in.readString();
        answ = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(id);
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
