package templatemethod;

public abstract class EssayTemplate {
    private String topic;
    
    public EssayTemplate(String topic) {
        this.topic = topic;
    }
  
    public final void writeEssay() {
        doResearch();
        makeOutline();
        writeIntro();
        writeBody();
        writeConclusion();
    }
  
    void doResearch() { System.out.println("Researching"); }
  
    void makeOutline() { System.out.println("Making an outline"); }
  
    void writeIntro() { System.out.println("Introducing topic"); }
     
    void writeConclusion() { System.out.println("Concluding topic");}
  
    abstract void writeBody();
}
