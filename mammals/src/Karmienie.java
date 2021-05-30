public class Karmienie extends Thread {
    private DataBufor db;
    private double weight;
    private Mammals mammals;
    private int val;

    public Karmienie(DataBufor db, Mammals mammals, int val) {
        this.db = db;
        this.mammals = mammals;
        this.val = val;
    }

    @Override
    public void run() {
        for (int i = 0; i < 365 * 2; i++) {             //365*2 bo cykl karmienie-spalanie jest 2 razy na dzień
            try {
                sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            weight = mammals.getBody_wt() * (1 + ((Math.random() * 15 + 5) / 1000));                 //losowanie wartości 1.005 - 1.02
            db.put(weight);                                                                                                            //dodanie val aby były różne kolory wyświetlania
            if (val == 0)
                Colours.printColoured("Zwierze waga po jedzeniu dnia " + (1 + (i / 2)) + ": " + weight, Colours.blue);           //tricky wyciągnięcie dnia
            else if (val == 1)
                Colours.printColoured("Zwierze waga po jedzeniu dnia " + (1 + (i / 2)) + ": " + weight, Colours.magenta);
            else Colours.printColoured("Zwierze waga po jedzeniu dnia " + (1 + (i / 2)) + ": " + weight, Colours.green);

        }
    }
}
