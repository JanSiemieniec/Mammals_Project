public class DataBufor {

    private boolean flag;
    private double weight;

    public DataBufor() {
        this.flag = false;
        this.weight = 0;
    }

    public synchronized void put(double weight) {
        while (flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.weight = weight;
        this.flag = true;
        notify();
    }


    public synchronized double get() {
        while (!flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        double rWeight = this.weight;
        flag = false;
        notify();
        return rWeight;
    }


}
