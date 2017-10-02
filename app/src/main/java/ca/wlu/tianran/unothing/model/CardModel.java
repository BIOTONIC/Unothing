package ca.wlu.tianran.unothing.model;

public class CardModel implements ICard {
    int id;
    String image;
    String ques;
    String answ;

    public CardModel(int id, String image, String ques, String answ) {
        this.id = id;
        this.image = image;
        this.ques = ques;
        this.answ = answ;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public String getQues() {
        return ques;
    }

    @Override
    public String getAnsw() {
        return answ;
    }
}
