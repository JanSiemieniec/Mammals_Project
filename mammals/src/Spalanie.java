public class Spalanie extends Thread {
    private DataBufor db;
    private Mammals mammals;
    private int val;

    public Spalanie(DataBufor db, Mammals mammals, int val) {
        this.db = db;
        this.mammals = mammals;
        this.val = val;
    }

    @Override
    public void run() {
        double weight;
        for (int i = 0; i < 365 * 2; i++) {

            weight = db.get() * 0.986;          //tracenie 1,4% wagi
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (val == 0)
                Colours.printColoured("Zwierze waga po spaleniu dnia " + (1 + (i / 2)) + ": " + weight, Colours.blue);
            else if (val == 1)
                Colours.printColoured("Zwierze waga po spaleniu dnia " + (1 + (i / 2)) + ": " + weight, Colours.magenta);
            else Colours.printColoured("Zwierze waga po spaleniu dnia " + (1 + (i / 2)) + ": " + weight, Colours.green);
            mammals.setBody_wt(weight);
        }
    }
}
