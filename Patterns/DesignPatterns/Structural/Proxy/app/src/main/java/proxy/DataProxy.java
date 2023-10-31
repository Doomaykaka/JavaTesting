package proxy;

public class DataProxy implements Data {
    private Data service;

    public DataProxy(Data serviceData) {
        this.service = serviceData;
    }

    @Override
    public void addData(String dataChunck) {
        System.out.println("Proxy started data adding");

        try {
            if (dataChunck == null) {
                throw new Exception("Data chunck cannot be null or empty");
            } else {
                if ((!dataChunck.isBlank()) && (!dataChunck.isEmpty())) {
                    service.addData(dataChunck);
                } else {
                    throw new Exception("Data chunck cannot be null or empty");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Proxy ended data adding");
    }

    @Override
    public int search(String dataToSearch) {
        int index = -1;

        System.out.println("Proxy started data chunck searching");

        try {
            if (dataToSearch == null) {
                throw new Exception("Data chunck to search cannot be null or empty");
            } else {
                if ((!dataToSearch.isBlank()) && (!dataToSearch.isEmpty())) {
                    index = service.search(dataToSearch);
                } else {
                    throw new Exception("Data chunck to search cannot be null or empty");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Proxy ended data chunck searching");

        return index;
    }

    @Override
    public boolean removeData(String dataChunck) {
        boolean isDeleted = false;

        System.out.println("Proxy started data removing");

        try {
            if (dataChunck == null) {
                throw new Exception("Data chunck cannot be null or empty");
            } else {
                if ((!dataChunck.isBlank()) && (!dataChunck.isEmpty())) {
                    isDeleted = service.removeData(dataChunck);
                } else {
                    throw new Exception("Data chunck cannot be null or empty");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Proxy ended data removing");
        return isDeleted;
    }

    @Override
    public void clearData() {
        System.out.println("Proxy started clearing");

        service.clearData();

        System.out.println("Proxy ended clearing");
    }

    @Override
    public String getData(int index) {
        String data = null;

        System.out.println("Proxy started getting");

        try {
            if (index >= 0) {
                data = service.getData(index);
            } else {
                throw new Exception("Bad index to get data");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Proxy ended getting");
        return null;
    }

}
