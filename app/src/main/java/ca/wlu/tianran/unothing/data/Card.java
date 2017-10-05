package ca.wlu.tianran.unothing.data;

public class Card{
    int id;
    String image;
    String ques;
    String answ;

    public Card(int id, String image, String ques, String answ) {
        this.id = id;
        this.image = image;
        this.ques = ques;
        this.answ = answ;
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
}
