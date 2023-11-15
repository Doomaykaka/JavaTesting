package templatemethod;

public class ShortEssay extends EssayTemplate {

    public ShortEssay(String topic) {
        super(topic);
    }

    @Override
    void writeBody() {
        System.out.println("Adding 2 paras");
    }

}
