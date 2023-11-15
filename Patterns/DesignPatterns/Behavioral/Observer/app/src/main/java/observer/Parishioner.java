package observer;

public class Parishioner implements Observer {
    private String name;

    public Parishioner(String name, Observable o) {
        this.name = name;
        o.registerObserver(this);
    }

    @Override
    public void update(String news) {
        System.out.println(name + " узнал новость: " + news);
    }
}
