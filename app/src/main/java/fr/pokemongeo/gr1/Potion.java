package fr.pokemongeo.gr1;

public class Potion extends Item {
    private int pv;

    public Potion(String name, String description, int quantity, int pv) {
        super(name, "baseline_local_drink_24", description, quantity);
        this.pv = pv;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv= pv;
    }
}
