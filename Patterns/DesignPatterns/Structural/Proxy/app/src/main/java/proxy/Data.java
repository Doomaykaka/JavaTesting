package proxy;

public interface Data {
    public void addData(String dataChunck);

    public int search(String dataToSearch);

    public boolean removeData(String dataChunck);

    public void clearData();

    public String getData(int index);
}
