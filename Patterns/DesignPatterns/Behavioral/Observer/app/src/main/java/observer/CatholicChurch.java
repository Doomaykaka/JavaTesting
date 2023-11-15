package observer;

import java.util.List;
import java.util.ArrayList;

public class CatholicChurch implements Observable {
    private List<Observer> parishioners;
    private String newsChurch;

    public CatholicChurch() {
        parishioners = new ArrayList<>();
    }

    public void setNewsChurch(String news) {
        this.newsChurch = news;
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer o) {
        parishioners.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        parishioners.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : parishioners)
            o.update(newsChurch);
    }
}
