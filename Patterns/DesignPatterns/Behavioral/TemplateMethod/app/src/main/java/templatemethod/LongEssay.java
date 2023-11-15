package templatemethod;

public class LongEssay extends EssayTemplate {

    public LongEssay(String topic) {
        super(topic);
    }

    @Override
    void writeBody() {
        System.out.println("Adding 6 paras");
    }

}
