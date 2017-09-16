package ca.wlu.tianran.unothing.model;

public class CardModel implements ICard {
    int id;
    String image;
    String question;
    String answer;

    public CardModel(int id, String image, String question, String answer) {
        this.id = id;
        this.image = image;
        this.question = question;
        this.answer = answer;
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
    public String getQuestion() {
        return question;
    }

    @Override
    public String getAnswer() {
        return answer;
    }
}
