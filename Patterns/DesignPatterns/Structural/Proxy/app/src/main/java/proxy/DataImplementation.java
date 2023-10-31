package proxy;

import java.util.ArrayList;
import java.util.List;

public class DataImplementation implements Data {

    private List<String> data;

    public DataImplementation() {
        data = new ArrayList<>();
    }

    @Override
    public void addData(String dataChunck) {
        data.add(dataChunck);
    }

    @Override
    public int search(String dataToSearch) {
        return data.indexOf(dataToSearch);
    }

    @Override
    public boolean removeData(String dataChunck) {
        return data.remove(dataChunck);
    }

    @Override
    public void clearData() {
        data.clear();
    }

    @Override
    public String getData(int index) {
        return data.get(index);
    }

}
