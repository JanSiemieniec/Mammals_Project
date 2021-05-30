

public class Mammals implements Comparable<Mammals> {
    private double body_wt, brain_wt, non_dreaming, dreaming, total_sleep, life_span, gestation;
    private int predation, exposure, danger;

    public Mammals(double body_wt, double brain_wt, double non_dreaming, double dreaming, double total_sleep,
                   double life_span, double gestation, double predation, double exposure, double danger) {
        this.body_wt = body_wt;
        this.brain_wt = brain_wt;
        this.non_dreaming = non_dreaming;
        this.dreaming = dreaming;
        this.total_sleep = total_sleep;
        this.life_span = life_span;
        this.gestation = gestation;
        this.predation = (int) predation;
        this.exposure = (int) exposure;
        this.danger = (int) danger;
    }

    public double getBody_wt() {
        return body_wt;
    }

    public void setBody_wt(double body_wt) {
        this.body_wt = body_wt;
    }

    public double getDreaming() {
        return dreaming;
    }

    public double getLife_span() {
        return life_span;
    }

    public double getTotal_sleep() {
        return total_sleep;
    }

    @Override
    public String toString() {                                              //zamiana 0 na unknow przy wyświetlaniu
        return
                "body_wt=" + (body_wt != 0 ? body_wt : "unknow") +
                        ", brain_wt=" + (brain_wt != 0 ? brain_wt : "unknow") +
                        ", non_dreaming=" + (non_dreaming != 0 ? non_dreaming : "unknow") +
                        ", dreaming=" + (dreaming != 0 ? dreaming : "unknow") +
                        ", total_sleep=" + (total_sleep != 0 ? total_sleep : "unknow") +
                        ", life_span=" + (life_span != 0 ? life_span : "unknow") +
                        ", gestation=" + (gestation != 0 ? gestation : "unknow") +
                        ", predation=" + (predation != 0 ? predation : "unknow") +
                        ", exposure=" + (exposure != 0 ? exposure : "unknow") +
                        ", danger=" + (danger != 0 ? danger : "unknow");
    }

    @Override
    public int compareTo(Mammals o) {
        return Double.compare(o.getBody_wt(), getBody_wt());
    }
}

